package com.yooha.privacyplugin.Hook.Media;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;


import de.robv.android.xposed.XposedHelpers;

public class HookMediaDrm extends HookMethods{
    public int mCountgetPropertyByteArray = 0;

    public HookMediaDrm(ClassLoader loader, String pkg, int level){
        super("android.media.MediaDrm", loader, pkg, level);
    }

    public HookMediaDrm(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    @Override
    public void hookMethod(){
        try{
            getPropertyByteArray();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    public void getPropertyByteArray(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getPropertyByteArray",
                String.class,
                new YH_MethodHook("android.media.MediaDrm -> getPropertyByteArray():获取字节属性值"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetPropertyByteArray++;
                            String param1 = (String)param.args[0];
                            if (param1.equals("deviceUniqueId")){
                                byte[] ret = (byte[])param.getResult();
                                String stack = LogUtil.getStackTrace();
                                HookMessage msg = new HookMessage.Builder()
                                        .setLevel(mLevel)
                                        .setClass(mCls)
                                        .setMethod("getPropertyByteArray")
                                        .setMethodSignature("(String propertyName)")
                                        .setReturn(bytesToHexString(ret))
                                        .setStackTrace(stack)
                                        .setCount(mCountgetPropertyByteArray)
                                        .setDescription("获取设备唯一属性（水印ID）")
                                        .build();
                                MsgSender.addMsg(msg);
                            }
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



