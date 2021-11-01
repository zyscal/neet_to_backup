#ifndef XCBR_POS_34828_H
#define XCBR_POS_34828_H

#include <anjay/dm.h>
#include<pthread.h>
const anjay_dm_object_def_t **xcbr_pos_object_create(void);
void xcbr_pos_object_release(const anjay_dm_object_def_t **def);
void xcbr_pos_object_notify(anjay_t *anjay, const anjay_dm_object_def_t **def);
int xcbr_pos_object_change_stval(int iid, anjay_dm_object_def_t **obj_ptr, int num);
pthread_mutex_t xcbr_mutex;
#endif // XCBR_POS_34828_H
