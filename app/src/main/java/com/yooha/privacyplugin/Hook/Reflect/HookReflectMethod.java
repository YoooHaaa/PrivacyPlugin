package com.yooha.privacyplugin.Hook.Reflect;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;


import de.robv.android.xposed.XposedHelpers;

public class HookReflectMethod extends HookMethods {

    public HookReflectMethod(ClassLoader loader, String pkg, int level){
        super( "java.lang.reflect.Method", loader, pkg, level);
    }

    public HookReflectMethod(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            invoke(); //由于该方法的使用较频繁，hook可能导致应用崩溃
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void invoke() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "invoke",
                Object.class,
                Object[].class,
                new YH_MethodHook("java.lang.reflect.Method -> invoke(Object obj, Object args):反射调用方法"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String method = (String)param.thisObject;
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("invoke(" + method + ")")
                                    .setMethodSignature("(Object obj, Object args)")
                                    .setStackTrace(stack)
                                    .setDescription("反射调用方法")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

}
