#include <anjay/anjay.h>
#include <anjay/attr_storage.h>
#include <anjay/security.h>
#include <anjay/server.h>
#include <avsystem/commons/avs_log.h>

#include <poll.h>
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include<stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <unistd.h>

#include "time_object.h"
#include "firmware_update.h"
#include "XCBR_Pos_34828.h"

char *IPaddr = NULL;
int main_loop(anjay_t *anjay, const anjay_dm_object_def_t **time_object
                , const anjay_dm_object_def_t **xcbr_pos_object) {
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
        // Wait for the events if necessary, and handle them.
        if(wait_ms != 0)
            // printf("wait time ms : %d\n", wait_ms);
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
        // time_object_notify(anjay, time_object);
        xcbr_pos_object_notify(anjay, xcbr_pos_object);
                    // anjay_schedule_registration_update(anjay, 1);

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
    //     .server_uri = "coaps://try-anjay.avsystem.com:5684",
    //     .security_mode = ANJAY_SECURITY_PSK,
    //     .public_cert_or_psk_identity = (const uint8_t *) PSK_IDENTITY,
    //     .public_cert_or_psk_identity_size = strlen(PSK_IDENTITY),
    //     .private_cert_or_psk_key = (const uint8_t *) PSK_KEY,
    //     .private_cert_or_psk_key_size = strlen(PSK_KEY)
    // };


    const anjay_security_instance_t security_instance = {
        .ssid = 1,
        // .server_uri = "coap://59.110.213.240:5700",
        // .server_uri = "coap://192.168.24.129:5683",
        // .server_uri = "coap://192.168.3.24:6000",
        .server_uri = IPaddr,
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
void CMD(void* arg)
{
    int tmp = *(int *)arg;
    anjay_dm_object_def_t **xcbr_pos_object = *(anjay_dm_object_def_t ***)arg;
    int i = 0;
    char c;
    
    while (c = getchar())
    {
        if(c == 'c')
        {
            if(i == 0)
            {
                i = 4;
            }
            else
            {
                i = 0;
            }
                        anjay_schedule_registration_update(anjay, 1);
            int result = xcbr_pos_object_change_stval(0, xcbr_pos_object, i);
                        anjay_schedule_registration_update(anjay, 1);
            printf("\n\nxcbr_pos_stval now is ： %d\n", result);
        }
        else if(c == '\n')
        {
            printf("\n");
        }
        else if(c == 'u')
        {
            anjay_schedule_registration_update(anjay, 1);
anjay_schedule_registration_update(anjay, 1);
anjay_schedule_registration_update(anjay, 1);
anjay_schedule_registration_update(anjay, 1);
            anjay_schedule_registration_update(anjay, 1);
        }
    }
}

void auto_POS(void* arg)
{
    anjay_dm_object_def_t **xcbr_pos_object = *(anjay_dm_object_def_t ***)arg;
    int state = 0;
    printf(">>>>>>>>>>>>>>>>before sleep\n");
    sleep(60);
    while (true)
    {
        printf("\n\n\n##############enter into while ############\n\n");
        sleep(10);
        printf("ready to change\n");
        int result = xcbr_pos_object_change_stval(0, xcbr_pos_object, state);
        // anjay_schedule_registration_update(anjay, 1);
        state = (state + 1) % 2;
        printf("\n\nxcbr_pos_stval now is ： %d\n", result);
    }
}

void* say_hello(void* arg)
{

    CMD(arg);
    // auto_POS(arg);
}



int main(int argc, char *argv[]) {
    if (argc != 3) {
        avs_log(tutorial, ERROR, "usage: %s ENDPOINT_NAME", argv[0]);
        return -1;
    }

    avs_coap_udp_tx_params_t udp_tx_params = {
   // Wait at least 4 seconds for the initial response.
   .ack_timeout = avs_time_duration_from_scalar(6, AVS_TIME_S),
   // Do not randomize wait times for simplicity of the discussion,
   // thus "at least" in the comment above should be thought of as
   // "exactly".
   .ack_random_factor = 1.0,
   // Allow up to 4 retransmissions.
   .max_retransmit = 0,
   // leave the NSTART parameter at the default value of 1
   .nstart = 2
};
    ENDPOINT_NAME = argv[1];
    printf("%s\n",ENDPOINT_NAME);
    IPaddr = argv[2];
    printf("%s\n", IPaddr);

    const anjay_configuration_t CONFIG = {
        .endpoint_name = ENDPOINT_NAME,
        .in_buffer_size = 4000,
        .out_buffer_size = 4000,
        .msg_cache_size = 4000,
        .udp_tx_params = &udp_tx_params
    };


    anjay = anjay_new(&CONFIG);
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

    const anjay_dm_object_def_t **xcbr_pos_object = NULL;
    if (!result) {
        xcbr_pos_object = xcbr_pos_object_create();
        if(xcbr_pos_object) {
            result = anjay_register_object(anjay, xcbr_pos_object);
        } else
        {
            result = -1;
        }
    }

    pthread_t tids[0];
    int i = pthread_create(&tids[0], NULL, say_hello, &xcbr_pos_object);
    if(i == 0)
    {
        printf("thread success!\n");
    }
    else{
        printf("thread failed!\n");
    }

    if (!result) {
        result = main_loop(anjay, time_object, xcbr_pos_object);
    }

    anjay_delete(anjay); 
    time_object_release(time_object);
    xcbr_pos_object_release(xcbr_pos_object);
    return result;
}
