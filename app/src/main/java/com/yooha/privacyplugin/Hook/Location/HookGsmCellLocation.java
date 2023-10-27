package com.yooha.privacyplugin.Hook.Location;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookGsmCellLocation extends HookMethods {
    public int mCountgetCid = 0;
    public int mCountgetLac = 0;

    public HookGsmCellLocation(ClassLoader loader, String pkg, int level){
        super( "android.telephony.gsm.GsmCellLocation", loader, pkg, level);
    }

    public HookGsmCellLocation(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_SENSITIVE);
    }

    @Override
    protected void hookMethod() {

        try{
            getCid();
            getLac();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void getCid() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getCid",
                new YH_MethodHook("android.telephony.gsm.GsmCellLocation -> getCid():获取GSM基站Cid"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetCid++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getCid")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetCid)
                                    .setDescription("获取GSM基站Cid")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getLac() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLac",
                new YH_MethodHook("android.telephony.gsm.GsmCellLocation -> getLac():获取GSM基站位置区号"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLac++;
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLac")
                                    .setMethodSignature("()")
                                    .setReturn(Integer.toString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLac)
                                    .setDescription("获取GSM基站位置区号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
