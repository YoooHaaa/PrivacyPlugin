package com.yooha.privacyplugin.Hook.Reflect;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;


public class HookField extends HookMethods {

    public HookField(ClassLoader loader, String pkg, int level){
        super( "java.lang.reflect.Field", loader, pkg, level);
    }

    public HookField(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            get();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void get() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "get",
                Object.class,
                new YH_MethodHook("java.lang.reflect.Field -> get(Object obj):获取字段对象"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            Object param1 = (Object)param.args[0];
                            Object ret = (Object)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("get")
                                    .setMethodSignature("(Object obj)")
                                    .setParams(new ArrayList(Arrays.asList(param1.toString())))
                                    .setReturn(ret.toString())
                                    .setStackTrace(stack)
                                    .setDescription("获取字段对象")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

}
