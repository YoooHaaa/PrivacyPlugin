package com.yooha.privacyplugin.Hook.Sensor;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class HookSensorManager extends HookMethods {

    public int mCountgetSensorList = 0;
    public int mCountregisterListener = 0;

    public HookSensorManager(ClassLoader loader, String pkg, int level){
        super("android.hardware.SensorManager", loader, pkg, level);
    }

    public HookSensorManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    @Override
    public void hookMethod(){
        try{
            //getSensorList();
            registerListener();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    public void getSensorList(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getSensorList",
                int.class,
                new YH_MethodHook("android.hardware.SensorManager -> getSensorList():获取传感器列表"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetSensorList++;
                            int param1 = (int)param.args[0];
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getSensorList")
                                    .setMethodSignature("(int type)")
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setReturn(String.valueOf(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSensorList)
                                    .setDescription("获取传感器列表")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void registerListener(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "registerListener",
                SensorEventListener.class,
                Sensor.class,
                int.class,
                new YH_MethodHook("android.hardware.SensorManager -> registerListener():注册传感器监听"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountregisterListener++;
                            String param1 = ((SensorEventListener) param.args[0]).toString();
                            String param2 = ((Sensor) param.args[1]).getName();
                            String param3 = Integer.toString((int) param.args[2]);
//                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("registerListener")
                                    .setMethodSignature("(SensorEventListener listener, Sensor sensor, int samplingPeriodUs)")
                                    .setParams(new ArrayList(Arrays.asList(param1,param2,param3)))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSensorList)
                                    .setDescription("注册传感器监听")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
