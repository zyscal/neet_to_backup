#include <anjay/anjay.h>
#include <anjay/attr_storage.h>
#include <anjay/security.h>
#include <anjay/server.h>
#include <avsystem/commons/avs_log.h>

#include <poll.h>

#include "firmware_update.h"
#include "time_object.h"

int main_loop(anjay_t *anjay, const anjay_dm_object_def_t **time_object) {
    while (true) {
        // Obtain all network data sources
        AVS_LIST(avs_net_socket_t *const) sockets = anjay_get_sockets(anjay);

        // Prepare to poll() on them
        size_t numsocks = AVS_LIST_SIZE(sockets);
        struct pollfd pollfds[numsocks];
        size_t i = 0;
        AVS_LIST(avs_net_socket_t *const) sock;
        AVS_LIST_FOREACH(sock, sockets) {
            pollfds[i].fd = *(const int *) avs_net_socket_get_system(*sock);
            pollfds[i].events = POLLIN;
            pollfds[i].revents = 0;
            ++i;
        }

        const int max_wait_time_ms = 1000;
        // Determine the expected time to the next job in milliseconds.
        // If there is no job we will wait till something arrives for
        // at most 1 second (i.e. max_wait_time_ms).
        int wait_ms =
                anjay_sched_calculate_wait_time_ms(anjay, max_wait_time_ms);

        // printf("----------------------\nwait time : %d\n", wait_ms);
        // Wait for the events if necessary, and handle them.
        if (poll(pollfds, numsocks, wait_ms) > 0) {
            int socket_id = 0;
            AVS_LIST(avs_net_socket_t *const) socket = NULL;
            AVS_LIST_FOREACH(socket, sockets) {
                if (pollfds[socket_id].revents) {
                    if (anjay_serve(anjay, *socket)) {
                        avs_log(tutorial, ERROR, "anjay_serve failed");
                    }
                }
                ++socket_id;
            }
        }

        // Notify the library about a Resource value change
        time_object_notify(anjay, time_object);

        // Finally run the scheduler
        anjay_sched_run(anjay);
    }
    return 0;
}

// Installs Security Object and adds and instance of it.
// An instance of Security Object provides information needed to connect to
// LwM2M server.
static int setup_security_object(anjay_t *anjay) {
    if (anjay_security_object_install(anjay)) {
        return -1;
    }

    static const char PSK_IDENTITY[] = "identity";
    static const char PSK_KEY[] = "P4s$w0rd";

    // anjay_security_instance_t security_instance = {
    //     .ssid = 1,
    //     .server_uri = "coap://59.110.213.240:5683",
    //     .security_mode = ANJAY_SECURITY_PSK,
    //     .public_cert_or_psk_identity = (const uint8_t *) PSK_IDENTITY,
    //     .public_cert_or_psk_identity_size = strlen(PSK_IDENTITY),
    //     .private_cert_or_psk_key = (const uint8_t *) PSK_KEY,
    //     .private_cert_or_psk_key_size = strlen(PSK_KEY)
    // };
        const anjay_security_instance_t security_instance = {
        .ssid = 1,
        .server_uri = "coap://59.110.213.240:5700",
        .security_mode = ANJAY_SECURITY_NOSEC
    };

    // Anjay will assign Instance ID automatically
    anjay_iid_t security_instance_id = ANJAY_ID_INVALID;
    if (anjay_security_object_add_instance(anjay, &security_instance,
                                           &security_instance_id)) {
        return -1;
    }

    return 0;
}

// Installs Server Object and adds and instance of it.
// An instance of Server Object provides the data related to a LwM2M Server.
static int setup_server_object(anjay_t *anjay) {
    if (anjay_server_object_install(anjay)) {
        return -1;
    }

    const anjay_server_instance_t server_instance = {
        // Server Short ID
        .ssid = 1,
        // Client will send Update message often than every 60 seconds
        .lifetime = 500,
        // Disable Default Minimum Period resource
        .default_min_period = -1,
        // Disable Default Maximum Period resource
        .default_max_period = -1,
        // Disable Disable Timeout resource
        .disable_timeout = -1,
        // Sets preferred transport to UDP
        .binding = "U"
    };

    // Anjay will assign Instance ID automatically
    anjay_iid_t server_instance_id = ANJAY_ID_INVALID;
    if (anjay_server_object_add_instance(anjay, &server_instance,
                                         &server_instance_id)) {
        return -1;
    }

    return 0;
}

int main(int argc, char *argv[]) {


    #ifdef WITH_DOWNLOADER
        printf("\n\ni have http\n\n");
    #else
        printf("\n\n idont\n\n");
    #endif

    if (argc != 2) {
        avs_log(tutorial, ERROR, "usage: %s ENDPOINT_NAME", argv[0]);
        return -1;
    }

    ENDPOINT_NAME = argv[1];
    avs_coap_udp_tx_params_t udp_tx_params = {
   // Wait at least 4 seconds for the initial response.
   .ack_timeout = avs_time_duration_from_scalar(4, AVS_TIME_S),
   // Do not randomize wait times for simplicity of the discussion,
   // thus "at least" in the comment above should be thought of as
   // "exactly".
   .ack_random_factor = 1.0,
   // Allow up to 4 retransmissions.
   .max_retransmit = 4,
   // leave the NSTART parameter at the default value of 1
   .nstart = 1
};
    const anjay_configuration_t CONFIG = {
        .endpoint_name = ENDPOINT_NAME,
        .in_buffer_size = 4000,
        .out_buffer_size = 4000,
        .msg_cache_size = 4000,
        .udp_tx_params = &udp_tx_params
    };

    anjay_t *anjay = anjay_new(&CONFIG);
    if (!anjay) {
        avs_log(tutorial, ERROR, "Could not create Anjay object");
        return -1;
    }

    int result = 0;
    // Install Attribute storage and setup necessary objects
    if (anjay_attr_storage_install(anjay) || setup_security_object(anjay)
            || setup_server_object(anjay) || fw_update_install(anjay)) {
        result = -1;
    }

    const anjay_dm_object_def_t **time_object = NULL;
    if (!result) {
        time_object = time_object_create();
        if (time_object) {
            result = anjay_register_object(anjay, time_object);
        } else {
            result = -1;
        }
    }

    if (!result) {
        result = main_loop(anjay, time_object);
    }

    anjay_delete(anjay);
    time_object_release(time_object);
    return result;
}
