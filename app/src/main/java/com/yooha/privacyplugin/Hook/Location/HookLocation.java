package com.yooha.privacyplugin.Hook.Location;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookLocation extends HookMethods {
    public int mCountgetLatitude = 0;

    public HookLocation(ClassLoader loader, String pkg, int level){
        super( "android.location.Location", loader, pkg, level);
    }

    public HookLocation(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_SENSITIVE);
    }

    @Override
    protected void hookMethod() {

        try{
            getLatitude();
            getLongitude();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }
    private void getLatitude() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLatitude",
                new YH_MethodHook("android.location.Location -> getLatitude():获取纬度信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLatitude++;
                            double ret = (double)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLatitude")
                                    .setMethodSignature("()")
                                    .setReturn(Double.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLatitude)
                                    .setDescription("获取纬度信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
    private void getLongitude() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLongitude",
                new YH_MethodHook("android.location.Location -> getLongitude():获取经度信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLatitude++;
                            double ret = (double)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLongitude")
                                    .setMethodSignature("()")
                                    .setReturn(Double.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLatitude)
                                    .setDescription("获取经度信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
