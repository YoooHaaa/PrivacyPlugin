package com.yooha.privacyplugin.Hook.Reflect;

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

public class HookReflectField extends HookMethods {

    public HookReflectField(ClassLoader loader, String pkg, int level){
        super( "java.lang.Class", loader, pkg, level);
    }

    public HookReflectField(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }

    @Override
    protected void hookMethod() {
        try{

            getField();
            getFields();
            getDeclaredField();
            getDeclaredFields();
//            getMethod();
//            getMethods();
//            getDeclaredMethod();
//            getDeclaredMethods();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    private void getField() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getField",
                String.class,
                new YH_MethodHook("java.lang.Class -> getField(String name):获取字段"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String param1 = (String)param.args[0];
                            Field ret = (Field)param.getResult();
                            String sRet = "null";
                            if (ret != null){
                                sRet = param.thisObject + " -> " + ret.getName();
                            }
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getField")
                                    .setMethodSignature("(String name)")
                                    .setParams(new ArrayList(Arrays.asList(param1)))
                                    .setReturn(sRet)
                                    .setStackTrace(stack)
                                    .setDescription("获取字段")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getFields() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getFields",
                new YH_MethodHook("java.lang.Class -> getFields():获取类的所有字段"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            Field[] ret = (Field[])param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getFields")
                                    .setMethodSignature("()")
                                    .setReturn(param.thisObject + " -> " + FieldsToString(ret))
                                    .setStackTrace(stack)
                                    .setDescription("获取类的所有字段")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getDeclaredField() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDeclaredField",
                String.class,
                new YH_MethodHook("java.lang.Class -> getDeclaredField(String name):获取字段"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String param1 = (String)param.args[0];
                            Field ret = (Field)param.getResult();
                            String sRet = "null";
                            if (ret != null){
                                sRet = param.thisObject + " -> " + ret.getName();
                            }
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getDeclaredField")
                                    .setMethodSignature("(String name)")
                                    .setParams(new ArrayList(Arrays.asList(param1)))
                                    .setReturn(sRet)
                                    .setStackTrace(stack)
                                    .setDescription("获取字段")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getDeclaredFields() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDeclaredFields",
                new YH_MethodHook("java.lang.Class -> getDeclaredFields():获取类的所有字段"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            Field[] ret = (Field[])param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getDeclaredFields")
                                    .setMethodSignature("()")
                                    .setReturn(param.thisObject + " -> " + FieldsToString(ret))
                                    .setStackTrace(stack)
                                    .setDescription("获取类的所有字段")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private String FieldsToString(Field[] fs){
        if (fs == null){
            return "null";
        }
        StringBuffer sb = new StringBuffer();
        for (Field fl:fs){
            sb.append("<" + fl.getName() + ">");
        }
        return sb.toString();
    }

    private void getDeclaredMethod() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDeclaredMethod",
                String.class,
                Class[].class,
                new YH_MethodHook("java.lang.Class -> getDeclaredMethod(String name, class[] parameterTypes):获取方法"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String param1 = (String)param.args[0];
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getDeclaredMethod")
                                    .setMethodSignature("(String name, class[] parameterTypes)")
                                    .setParams(new ArrayList(Arrays.asList(param1)))
                                    .setReturn(param.thisObject + " -> " + param1)
                                    .setStackTrace(stack)
                                    .setDescription("获取方法")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getDeclaredMethods() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getDeclaredMethods",
                new YH_MethodHook("java.lang.Class -> getDeclaredMethods():获取类的全部方法"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getDeclaredMethods")
                                    .setReturn(param.thisObject + " -> [...]")
                                    .setMethodSignature("()")
                                    .setStackTrace(stack)
                                    .setDescription("获取类的全部方法")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getMethod(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getMethod",
                String.class,
                Class[].class,
                new YH_MethodHook("java.lang.Class -> getMethod(String name, class[] parameterTypes):获取方法"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String param1 = (String)param.args[0];
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getMethod")
                                    .setMethodSignature("(String name, class[] parameterTypes)")
                                    .setParams(new ArrayList(Arrays.asList(param1)))
                                    .setReturn(param.thisObject + " -> " + param1)
                                    .setStackTrace(stack)
                                    .setDescription("获取方法")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private void getMethods(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getMethods",
                new YH_MethodHook("java.lang.Class -> getMethods():获取类的全部方法"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getMethods")
                                    .setMethodSignature("()")
                                    .setReturn(param.thisObject + " -> [...]")
                                    .setStackTrace(stack)
                                    .setDescription("获取类的全部方法")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
