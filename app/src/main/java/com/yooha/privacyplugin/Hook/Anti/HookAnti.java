package com.yooha.privacyplugin.Hook.Anti;

import android.util.Log;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import de.robv.android.xposed.XposedHelpers;

public class HookAnti extends HookMethods {

    public HookAnti(ClassLoader loader,String pkg,int level){
        super("",loader,pkg,level);
    }

    public HookAnti(ClassLoader loader,String pkg){
        this(loader,pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        antiStackTrace();
    }

    private void antiStackTrace(){
        XposedHelpers.findAndHookMethod("java.lang.Throwable",
                this.mLoader,
                "getStackTrace",
                new YH_MethodHook("java.lang.Throwable -> getStackTrace(): anti StackTrace check") {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try {
                            StackTraceElement[] ret = (StackTraceElement[]) param.getResult();
                            for (int i = 0; i < ret.length; i++) {
                                StackTraceElement element = ret[i];
                                String lineClass = element.getClassName();
                                if (lineClass.contains("LSPHooker_")){
                                    StackTraceElement tmp = new StackTraceElement(lineClass.replace("LSPHooker_", "Yooha"), element.getMethodName(), element.getFileName(), element.getLineNumber());
                                    ret[i] = tmp;
                                    continue;
                                }
                                if (lineClass.contains("de.robv.android.xposed.")){
                                    StackTraceElement tmp = new StackTraceElement(lineClass.replace("de.robv.android.xposed.XposedBridge$AdditionalHookInfo", "com.yooha.replace"), element.getMethodName(), element.getFileName(), element.getLineNumber());
                                    ret[i] = tmp;
                                }
                            }
                            param.setResult(ret);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
