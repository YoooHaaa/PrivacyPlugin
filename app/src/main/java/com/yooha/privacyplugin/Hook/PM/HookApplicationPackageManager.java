package com.yooha.privacyplugin.Hook.PM;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.XposedHelpers;

public class HookApplicationPackageManager extends HookMethods {
    public int mCountgetInstalledPackages_I = 0;
    public int mCountgetInstalledPackages_II = 0;
    public int mCountgetInstalledApplications_I = 0;
    public int mCountgetInstalledApplications_II = 0;
    public int mCountqueryIntentActivities_II = 0;
    public int mCountqueryIntentActivities_III = 0;

    public HookApplicationPackageManager(ClassLoader loader, String pkg, int level){
        super( "android.app.ApplicationPackageManager", loader, pkg, level);
    }

    public HookApplicationPackageManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    @Override
    protected void hookMethod() {
        try{
            getInstalledPackages();
            getInstalledApplications();
            //queryIntentActivities();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    // List<PackageInfo>
    public void getInstalledPackages() throws Exception {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getInstalledPackages",
                int.class,
                new YH_MethodHook("android.app.ApplicationPackageManager -> getInstalledPackages(int flag):获取已安装应用列表"){ //flag请查询：https://developer.android.google.cn/reference/android/content/pm/PackageManager"
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetInstalledPackages_I++;
                            int param1 = (int)param.args[0];
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getInstalledPackages")
                                    .setMethodSignature("(int flag)")
                                    .setReturn(PackageInfosToString(ret))
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetInstalledPackages_I)
                                    .setDescription("获取已安装应用列表")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    //List<PackageInfo>
    public String PackageInfosToString(List<PackageInfo> ls){
        StringBuffer sb = new StringBuffer();
        try{
            for (int i = 0; i < ls.size() && i <10; i++){
                sb.append("<" + ls.get(i).packageName + ">");
            }
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
        return sb.toString();
    }

    public void getInstalledApplications(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getInstalledApplications",
                int.class,
                new YH_MethodHook("android.app.ApplicationPackageManager -> getInstalledApplications(int flag):获取已安装应用列表"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetInstalledApplications_I++;
                            int param1 = (int)param.args[0];
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getInstalledApplications")
                                    .setMethodSignature("(int flag)")
                                    .setReturn(ApplicationInfosToString(ret))
                                    .setParams(new ArrayList(Arrays.asList(Integer.toString(param1))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetInstalledApplications_I)
                                    .setDescription("获取已安装应用列表")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    //List<ApplicationInfo>
    public String ApplicationInfosToString(List<ApplicationInfo> ls){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ls.size() && i<10; i++){
            sb.append("<" + ls.get(i).packageName + ">");
        }
        return sb.toString();
    }



    public void queryIntentActivities() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "queryIntentActivities",
                Intent.class,
                int.class,
                new YH_MethodHook("android.app.ApplicationPackageManager -> ResolveInfo queryIntentActivities(Intent intent,int flag):获取已安装应用信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountqueryIntentActivities_II++;
                            Intent param1 = (Intent)param.args[0];
                            int param2 = (int)param.args[1];
                            List ret = (List)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("queryIntentActivities")
                                    .setMethodSignature("(Intent intent,int flag)")
                                    .setReturn(ResolveInfosToString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), Integer.toString(param2))))
                                    .setStackTrace(stack)
                                    .setCount(mCountqueryIntentActivities_II)
                                    .setDescription("获取已安装应用信息")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    //List<ResolveInfo>
    public String ResolveInfosToString(List<ResolveInfo> ls){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ls.size() && i<10; i++){
            sb.append("<" + ls.get(i).activityInfo.packageName + ">");
        }
        return sb.toString();
    }

}
