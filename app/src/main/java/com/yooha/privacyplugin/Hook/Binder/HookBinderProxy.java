package com.yooha.privacyplugin.Hook.Binder;

import android.os.IBinder;
import android.os.Parcel;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XposedHelpers;


public class HookBinderProxy  extends HookMethods {
    public int mCounttransact = 0;


    public HookBinderProxy(ClassLoader loader, String pkg, int level){
        super( "android.os.BinderProxy", loader, pkg, level);
    }

    public HookBinderProxy(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{
            transact();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    public String getInterfaceNumberName(String name, int code){
        Class<?> clsInterface = null;
        try{
            clsInterface = Class.forName(name + "$Stub");
        } catch (Throwable th){
            try{
                clsInterface = Class.forName(name);
            } catch (Throwable thr){
            }
        }

        try{
            if (clsInterface != null){
                Field[] fields = clsInterface.getDeclaredFields();
                for (Field f:fields){
                    if (f.getType().getName().equals("int")){
                        f.setAccessible(true);
                        if (f.getInt(null) == code){
                            return f.getName();
                        }
                    }
                }
            }
        } catch (Throwable thr){
        }
        return "";
    }

    public void transact(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "transact",
                int.class,
                Parcel.class,
                Parcel.class,
                int.class,
                new YH_MethodHook("android.os.BinderProxy -> transact():与服务端通信"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String stack = LogUtil.getStackTrace();
                            String[] stacks = stack.split("\n");
                            if (stacks.length >= 5){
                                String call = stacks[4];
                                if (call.startsWith("at android.") || call.startsWith("at com.android.") ||
                                        call.startsWith("at com.miui.") || call.startsWith("at miui.") ||
                                        call.startsWith("at com.qualcomm.") || call.startsWith("at org.chromium.")){
                                    return;
                                }
                            }
                            mCounttransact++;
                            int code = (int)param.args[0];
                            String interfaceNumberName = "";
                            boolean ret = (boolean)param.getResult();
                            String interfaceClassName = ((IBinder) param.thisObject).getInterfaceDescriptor();
                            if (interfaceClassName.contains("lsposed")){
                                return;
                            }
                            // 获取调用号对应的接口名
                            interfaceNumberName = getInterfaceNumberName(interfaceClassName, code);

                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("transact")
                                    .setMethodSignature("(int code, Parcel data, Parcel reply, int flags)")
                                    .setParams(new ArrayList(Arrays.asList(interfaceNumberName, interfaceClassName)))
                                    .setReturn(String.valueOf(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCounttransact)
                                    .setDescription("与服务端通信")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

}

