package com.yooha.privacyplugin.Hook.Camera;

import android.hardware.Camera;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookCamera extends HookMethods {
    public int mCountopen = 0;
    public int mCountopen_I = 0;

    public HookCamera(ClassLoader loader, String pkg, int level){
        super( "android.hardware.Camera", loader, pkg, level);
    }

    public HookCamera(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            open();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void open(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "open",
                new YH_MethodHook("android.hardware.Camera -> open():调用摄像头"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountopen++;
                            Camera ret = (Camera)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("open")
                                    .setMethodSignature("()")
                                    .setReturn(ret.toString())
                                    .setStackTrace(stack)
                                    .setCount(mCountopen)
                                    .setDescription("调用摄像头")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "open",
                int.class,
                new YH_MethodHook("android.hardware.Camera -> open(int cameraId):调用摄像头"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountopen_I++;
                            int param1 = (int)param.args[0];
                            Camera ret = (Camera)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("open")
                                    .setMethodSignature("(int cameraId)")
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setReturn(ret.toString())
                                    .setStackTrace(stack)
                                    .setCount(mCountopen_I)
                                    .setDescription("调用摄像头")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

}
