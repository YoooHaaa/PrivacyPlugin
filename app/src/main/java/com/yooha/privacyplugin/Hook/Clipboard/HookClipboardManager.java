package com.yooha.privacyplugin.Hook.Clipboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookClipboardManager extends HookMethods {
    private int mCountsetPrimaryClip = 0;
    private int mCountaddPrimaryClipChangedListener = 0;

    public HookClipboardManager(ClassLoader loader, String pkg, int level){
        super( "android.content.ClipboardManager", loader, pkg, level);
    }

    public HookClipboardManager(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_RISK);
    }

    @Override
    protected void hookMethod() {
        try{
            setPrimaryClip();
            addPrimaryClipChangedListener();
            getPrimaryClip();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    private void setPrimaryClip() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "setPrimaryClip",
                ClipData.class,
                new YH_MethodHook("android.content.ClipboardManager -> setPrimaryClip(ClipData clip):设置剪切板"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsetPrimaryClip++;
                            ClipData param1 = (ClipData)param.args[0];
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("setPrimaryClip")
                                    .setMethodSignature("(ClipData clip)")
                                    .setParams(new ArrayList(Arrays.asList(param1.toString())))
                                    .setStackTrace(stack)
                                    .setCount(mCountsetPrimaryClip)
                                    .setDescription("设置剪切板")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getPrimaryClip() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getPrimaryClip",
                new YH_MethodHook("android.content.ClipboardManager -> getPrimaryClip(ClipData clip):获取剪切板"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountsetPrimaryClip++;
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getPrimaryClip")
                                    .setMethodSignature("(ClipData clip)")
                                    .setParams(new ArrayList(Arrays.asList("")))
                                    .setStackTrace(stack)
                                    .setCount(mCountsetPrimaryClip)
                                    .setDescription("获取剪切板")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void addPrimaryClipChangedListener() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "addPrimaryClipChangedListener",
                ClipboardManager.OnPrimaryClipChangedListener.class,
                new YH_MethodHook("android.content.ClipboardManager -> addPrimaryClipChangedListener(OnPrimaryClipChangedListener what):设置剪切板监听器"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountaddPrimaryClipChangedListener++;
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("addPrimaryClipChangedListener")
                                    .setMethodSignature("(OnPrimaryClipChangedListener what)")
                                    .setStackTrace(stack)
                                    .setCount(mCountaddPrimaryClipChangedListener)
                                    .setDescription("设置剪切板监听器")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
