package com.yooha.privacyplugin.Hook.Other;

import android.content.Intent;
import android.os.Bundle;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;



public class HookActivity  extends HookMethods {
    public int mCountgetHostAddress = 0;
    public int mCountgetHostName = 0;

    public HookActivity(ClassLoader loader, String pkg, int level){
        super( "android.app.Activity", loader, pkg, level);
    }

    public HookActivity(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    public void hookMethod(){
        try{
            startActivity();
            startActivity_();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void startActivity(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "startActivity",
                Intent.class,
                new YH_MethodHook("android.app.Activity -> Activity():应用正在启动Activity"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetHostAddress++;
                            Intent param1 = (Intent)param.args[0];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("startActivity")
                                    .setMethodSignature("()")
                                    .setParams(new ArrayList(Arrays.asList(param1.toString())))
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

    public void startActivity_(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "startActivity",
                Intent.class,
                Bundle.class,
                new YH_MethodHook("android.app.Activity -> Activity():应用正在启动Activity"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetHostAddress++;
                            Intent param1 = (Intent)param.args[0];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("startActivity")
                                    .setMethodSignature("()")
                                    .setParams(new ArrayList(Arrays.asList(param1.toString())))
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

