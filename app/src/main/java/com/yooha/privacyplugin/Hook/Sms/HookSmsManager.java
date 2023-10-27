package com.yooha.privacyplugin.Hook.Sms;

import android.app.PendingIntent;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class HookSmsManager extends HookMethods {
    public int mCountsendTextMessage = 0;
    public int mCountsendMultipartTextMessageInternal = 0;
    public int mCountsendDataMessage = 0;

    public HookSmsManager(ClassLoader loader, String pkg, int level){
        super("android.telephony.SmsManager", loader, pkg, level);
    }

    public HookSmsManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    @Override
    public void hookMethod(){
        try{
            sendTextMessage();
            sendMultipartTextMessageInternal();
            sendDataMessage();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    public void sendTextMessage(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "sendTextMessage",
                String.class,
                String.class,
                String.class,
                PendingIntent.class,
                PendingIntent.class,
                int.class,
                boolean.class,
                int.class,
                new YH_MethodHook("android.telephony.SmsManager -> sendTextMessage():发送短信"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsendTextMessage++;
                            String param1 = (String)param.args[0];
                            String param2 = (String)param.args[1];
                            String param3 = (String)param.args[2];
                            ArrayList<String> params=new ArrayList<String>();
                            params.add(param1);
                            params.add(param2);
                            params.add(param3);
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("sendTextMessage")
                                    .setMethodSignature("String destinationAddress, String scAddress, String text,PendingIntent sentIntent, PendingIntent deliveryIntent,int priority, boolean expectMore, int validityPeriod")
                                    .setParams(params)
                                    .setReturn("null")
                                    .setStackTrace(stack)
                                    .setCount(mCountsendTextMessage)
                                    .setDescription("发送短信")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });

        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "sendTextMessage",
                String.class,
                String.class,
                String.class,
                PendingIntent.class,
                PendingIntent.class,
                new YH_MethodHook("android.telephony.SmsManager -> sendTextMessage():发送短信"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsendTextMessage++;
                            String param1 = (String)param.args[0];
                            String param2 = (String)param.args[1];
                            String param3 = (String)param.args[2];
                            ArrayList<String> params=new ArrayList<String>();
                            params.add(param1);
                            params.add(param2);
                            params.add(param3);
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("sendTextMessage")
                                    .setMethodSignature("String destinationAddress, String scAddress, String text,PendingIntent sentIntent, PendingIntent deliveryIntent")
                                    .setParams(params)
                                    .setReturn("null")
                                    .setStackTrace(stack)
                                    .setCount(mCountsendTextMessage)
                                    .setDescription("发送短信")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void sendMultipartTextMessageInternal(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "sendMultipartTextMessageInternal",
                String.class,
                String.class,
                List.class,
                List.class,
                List.class,
                boolean.class,
                int.class,
                boolean.class,
                int.class,
                new YH_MethodHook("android.telephony.SmsManager -> sendMultipartTextMessageInternal():发送多条短信"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsendMultipartTextMessageInternal++;
                            String param1 = (String)param.args[0];
                            String param2 = (String)param.args[1];
                            ArrayList<String> params=new ArrayList<String>();
                            params.add(param1);
                            params.add(param2);
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("sendMultipartTextMessageInternal")
                                    .setMethodSignature("String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessage, int priority, boolean expectMore, int validityPeriod")
                                    .setParams(params)
                                    .setReturn("null")
                                    .setStackTrace(stack)
                                    .setCount(mCountsendMultipartTextMessageInternal)
                                    .setDescription("发送多条短信")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });

        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "sendMultipartTextMessageInternal",
                String.class,
                String.class,
                List.class,
                List.class,
                List.class,
                boolean.class,
                String.class,
                new YH_MethodHook("android.telephony.SmsManager -> sendMultipartTextMessageInternal():发送多条短信"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsendMultipartTextMessageInternal++;
                            String param1 = (String)param.args[0];
                            String param2 = (String)param.args[1];
                            ArrayList<String> params = new ArrayList<String>();
                            params.add(param1);
                            params.add(param2);
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("sendMultipartTextMessageInternal")
                                    .setMethodSignature("String destinationAddress, String scAddress, List<String> parts, List<PendingIntent> sentIntents, List<PendingIntent> deliveryIntents, boolean persistMessage, int priority, boolean expectMore, int validityPeriod")
                                    .setParams(params)
                                    .setReturn("null")
                                    .setStackTrace(stack)
                                    .setCount(mCountsendMultipartTextMessageInternal)
                                    .setDescription("发送多条短信")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void sendDataMessage(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "sendDataMessage",
                String.class,
                String.class,
                short.class,
                byte[].class,
                PendingIntent.class,
                PendingIntent.class,
                new YH_MethodHook("android.telephony.SmsManager -> sendDataMessage():发送文本短信"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsendDataMessage++;
                            String param1 = (String)param.args[0];
                            String param2 = (String)param.args[1];
                            ArrayList<String> params=new ArrayList<String>();
                            params.add(param1);
                            params.add(param2);
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("sendDataMessage")
                                    .setMethodSignature("String destinationAddress, String scAddress, short destinationPort, byte[] data, PendingIntent sentIntent, PendingIntent deliveryIntent")
                                    .setParams(params)
                                    .setReturn("null")
                                    .setStackTrace(stack)
                                    .setCount(mCountsendDataMessage)
                                    .setDescription("发送文本短信")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });


    }
}
