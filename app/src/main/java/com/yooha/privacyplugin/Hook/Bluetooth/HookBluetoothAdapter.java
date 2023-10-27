package com.yooha.privacyplugin.Hook.Bluetooth;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;




import de.robv.android.xposed.XposedHelpers;

public class HookBluetoothAdapter  extends HookMethods {
    public int mCountgetName = 0;
    public int mCountgetState = 0;
    public int mCountgetAddress = 0;
    public int mCountenable = 0;
    public int mCountdisable = 0;

    public HookBluetoothAdapter(ClassLoader loader, String pkg, int level){
        super( "android.bluetooth.BluetoothAdapter", loader, pkg, level);
    }

    public HookBluetoothAdapter(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
//            getName();
//            getState();
            getAddress();
            enable();
            disable();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    public void getName(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getName",
                new YH_MethodHook("android.bluetooth.BluetoothAdapter -> getName():获取蓝牙设备名称"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetName++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getName")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetName)
                                    .setDescription("获取蓝牙设备名称")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getState(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getState",
                new YH_MethodHook("android.bluetooth.BluetoothAdapter -> getState():获取本地蓝牙适配器状态"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetState++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getState")
                                    .setMethodSignature("()")
                                    .setReturn(parseState(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetState)
                                    .setDescription("获取本地蓝牙适配器状态")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public String parseState(int state){
        switch (state){
            case 0:
                return "STATE_DISCONNECTED";
            case 1:
                return "STATE_CONNECTING";
            case 2:
                return "STATE_CONNECTED";
            case 3:
                return "STATE_DISCONNECTING";
            case 10:
                return "STATE_OFF";
            case 11:
                return "STATE_TURNING_ON";
            case 12:
                return "STATE_ON";
            case 13:
                return "STATE_TURNING_OFF";
            default:break;
        }
        return "null";
    }

    public void getAddress (){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getAddress",
                new YH_MethodHook("android.bluetooth.BluetoothAdapter -> getAddress ():获取本地蓝牙适配器的硬件地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetAddress++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getAddress")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetAddress)
                                    .setDescription("获取本地蓝牙适配器的硬件地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void enable (){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "enable",
                new YH_MethodHook("android.bluetooth.BluetoothAdapter -> enable ():打开蓝牙"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountenable++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("enable")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountenable)
                                    .setDescription("打开蓝牙")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void disable (){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "disable",
                new YH_MethodHook("android.bluetooth.BluetoothAdapter -> disable ():关闭蓝牙"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountdisable++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("disable")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountdisable)
                                    .setDescription("关闭蓝牙")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
