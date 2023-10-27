package com.yooha.privacyplugin.Hook.Bluetooth;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookBluetoothDevice  extends HookMethods {
    public int mCountgetName = 0;
    public int mCountgetBondState = 0;
    public int mCountgetAddress = 0;

    public HookBluetoothDevice(ClassLoader loader, String pkg, int level){
        super( "android.bluetooth.BluetoothDevice", loader, pkg, level);
    }

    public HookBluetoothDevice(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            // getName();
            getAddress();
            // getBondState();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void getName(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getName",
                new YH_MethodHook("android.bluetooth.BluetoothDevice -> getName():获取远程设备的友好蓝牙名称"){
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
                                    .setDescription("获取远程设备的友好蓝牙名称")
                                    .build();
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
                new YH_MethodHook("android.bluetooth.BluetoothDevice -> getAddress():获取蓝牙设备的硬件地址"){
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
                                    .setDescription("获取蓝牙设备的硬件地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }


    public void getBondState (){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getBondState",
                new YH_MethodHook("android.bluetooth.BluetoothDevice -> getBondState():获取远程设备的绑定状态"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetBondState++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getBondState")
                                    .setMethodSignature("()")
                                    .setReturn(parseState(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetBondState)
                                    .setDescription("获取远程设备的绑定状态")
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
            case 10:
                return "BOND_NONE";
            case 11:
                return "BOND_BONDING";
            case 12:
                return "BOND_BONDED";
            default:break;
        }
        return "null";
    }
}
