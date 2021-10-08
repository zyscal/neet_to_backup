cmake_minimum_required(VERSION 3.1)

project(anjay-bc6 C)

set(CMAKE_C_STANDARD 99)
set(CMAKE_C_EXTENSIONS OFF)

find_package(anjay REQUIRED)



add_executable(${PROJECT_NAME}
               src/main.c
               src/time_object.h
               src/time_object.c
               src/XCBR_Pos_34828.c
               src/XCBR_Pos_34828.h
               src/firmware_update.c
               src/firmware_update.h
               )
find_package(Threads)
target_link_libraries(${PROJECT_NAME} PRIVATE anjay ${CMAKE_THREAD_LIBS_INIT})


