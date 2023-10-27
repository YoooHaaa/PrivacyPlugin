package com.yooha.privacyplugin.Hook.Other;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookContextIml extends HookMethods {
    public int mCountgetRegisterSMS = 0;

    public HookContextIml(ClassLoader loader, String pkg, int level){
        super( "android.app.ContextImpl", loader, pkg, level);
    }

    public HookContextIml(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    public void hookMethod(){
        try{
            registerReceiver();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void registerReceiver(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "registerReceiver",
                BroadcastReceiver.class,
                IntentFilter.class,
                new YH_MethodHook("android.app.ContextImpl -> ContextImpl():应用正在注册短信监听广播"){

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            BroadcastReceiver param1 = (BroadcastReceiver) param.args[0];
                            IntentFilter param2 = (IntentFilter) param.args[1];
                            String receiver = param1.getClass().getName();
                            String action = param2.getAction(0);
                            if(!action.equals("android.provider.Telephony.SMS_RECEIVED")){
                                return;
                            }
                            mCountgetRegisterSMS++;
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("registerReceiver")
                                    .setMethodSignature("BroadcastReceiver broadcastReceiver, IntentFilter intentFilter)")
                                    .setParams(new ArrayList(Arrays.asList(receiver,action)))
                                    .setReturn(null)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetRegisterSMS)
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }


}
