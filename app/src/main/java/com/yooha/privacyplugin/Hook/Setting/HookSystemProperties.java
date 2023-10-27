package com.yooha.privacyplugin.Hook.Setting;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;

public class HookSystemProperties extends HookMethods {
    public int mCountget_S = 0;
    public int mCountget_SS = 0;
    public int mCountgetInt = 0;
    public int mCountgetLong = 0;
    public int mCountgetBoolean = 0;
    public static String[] key={"ro.serialno"};

    public HookSystemProperties(ClassLoader loader, String pkg, int level){
        super( "android.os.SystemProperties", loader, pkg, level);
    }

    public HookSystemProperties(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            get();
//            getInt();
//            getLong();
//            getBoolean();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }

    public void get(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "get",
                String.class,
                new YH_MethodHook("android.os.SystemProperties -> get(String key):获取SN码"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountget_S++;
                            String param1 = (String)param.args[0];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("get")
                                    .setMethodSignature("(String key)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(param1)))
                                    .setStackTrace(stack)
                                    .setCount(mCountget_S)
                                    .setDescription("获取系统属性 - 设备SN码")
                                    .build();
                            if (param1.equals("ro.serialno"))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "get",
                String.class,
                String.class,
                new YH_MethodHook("android.os.SystemProperties -> get(String key,String def):获取系统属性"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountget_SS++;
                            String param1 = (String)param.args[0];
                            String param2 = (String)param.args[1];
                            String ret = (String)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("get")
                                    .setMethodSignature("(String key,String def)")
                                    .setReturn(ret)
                                    .setParams(new ArrayList(Arrays.asList(param1, param2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountget_SS)
                                    .setDescription("获取系统属性 - 设备SN码")
                                    .build();
                            if (param1.equals("ro.serialno"))
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
                String.class,
                int.class,
                new YH_MethodHook("android.os.SystemProperties -> getInt(String key,int def):获取系统属性"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetInt++;
                            String param1 = (String)param.args[0];
                            int param2 = (int)param.args[1];
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getInt")
                                    .setMethodSignature("(String key,int def)")
                                    .setReturn(Integer.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1, Integer.toString(param2))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetInt)
                                    .setDescription("获取系统属性")
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
                String.class,
                long.class,
                new YH_MethodHook("android.os.SystemProperties -> getLong(String key,long def):获取系统属性"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetLong++;
                            String param1 = (String)param.args[0];
                            long param2 = (long)param.args[1];
                            Long ret = (Long)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getLong")
                                    .setMethodSignature("(String key,long def)")
                                    .setReturn(Long.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1, Long.toString(param2))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetLong)
                                    .setDescription("获取系统属性")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public void getBoolean(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getBoolean",
                String.class,
                boolean.class,
                new YH_MethodHook("android.os.SystemProperties -> getBoolean(String key,boolean def):获取系统属性"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetBoolean++;
                            String param1 = (String)param.args[0];
                            boolean param2 = (boolean)param.args[1];
                            boolean ret = (boolean)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getBoolean")
                                    .setMethodSignature("(String key,boolean def)")
                                    .setReturn(Boolean.toString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1, Boolean.toString(param2))))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetBoolean)
                                    .setDescription("获取系统属性")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}



