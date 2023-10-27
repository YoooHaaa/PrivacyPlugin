package com.yooha.privacyplugin.Hook.Net;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.net.InetAddress;

import de.robv.android.xposed.XposedHelpers;

public class HookWifiInfo extends HookMethods {
    public int mCountgetMacAddress = 0;
    public int mCountgetIpAddress = 0;
    public int mCountgetSSID = 0;
    public int mCountgetBSSID = 0;

    public HookWifiInfo(ClassLoader loader, String pkg, int level){
        super("android.net.wifi.WifiInfo", loader, pkg, level);
    }

    public HookWifiInfo(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    @Override
    public void hookMethod(){
        try{
            getMacAddress();
            getIpAddress();
            getSSID();
            getBSSID();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void getMacAddress(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getMacAddress",
                new YH_MethodHook("android.net.wifi.WifiInfo -> getMacAddress():获取mac地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetMacAddress++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getMacAddress")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetMacAddress)
                                    .setDescription("获取mac地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getIpAddress(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getIpAddress",
                new YH_MethodHook("android.net.wifi.WifiInfo -> getIpAddress():获取Wifi的IP地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetIpAddress++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getIpAddress")
                                    .setMethodSignature("()")
                                    .setReturn(IpAddrToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetIpAddress)
                                    .setDescription("获取Wifi的IP地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private String IpAddrToString(int intaddr){
        byte[] byteaddr = new byte[] { (byte) (intaddr & 0xff),
                (byte) (intaddr >> 8 & 0xff), (byte) (intaddr >> 16 & 0xff),
                (byte) (intaddr >> 24 & 0xff) };
        InetAddress addr = null;
        try {
            addr = InetAddress.getByAddress(byteaddr);
        } catch (Exception e) {
            LogUtil.error(e.toString());
        }
        String mobileIp = addr.getHostAddress();
        return mobileIp;
    }

    public void getSSID(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getSSID",
                new YH_MethodHook("android.net.wifi.WifiInfo -> getSSID():获取Wifi SSID"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetSSID++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getSSID")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSSID)
                                    .setDescription("获取Wifi SSID")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getBSSID(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getBSSID",
                new YH_MethodHook("android.net.wifi.WifiInfo -> getBSSID():获取Wifi BSSID(wifi mac地址)"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetBSSID++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getBSSID")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetBSSID)
                                    .setDescription("获取Wifi BSSID(wifi mac地址)")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

}
