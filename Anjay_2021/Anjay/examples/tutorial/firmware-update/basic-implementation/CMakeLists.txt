cmake_minimum_required(VERSION 3.1)
project(basic-firmware-update C)

set(CMAKE_C_STANDARD 99)
set(CMAKE_C_EXTENSIONS OFF)

find_package(anjay REQUIRED)

add_executable(${PROJECT_NAME}
               src/main.c
               src/firmware_update.c
               src/firmware_update.h
               src/time_object.c
               src/time_object.h)
target_link_libraries(${PROJECT_NAME} PRIVATE anjay)

# add_definitions( -DWITH_DOWNLOADER=ON )
# add_definitions( -DWITH_HTTP_DOWNLOAD=ON )
# add_definitions( -DANJAY_WITH_DOWNLOADER=ON )
# add_definitions( -DANJAY_WITH_HTTP_DOWNLOAD=ON )

# option (WITH_DOWNLOADER "Use provided math implementation" ON)
# option (WITH_COAP_DOWNLOAD  "Use provided math implementation" ON)
# option (WITH_HTTP_DOWNLOAD  "Use provided math implementation" ON)
# option (WITH_AVS_COAP_UDP  "Use provided math implementation" ON)
# option (ANJAY_WITH_HTTP_DOWNLOAD "Use provided math implementation" ON)
