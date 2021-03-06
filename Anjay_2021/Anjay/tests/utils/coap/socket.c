/*
 * Copyright 2017-2020 AVSystem <avsystem@avsystem.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <anjay_init.h>

#include "tests/utils/coap/socket.h"

void _anjay_mocksock_create(avs_net_socket_t **mocksock,
                            int inner_mtu,
                            int mtu) {
    avs_unit_mocksock_create_datagram(mocksock);
    avs_unit_mocksock_enable_recv_timeout_getsetopt(
            *mocksock, avs_time_duration_from_scalar(30, AVS_TIME_S));
    if (inner_mtu >= 0) {
        avs_unit_mocksock_enable_inner_mtu_getopt(*mocksock, inner_mtu);
    }
    if (mtu >= 0) {
        avs_unit_mocksock_enable_mtu_getopt(*mocksock, mtu);
    }
}
