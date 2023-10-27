package com.yooha.privacyplugin.Hook.Net;

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;
import java.util.List;
import de.robv.android.xposed.XposedHelpers;

public class HookWifiManager extends HookMethods {
    private int mCountgetConfiguredNetworks = 0;
    private int mCountgetConnectionInfo = 0;
    private int mCountgetDhcpInfo = 0;
    private int mCountgetScanResults = 0;

    public HookWifiManager(ClassLoader loader, String pkg, int level){
        super("android.net.wifi.WifiManager", loader, pkg, level);
    }

    public HookWifiManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_SENSITIVE);
    }


    @Override
    public void hookMethod(){

        try{
            getConfiguredNetworks();
            getConnectionInfo();
            getDhcpInfo();
            getScanResults();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void getConfiguredNetworks() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getConfiguredNetworks",
                new YH_MethodHook("android.net.wifi.WifiManager -> getConfiguredNetworks():获取当前前台用户配置的所有网络的列表"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetConfiguredNetworks++;
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getConfiguredNetworks")
                                    .setMethodSignature("()")
                                    .setReturn(WifiConfigurationToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetConfiguredNetworks)
                                    .setDescription("获取当前前台用户配置的所有网络的列表")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private String WifiConfigurationToString(List<WifiConfiguration> ls){
        if (ls == null ){
            return "null";
        }
        StringBuffer sb = new StringBuffer();
        for (WifiConfiguration cf:ls){
            sb.append("<" + cf.SSID + ">");
        }
        return sb.toString();
    }

    private void getConnectionInfo() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getConnectionInfo",
                new YH_MethodHook("android.net.wifi.WifiManager -> getConnectionInfo():获取当前网络连接信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetConnectionInfo++;
                            WifiInfo ret = (WifiInfo)param.getResult();
                            String sRet = "null";
                            if (ret != null){
                                sRet = ret.getSSID();
                            }
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getConnectionInfo")
                                    .setMethodSignature("()")
                                    .setReturn(sRet) //防止ret为null
                                    .setStackTrace(stack)
                                    .setCount(mCountgetConnectionInfo)
                                    .setDescription("获取当前网络连接信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getDhcpInfo() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDhcpInfo",
                new YH_MethodHook("android.net.wifi.WifiManager -> getDhcpInfo():获取网关信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetDhcpInfo++;
                            DhcpInfo ret = (DhcpInfo)param.getResult();
                            String sRet = "null";
                            if (ret != null){
                                sRet = ret.toString();
                            }
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getDhcpInfo")
                                    .setMethodSignature("()")
                                    .setReturn(sRet) //防止ret为null
                                    .setStackTrace(stack)
                                    .setCount(mCountgetDhcpInfo)
                                    .setDescription("获取网关信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getScanResults() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getScanResults",
                new YH_MethodHook("android.net.wifi.WifiManager -> getScanResults():获取Wifi扫描列表"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetScanResults++;
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getScanResults")
                                    .setMethodSignature("()")
                                    .setReturn(ScanResultToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetScanResults)
                                    .setDescription("获取Wifi扫描列表")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private String ScanResultToString(List<ScanResult> ls){
        if (ls == null ){
            return "null";
        }
        StringBuffer sb = new StringBuffer();
        for (ScanResult sr:ls){
            sb.append("<" + sr.SSID + ">");
        }
        return sb.toString();
    }

}
