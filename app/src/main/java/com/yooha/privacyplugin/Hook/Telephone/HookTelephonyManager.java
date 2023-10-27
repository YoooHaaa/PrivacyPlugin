package com.yooha.privacyplugin.Hook.Telephone;

import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;
import java.util.ArrayList;
import java.util.Arrays;
import de.robv.android.xposed.XposedHelpers;


public class HookTelephonyManager extends HookMethods {
    private int mCountgetDeviceId = 0;
    private int mCountgetDeviceId_I = 0;
    private int mCountgetImei = 0;
    private int mCountgetImei_I = 0;
    private int mCountgetMeid = 0;
    private int mCountgetMeid_I = 0;
    private int mCountgetSubscriberId = 0;
    private int mCountgetSimSerialNumber = 0;
    private int mCountgetLine1Number = 0;
    private int mCountgetNetworkOperator = 0;
    private int mCountgetNetworkOperatorName = 0;
    private int mCountgetPhoneType = 0;
    private int mCountgetNetworkType = 0;
    private int mCountgetCellLocation = 0;
    private int mCountlisten = 0;



    public HookTelephonyManager(ClassLoader loader, String pkg, int level){
        super( "android.telephony.TelephonyManager", loader, pkg, level);
    }

    public HookTelephonyManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    public void hookMethod(){
        try{
            getDeviceId();
            getImei();
            getMeid();
            getSubscriberId();
            getSimSerialNumber();
            getLine1Number();
//            getNetworkOperator();
//            getNetworkOperatorName();
//            getPhoneType();
//            getNetworkType();
            getCellLocation();
            //listen();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }


    public void getDeviceId(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDeviceId",
                new YH_MethodHook("android.telephony.TelephonyManager -> getDeviceId():获取IMEI"){
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                try{
                    mCountgetDeviceId++;
                    String ret = (String)param.getResult();
                    String stack = LogUtil.getStackTrace();
                    HookMessage msg = new HookMessage.Builder()
                            .setLevel(Data.LEVEL_SENSITIVE)
                            .setClass(mCls)
                            .setMethod("getDeviceId")
                            .setMethodSignature("()")
                            .setReturn(ret)
                            .setStackTrace(stack)
                            .setCount(mCountgetDeviceId)
                            .setDescription("获取设备ID")
                            .build();
                    MsgSender.addMsg(msg);
                }catch (Exception e){
                    LogUtil.error(e.toString());
                }
            }
        });

        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDeviceId",
                int.class,
                new YH_MethodHook("android.telephony.TelephonyManager -> getDeviceId(int slotIndex):获取IMEI"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetDeviceId_I++;
                            int param1 = (int)param.args[0];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getDeviceId")
                                    .setMethodSignature("(int slotIndex)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetDeviceId_I)
                                    .setDescription("获取设备ID")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getImei(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getImei",
                new YH_MethodHook("android.telephony.TelephonyManager -> getImei():获取设备IMEI"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetImei++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getImei")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetImei)
                                    .setDescription("获取设备IMEI")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });

        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getImei",
                int.class,
                new YH_MethodHook("android.telephony.TelephonyManager -> getImei(int slotIndex ):获取设备IMEI"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetImei_I++;
                            int param1 = (int)param.args[0];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getImei")
                                    .setMethodSignature("(int slotIndex)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetImei_I)
                                    .setDescription("获取设备IMEI")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getMeid(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getMeid",
                new YH_MethodHook("android.telephony.TelephonyManager -> getMeid():获取MEID"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetMeid++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getMeid")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetMeid)
                                    .setDescription("获取MEID")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });

        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getMeid",
                int.class,
                new YH_MethodHook("android.telephony.TelephonyManager -> getMeid(int slotIndex):获取MEID"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetMeid_I++;
                            int param1 = (int)param.args[0];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getMeid")
                                    .setMethodSignature("(int slotIndex)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetMeid_I)
                                    .setDescription("获取MEID")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getSubscriberId(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getSubscriberId",
                new YH_MethodHook("android.telephony.TelephonyManager -> getSubscriberId():获取IMSI"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetSubscriberId++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getSubscriberId")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSubscriberId)
                                    .setDescription("获取IMSI")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getSimSerialNumber(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getSimSerialNumber",
                new YH_MethodHook("android.telephony.TelephonyManager -> getSimSerialNumber():获取SIM卡的序列号"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetSimSerialNumber++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getSimSerialNumber")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSimSerialNumber)
                                    .setDescription("获取SIM卡的序列号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getLine1Number(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLine1Number",
                new YH_MethodHook("android.telephony.TelephonyManager -> getLine1Number():获取手机号"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLine1Number++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(Data.LEVEL_SENSITIVE)
                                    .setClass(mCls)
                                    .setMethod("getLine1Number")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLine1Number)
                                    .setDescription("获取手机号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getNetworkOperator(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getNetworkOperator",
                new YH_MethodHook("android.telephony.TelephonyManager -> getNetworkOperator ():获取网络运营商"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetNetworkOperator++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getNetworkOperator")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetNetworkOperator)
                                    .setDescription("获取网络运营商")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getNetworkOperatorName(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getNetworkOperatorName",
                new YH_MethodHook("android.telephony.TelephonyManager -> getNetworkOperatorName ():获取网络运营商名称"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetNetworkOperatorName++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getNetworkOperatorName")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetNetworkOperatorName)
                                    .setDescription("获取网络运营商名称")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getPhoneType(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getPhoneType",
                new YH_MethodHook("android.telephony.TelephonyManager -> getPhoneType ():获取设备支持的网络类型"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetPhoneType++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getPhoneType")
                                    .setMethodSignature("()")
                                    .setReturn(PhoneTypeToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetPhoneType)
                                    .setDescription("获取设备支持的网络类型")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
    public String PhoneTypeToString(int type){
        switch (type){
            case 2:
                return "PHONE_TYPE_CDMA";
            case 1:
                return "PHONE_TYPE_GSM";
            case 0:
                return "PHONE_TYPE_NONE";
            case 3:
                return "PHONE_TYPE_NONE";
            default: break;
        }
        return "null";
    }

    public void getNetworkType(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getNetworkType",
                new YH_MethodHook("android.telephony.TelephonyManager -> getNetworkType():获取可用的网络类型"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetNetworkType++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getNetworkType")
                                    .setMethodSignature("()")
                                    .setReturn(NetworkTypeToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetNetworkType)
                                    .setDescription("获取可用的网络类型")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
    public String NetworkTypeToString(int type){
        switch (type){
            case 0:
                return "NETWORK_TYPE_UNKNOWN";
            case 1:
                return "NETWORK_TYPE_GPRS";
            case 2:
                return "NETWORK_TYPE_EDGE";
            case 3:
                return "NETWORK_TYPE_UMTS";
            case 4:
                return "NETWORK_TYPE_CDMA";
            case 5:
                return "NETWORK_TYPE_EVDO_0";
            case 6:
                return "NETWORK_TYPE_EVDO_A";
            case 7:
                return "NETWORK_TYPE_1xRTT";
            case 8:
                return "NETWORK_TYPE_HSDPA";
            case 9:
                return "NETWORK_TYPE_HSUPA";
            case 10:
                return "NETWORK_TYPE_HSPA";
            case 11:
                return "NETWORK_TYPE_IDEN";
            case 12:
                return "NETWORK_TYPE_EVDO_B";
            case 13:
                return "NETWORK_TYPE_LTE";
            case 14:
                return "NETWORK_TYPE_EHRPD";
            case 15:
                return "NETWORK_TYPE_HSPAP";
            case 16:
                return "NETWORK_TYPE_GSM";
            case 17:
                return "NETWORK_TYPE_TD_SCDMA";
            case 18:
                return "NETWORK_TYPE_IWLAN";
            case 20:
                return "NETWORK_TYPE_NR";
            default: break;
        }
        return "null";
    }

    public void getCellLocation(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getCellLocation",
                new YH_MethodHook("android.telephony.TelephonyManager -> getCellLocation():获取位置信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetCellLocation++;
                            CellLocation ret = (CellLocation)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getCellLocation")
                                    .setMethodSignature("()")
                                    .setReturn(String.valueOf(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetCellLocation)
                                    .setDescription("获取位置信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void listen(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "listen",
                PhoneStateListener.class,
                int.class,
                new YH_MethodHook("android.telephony.TelephonyManager -> listen(PhoneStateListener listener, int events):注册手机通话状态监听器"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountlisten++;
                            CellLocation ret = (CellLocation)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("listen")
                                    .setMethodSignature("(PhoneStateListener listener, int events)")
                                    .setReturn(String.valueOf(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountlisten)
                                    .setDescription("注册手机通话状态监听器")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

}
