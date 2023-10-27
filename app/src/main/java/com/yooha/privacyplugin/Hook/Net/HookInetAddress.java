package com.yooha.privacyplugin.Hook.Net;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookInetAddress extends HookMethods {
    public int mCountgetHostAddress = 0;
    public int mCountgetHostName = 0;

    public HookInetAddress(ClassLoader loader, String pkg, int level){
        super( "java.net.Inet4Address", loader, pkg, level);
    }

    public HookInetAddress(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
        //InetAddress
    }


    public void hookMethod(){
        try{
            getHostAddress();
            getAddress();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void getHostAddress(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getHostAddress",
                new YH_MethodHook("java.net.Inet4Address -> getHostAddress():获取IP地址"){
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
                                    .setMethod("getHostAddress ")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetHostAddress)
                                    .setDescription("获取IP地址")
                                    .build();
                            if (!stack.contains("libcore.io.Linux.connect"))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }


    public void getAddress(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getAddress",
                new YH_MethodHook("java.net.Inet4Address -> getAddress():获取原始IP地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetHostName++;
                            byte[] ret = (byte[])param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getAddress")
                                    .setMethodSignature("()")
                                    .setReturn(bytesToHexString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetHostName)
                                    .setDescription("获取原始IP地址")
                                    .build();
                            if (!stack.contains("libcore.io.Linux.connect"))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null){
            return "null";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString().toUpperCase();
    }
}
