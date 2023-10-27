package com.yooha.privacyplugin.Hook.PM;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ProviderInfo;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookPackageManager extends HookMethods {
    public int mCountgetPackageArchiveInfo = 0;

    public HookPackageManager(ClassLoader loader, String pkg, int level){
        super( "android.content.pm.PackageManager", loader, pkg, level);
    }

    public HookPackageManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_SENSITIVE);
    }


    @Override
    protected void hookMethod() {
        try{
            getPackageArchiveInfo();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    private void getPackageArchiveInfo() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getPackageArchiveInfo",
                String.class,
                int.class,
                new YH_MethodHook("android.content.pm.PackageManager -> getPackageArchiveInfo(String archiveFilePath, int flags):获取包相关信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetPackageArchiveInfo++;
                            String param1 = (String)param.args[0];
                            int param2 = (int)param.args[1];
                            if(mPkg.indexOf(mPkg) >=0){
                                return;
                            }
                            PackageInfo ret = (PackageInfo)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getPackageArchiveInfo")
                                    .setMethodSignature("(String archiveFilePath, int flags)")
                                    .setParams(new ArrayList(Arrays.asList(String.valueOf(param1), Integer.toString(param2))))
                                    .setReturn(PackageInfoToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetPackageArchiveInfo)
                                    .setDescription("获取包相关信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public String PackageInfoToString(PackageInfo pi){
        if (pi == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(" ");
        try{
            ActivityInfo[] acs = pi.activities;
            if (acs != null){
                sb.append("ActivityInfo -> ");
                for (ActivityInfo ac:acs){
                    sb.append(ac.name + ",");
                }
            }
            ProviderInfo[] pvs = pi.providers;
            if (pvs != null){
                sb.append("ProviderInfo -> ");
                for (ProviderInfo pv:pvs){
                    sb.append(pv.name + ",");
                }
            }
            ActivityInfo[] rcs = pi.receivers;
            if (rcs != null){
                sb.append("receivers -> ");
                for (ActivityInfo rc:rcs){
                    sb.append(rc.name + ",");
                }
            }
            //sb.deleteCharAt(-1);
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
        return sb.toString();
    }

}
