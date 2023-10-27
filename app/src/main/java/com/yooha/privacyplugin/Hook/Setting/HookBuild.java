package com.yooha.privacyplugin.Hook.Setting;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookBuild extends HookMethods {
    public int mCountgetSerial = 0;

    public HookBuild(ClassLoader loader, String pkg, int level){
        super( "android.os.Build", loader, pkg, level);
    }

    public HookBuild(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            getSerial();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void getSerial() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getSerial",
                new YH_MethodHook("android.os.Build -> getSerial():获取SN码"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetSerial++;
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getSerial")
                                    .setMethodSignature("()")
                                    .setReturn(ret)
                                    .setStackTrace(stack)
                                    .setCount(mCountgetSerial)
                                    .setDescription("获取硬件序列号")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
