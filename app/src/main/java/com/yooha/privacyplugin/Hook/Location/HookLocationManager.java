package com.yooha.privacyplugin.Hook.Location;

import android.location.Location;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookLocationManager extends HookMethods {
    public int mCountgetLastKnownLocation = 0;

    public HookLocationManager(ClassLoader loader, String pkg, int level){
        super( "android.location.LocationManager", loader, pkg, level);
    }

    public HookLocationManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            getLastKnownLocation();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public String LocationToString(Location lc){
        if (lc == null){
            return "null";
        }
        try{
            double latitude = lc.getLatitude();//纬度
            double longitude = lc.getLongitude();//经度
            return "<" + latitude + ", " + longitude + ">";
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
        return "null";
    }

    public void getLastKnownLocation(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLastKnownLocation",
                String.class,
                new YH_MethodHook("android.location.LocationManager -> getLastKnownLocation(String provider):获取GPS地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLastKnownLocation++;
                            String param1 = (String)param.args[0];
                            Location ret = (Location)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLastKnownLocation")
                                    .setMethodSignature("(String provider)")
                                    .setReturn(LocationToString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1)))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLastKnownLocation)
                                    .setDescription("获取GPS地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
