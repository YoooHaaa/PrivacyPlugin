#include <jni.h>
#include <string>
#include "sandhook_native.h"
#include <unistd.h>
#include <cassert>
#include <cstdlib>
#include "utils/log.h"
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <stdio.h>
#include "stack/stack.h"

extern "C" void _init(void) {
    //LOGI("go into _init");
}

INIT_ARRAY void init(void){
    //LOGI("go into init_array");
}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++ open start +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
int (*old_open)(const char *, int, ...) = nullptr;
int (*open_addr)(const char *, int, ...) = nullptr;
int new_open(const char *arg0, int arg1, ...) {
    va_list args;
    va_start(args, arg1);
    int mode = va_arg(args, int);
    va_end(args);
    LOGI("open path -> %s ", arg0);
    return old_open(arg0, arg1, mode);
}

void starthooklibcopen() {
    open_addr = open;
    old_open = reinterpret_cast<int (*)(const char *, int, ...)>(SandInlineHook(reinterpret_cast<void *>(open_addr), reinterpret_cast<void *>(new_open)));
}
//--------------------------------------------------------- open end ---------------------------------------------------------

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++ popen start +++++++++++++++++++++++++++++++++++++++++++++++++++++++++

FILE* (*old_popen)(const char *, const char *) = nullptr;
FILE* (*popen_addr)(const char *, const char *) = nullptr;
FILE* new_popen(const char *arg0, const char * arg1) {
    native_log("popen", arg0);
    return old_popen(arg0, arg1);
}

void starthooklibcpopen() {
    popen_addr = popen;
    old_popen = reinterpret_cast<FILE *(*)(const char *, const char *)>(SandInlineHook(reinterpret_cast<void *>(popen_addr),
                                                        reinterpret_cast<void *>(new_popen)));
}
//--------------------------------------------------------- popen end ---------------------------------------------------------

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++ Sub_1271 end +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
typedef int (*type_sub_1271)(int a1, int a2, int r2_0, int r3_0);
type_sub_1271 hookfunction_backup = nullptr;

int new_sub_1271(int a1, int a2, int a3, int a4) {
    return hookfunction_backup(a1, a2, a3, a4);
}

void startHookLibnativeSub_1271(){
    //得到模块基址
    void *libnativebase = SandGetModuleBase("libnative.so");

    //hook sub_1271 函数
    unsigned long funaddr = (unsigned long) libnativebase + 0x1271 + 1;
    hookfunction_backup = reinterpret_cast<type_sub_1271>(SandInlineHook(reinterpret_cast<void *>(funaddr), reinterpret_cast<void *>(new_sub_1271)));
}
//--------------------------------------------------------- Sub_1271 end ---------------------------------------------------------


extern "C" jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    //LOGD("go into JNI_OnLoad");
    //starthooklibcopen();
    //startHookLibnativeSub_1271();
    starthooklibcpopen();
    return JNI_VERSION_1_6;
}
