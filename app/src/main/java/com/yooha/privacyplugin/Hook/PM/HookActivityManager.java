package com.yooha.privacyplugin.Hook.PM;

import android.app.ActivityManager;
import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;
import java.util.List;
import de.robv.android.xposed.XposedHelpers;

public class HookActivityManager extends HookMethods {
    public int mCountgetRunningAppProcesses = 0;

    public HookActivityManager(ClassLoader loader, String pkg, int level){
        super( "android.app.ActivityManager", loader, pkg, level);
    }

    public HookActivityManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_SENSITIVE);
    }


    @Override
    protected void hookMethod() {
        try{
            getRunningAppProcesses();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    private void getRunningAppProcesses() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getRunningAppProcesses",
                new YH_MethodHook("android.app.ApplicationPackageManager -> getRunningAppProcesses():获取设备上正在运行的应用程序进程的列表"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetRunningAppProcesses++;
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getRunningAppProcesses")
                                    .setMethodSignature("()")
                                    .setReturn(RunningAppProcessInfosToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetRunningAppProcesses)
                                    .setDescription("获取设备上正在运行的应用程序进程的列表")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    //List<RunningAppProcessInfo>
    public String RunningAppProcessInfosToString(List<ActivityManager.RunningAppProcessInfo> ls){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ls.size() && i<10; i++){
            sb.append("<" + ls.get(i).processName + ">");
        }
        return sb.toString();
    }
}
