package com.yooha.privacyplugin;


import com.yooha.privacyplugin.Hook.Base.HookHelper;
import com.yooha.privacyplugin.Util.LogUtil;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;



/**
 * 作者: Yooha
 * 时间: 2023/10/09
 * 说明: 监控应用隐私获取行为
 */


public class EntryHook implements IXposedHookLoadPackage {
    public HookHelper mHook = null;
    static {
        System.loadLibrary("yooha");
    }
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {


        try {
            if (lpparam.processName.contains("com.yooha.privacyplugin") || lpparam.processName.contains("com.miui") ){
                return;
            }
            LogUtil.info("********************************************************************** Load package -> " + lpparam.packageName);
            if (lpparam.isFirstApplication){
                mHook = HookHelper.sharedInstance(lpparam.packageName);
                mHook.setClassLoader(lpparam.classLoader).registerHook().hook();
            }
        } catch (Exception err) {
            LogUtil.error(err.toString());
        }
    }

}