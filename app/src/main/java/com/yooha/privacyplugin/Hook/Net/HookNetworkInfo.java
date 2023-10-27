package com.yooha.privacyplugin.Hook.Net;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class HookNetworkInfo extends HookMethods {
    public int mCountgetType = 0;
    public int mCountisConnected = 0;
    public int mCountisAvailable = 0;
    public int mCountgetTypeName = 0;

    public HookNetworkInfo(ClassLoader loader, String pkg, int level){
        super( "android.net.NetworkInfo", loader, pkg, level);
    }

    public HookNetworkInfo(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    public void hookMethod(){
        try{
            //getType();
            getTypeName();
            //isAvailable();
            //isConnected();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    private void isConnected() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "isConnected",
                new YH_MethodHook("android.net.NetworkInfo -> isConnected():获取网络是否连接"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountisConnected++;
                            boolean ret = (boolean)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("isConnected")
                                    .setMethodSignature("()")
                                    .setReturn(Boolean.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountisConnected)
                                    .setDescription("获取网络是否连接")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void isAvailable() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "isAvailable",
                new YH_MethodHook("android.net.NetworkInfo -> isAvailable():获取网络是否可用"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountisAvailable++;
                            boolean ret = (boolean)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("isAvailable")
                                    .setMethodSignature("()")
                                    .setReturn(Boolean.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountisAvailable)
                                    .setDescription("获取网络是否可用")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getTypeName() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getTypeName",
                new YH_MethodHook("android.net.NetworkInfo -> getTypeName():获取网络类型名称"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetTypeName++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getTypeName")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetTypeName)
                                    .setDescription("获取网络类型名称")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }


    public void getType(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getType",
                new YH_MethodHook("android.net.NetworkInfo -> getType():获取网络类型"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetType++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getType")
                                    .setMethodSignature("()")
                                    .setReturn(parseType(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetType)
                                    .setDescription("获取网络类型")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public String parseType(int type){
        switch (type){
            case 7:
                return "TYPE_BLUETOOTH";
            case 8:
                return "TYPE_DUMMY";
            case 9:
                return "TYPE_ETHERNET";
            case 0:
                return "TYPE_MOBILE";
            case 4:
                return "TYPE_MOBILE_DUN";
            case 5:
                return "TYPE_MOBILE_HIPRI";
            case 2:
                return "TYPE_MOBILE_MMS";
            case 3:
                return "TYPE_MOBILE_SUPL";
            case 17:
                return "TYPE_VPN";
            case 1:
                return "TYPE_WIFI";
            case 6:
                return "TYPE_WIMAX";
            default:break;
        }
        return "null";
    }

}
