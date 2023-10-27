//
// Created by Administrator on 2023/10/23.
//

#include "../utils/log.h"
#include "stack.h"
#include <jni.h>
#include <string>
#include <unistd.h>
#include <cassert>
#include <cstdlib>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <cstring>
#include <cstdio>
#include "android/log.h"
#include <jni.h>
#include <string>
#include <dlfcn.h>
#include <unwind.h>

using namespace std;


struct BacktraceState{
    void** current;
    void** end;
};

_Unwind_Reason_Code unwindCallback(struct _Unwind_Context* context, void* arg){
    BacktraceState* state = static_cast<BacktraceState*>(arg);
    uintptr_t pc = _Unwind_GetIP(context);
    if (pc) {
        if (state->current == state->end) {
            return _URC_END_OF_STACK;
        } else {
            *state->current++ = reinterpret_cast<void *>(pc);
        }
    }
    return _URC_NO_REASON;
}


int captureBacktrace(void** buffer, int max){
    BacktraceState state = {buffer, buffer + max};
    _Unwind_Backtrace(unwindCallback, &state);
    return state.current - buffer;
}

void fillBacktrace(string& os, void** buffer, int count){
    for (int idx = 0; idx < count; ++idx) {
        const void* addr = buffer[idx];
        const char* symbol = "";
        Dl_info info;
        if (dladdr(addr, &info) && info.dli_sname) {
            symbol = info.dli_sname;
        }
        char buf[256] = {0};
        sprintf(buf, "#  %d: 0x%p %s\n", idx, addr, symbol);
        os.append(buf);
    }
}

void backtraceToLogcat(string& os){
    const int max = 30; // 调用的层数
    void* buffer[max];
    fillBacktrace(os, buffer, captureBacktrace(buffer, max));
}

void native_log(const char* method, const char* param){
    string os;
    char buf[256] = {0};
    sprintf(buf, ">>> Native  -> %s\nparam       -> <%s>\n", method, param);
    backtraceToLogcat(os);
    LOGI(buf);
    LOGI("StackTrace  -> ");
    LOGI(os.c_str());
    LOGI("\n\n");
}