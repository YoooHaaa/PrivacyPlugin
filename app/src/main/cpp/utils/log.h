//
// Created by SwiftGan on 2019/2/15.
//

#pragma once



#include "android/log.h"
#include <jni.h>


#define TAG "A-Privacy"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__)

#define UNEXPORT __attribute__ ((visibility("hidden")))
#define INIT_ARRAY UNEXPORT __attribute__ ((constructor))
#define NAKED __attribute__((naked)) // 裸函数,编译器不会生成任何函数入口代码和退出代码

