package com.yooha.privacyplugin.Hook.Base;


import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookInterface;

public abstract class HookMethods implements HookInterface {
    public String mCls = null;
    protected ClassLoader mLoader = null;
    protected String mPkg = null;
    protected int mLevel = Data.LEVEL_GENERAL;

    protected HookMethods(String clz, ClassLoader loader, String pkg, int level){
        this.mCls = clz;
        this.mLoader = loader;
        this.mPkg = pkg;
        this.mLevel = level;
    }

    protected abstract void hookMethod();

    @Override
    public void hook(){
        hookMethod();
    }

}
