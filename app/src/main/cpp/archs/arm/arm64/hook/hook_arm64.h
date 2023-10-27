//
// Created by swift on 2019/5/23.
//

#pragma once

#include "hook.h"
#include <vector>
#include <sys/mman.h>
#include <cerrno>
#include <unistd.h>

namespace SandHook {
    namespace Hook {
        class InlineHookArm64Android : public InlineHook {
        public:
            void *Hook(void *origin, void *replace) override;

            bool BreakPoint(void *point, void (*callback)(REG[])) override;

            bool SingleBreakPoint(void *point, BreakCallback callback, void *data) override;

            void *SingleInstHook(void *origin, void *replace) override;

            bool ExceptionHandler(int num, sigcontext *context) override;

        private:
            std::vector<HookInfo> hook_infos;
        };
    }
}
