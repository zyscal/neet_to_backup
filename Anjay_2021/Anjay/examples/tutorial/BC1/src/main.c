#include <anjay/anjay.h>
#include <avsystem/commons/avs_log.h>
#include <stdio.h>


int main(int argc, char *argv[]) {
    if (argc != 2) {
        avs_log(tutorial, ERROR, "usage: %s ENDPOINT_NAME", argv[0]);
        return -1;
    }

    const anjay_configuration_t CONFIG = {
        .endpoint_name = argv[1],
        .in_buffer_size = 4000,
        .out_buffer_size = 4000,
        .msg_cache_size = 4000
    };

    anjay_t *anjay = anjay_new(&CONFIG);
    if (!anjay) {
        avs_log(tutorial, ERROR, "Could not create Anjay object");
        return -1;
    }
    for(int i = 0; i < argc; i++)
    {
        printf("%s\n", argv[i]);
    }
    anjay_delete(anjay);
    return 0;
}
