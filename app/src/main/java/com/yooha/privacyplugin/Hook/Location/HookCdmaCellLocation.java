package com.yooha.privacyplugin.Hook.Location;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;


import de.robv.android.xposed.XposedHelpers;

public class HookCdmaCellLocation extends HookMethods {
    public int mCountgetBaseStationId = 0;
    public int mCountgetNetworkId = 0;
    public int mCountgetSystemId = 0;
    public int mCountgetBaseStationLatitude = 0;
    public int mCountgetBaseStationLongitude = 0;

    public HookCdmaCellLocation(ClassLoader loader, String pkg, int level){
        super( "android.telephony.cdma.CdmaCellLocation", loader, pkg, level);
    }

    public HookCdmaCellLocation(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_SENSITIVE);
    }

    @Override
    protected void hookMethod() {

        try{
//            getBaseStationId();
//            getNetworkId();
//            getSystemId();
            getBaseStationLatitude();
            getBaseStationLongitude();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void getBaseStationId() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getBaseStationId",
                new YH_MethodHook("android.telephony.cdma.CdmaCellLocation -> getBaseStationId():获取CDMA基站识别号"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetBaseStationId++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getBaseStationId")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetBaseStationId)
                                    .setDescription("获取CDMA基站识别号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getNetworkId() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getNetworkId",
                new YH_MethodHook("android.telephony.cdma.CdmaCellLocation -> getNetworkId():获取CDMA网络识别号"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetNetworkId++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getNetworkId")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetNetworkId)
                                    .setDescription("获取CDMA网络识别号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getSystemId() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getSystemId",
                new YH_MethodHook("android.telephony.cdma.CdmaCellLocation -> getSystemId():获取CDMA系统识别号"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetSystemId++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getSystemId")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSystemId)
                                    .setDescription("获取CDMA系统识别号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getBaseStationLatitude() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getBaseStationLatitude",
                new YH_MethodHook("android.telephony.cdma.CdmaCellLocation -> getBaseStationLatitude():获取CDMA基站纬度"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetBaseStationLatitude++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getBaseStationLatitude")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetBaseStationLatitude)
                                    .setDescription("获取CDMA基站纬度")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getBaseStationLongitude () {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getBaseStationLongitude",
                new YH_MethodHook("android.telephony.cdma.CdmaCellLocation -> getBaseStationLongitude ():获取CDMA基站经度"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetBaseStationLongitude++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getBaseStationLongitude ")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetBaseStationLongitude)
                                    .setDescription("获取CDMA基站经度")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
