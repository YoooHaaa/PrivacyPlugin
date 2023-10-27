package com.yooha.privacyplugin.Hook.Setting;

import android.content.ContentResolver;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookSettingsSecure extends HookMethods {
    public int mCountgetFloat_CS = 0;
    public int mCountgetFloat_CSF = 0;
    public int mCountgetString = 0;
    public int mCountgetInt_CS = 0;
    public int mCountgetInt_CSI = 0;
    public int mCountgetLong_CS = 0;
    public int mCountgetLong_CSL = 0;

    public HookSettingsSecure(ClassLoader loader, String pkg, int level){
        super( "android.provider.Settings$Secure", loader, pkg, level);
    }

    public HookSettingsSecure(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    @Override
    protected void hookMethod() {
        try{
            getStringForUser();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }


    public void getStringForUser(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getStringForUser",
                ContentResolver.class,
                String.class,
                int.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getStringForUser(ContentResolver cr,String name,int userHandle):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetString++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            int param3 = (int)param.args[2];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getStringForUser")
                                    .setMethodSignature("(ContentResolver cr,String name,int userHandle)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2, Integer.toString(param3))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetString)
                                    .setDescription("获取系统设置 -- android_id")
                                    .build();
                            if(param2.contains("android_id"))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getInt(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getInt",
                ContentResolver.class,
                String.class,
                int.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getInt(ContentResolver cr,String name,int def):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetInt_CSI++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            int param3 = (int)param.args[2];
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getInt")
                                    .setMethodSignature("(ContentResolver cr,String name,int def)")
                                    .setReturn(Integer.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2, Integer.toString(param3))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetInt_CSI)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getInt",
                ContentResolver.class,
                String.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getInt(ContentResolver cr,String name):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetInt_CS++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getInt")
                                    .setMethodSignature("(ContentResolver cr,String name)")
                                    .setReturn(Integer.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetInt_CS)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
    public void getLong(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLong",
                ContentResolver.class,
                String.class,
                long.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getLong(ContentResolver cr,String name,long def):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLong_CSL++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            long param3 = (long)param.args[2];
                            long ret = (long)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLong")
                                    .setMethodSignature("(ContentResolver cr,String name,long def)")
                                    .setReturn(Long.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2, Long.toString(param3))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLong_CSL)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getLong",
                ContentResolver.class,
                String.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getLong(ContentResolver cr,String name):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLong_CS++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            long ret = (long)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLong")
                                    .setMethodSignature("(ContentResolver cr,String name)")
                                    .setReturn(Long.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLong_CS)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
    public void getString(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getString",
                ContentResolver.class,
                String.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getString(ContentResolver cr,String name):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetString++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getString")
                                    .setMethodSignature("(ContentResolver cr,String name)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetString)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getFloat(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getFloat",
                ContentResolver.class,
                String.class,
                float.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getFloat(ContentResolver cr,String name,float def):获取系统设置"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetFloat_CSF++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            float param3 = (float)param.args[2];
                            float ret = (float)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getFloat")
                                    .setMethodSignature("(ContentResolver cr,String name,float def)")
                                    .setReturn(Float.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2, Float.toString(param3))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetFloat_CSF)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getFloat",
                ContentResolver.class,
                String.class,
                new YH_MethodHook("android.provider.Settings$Secure -> getFloat(ContentResolver cr,String name):获取设备配置信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetFloat_CS++;
                            ContentResolver param1 = (ContentResolver)param.args[0];
                            String param2 = (String)param.args[1];
                            float ret = (float)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getFloat")
                                    .setMethodSignature("(ContentResolver cr,String name)")
                                    .setReturn(Float.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1.toString(), param2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetFloat_CS)
                                    .setDescription("获取系统设置")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
