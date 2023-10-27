package com.yooha.privacyplugin.Hook.Other;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookActivityThread  extends HookMethods {
        public int mCountgetHostAddress = 0;
        public int mCountgetHostName = 0;

        public HookActivityThread(ClassLoader loader, String pkg, int level){
            super( "android.app.ActivityThread", loader, pkg, level);
        }

        public HookActivityThread(ClassLoader loader, String pkg){
            this(loader, pkg, Data.LEVEL_GENERAL);
        }

        public void hookMethod(){
            try{
                handleBindApplication();

            }catch (Exception e){
                LogUtil.error(e.toString());
            }

        }

        public void handleBindApplication(){
            XposedHelpers.findAndHookMethod(this.mCls,
                    this.mLoader,
                    "handleBindApplication",
                    new YH_MethodHook("android.app.ActivityThread -> handleBindApplication():应用正在启动"){
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            try{
                                mCountgetHostAddress++;
                                String ret = (String)param.getResult();
                                String stack = LogUtil.getStackTrace();
                                HookMessage msg = new HookMessage.Builder()
                                        .setLevel(mLevel)
                                        .setClass(mCls)
                                        .setMethod("handleBindApplication")
                                        .setMethodSignature("()")
                                        .setReturn(ret)
                                        .setStackTrace(stack)
                                        .setCount(mCountgetHostAddress)
                                        .build();
                                MsgSender.addMsg(msg);
                            }catch (Exception e){
                                LogUtil.error(e.toString());
                            }
                        }
                    });
        }




}
