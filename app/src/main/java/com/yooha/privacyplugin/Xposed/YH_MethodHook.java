package com.yooha.privacyplugin.Xposed;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Util.LogUtil;
import de.robv.android.xposed.XC_MethodHook;

public abstract class YH_MethodHook extends XC_MethodHook {
    public YH_MethodHook(String info){
        if (Data.isDebug){
            LogUtil.info(info);
        }
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
    }
}
