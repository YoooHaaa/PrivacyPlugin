package com.yooha.privacyplugin.Util;


import android.util.Log;

public class LogUtil {
    private static final String TAG = "A-Privacy";
    private static final int STACK_TRACE = 3; //最底层被嵌套3层，所以原函数在第四个. 混淆后由于函数被内联 所以原函数在第2层

    public static void info(String log){ // Log最多打印 4*1024 字节的数据
        int max_log_length=4000 - TAG.length();
        //大于4000时
        while (log.length() > max_log_length){
            Log.i(TAG, log.substring(0, max_log_length) );
            log = log.substring(max_log_length);
        }
        //打印剩余部分
        Log.i(TAG, log);
    }

    public static void error(String log){
        StringBuffer sb = new StringBuffer(log);
        sb.append(getSystemMsg());
        Log.e(TAG, sb.toString());
    }

    private static String getSystemMsg(){
        StringBuffer sb = new StringBuffer();
        sb.append("  [ ");
        sb.append(getClassName()).append(" -> ");
        sb.append(getMethodName()).append(":");
        sb.append(getLineNumber());
        sb.append(" ]");
        return sb.toString();
    }

    public static void logStackTrace() {
        Log.i(TAG, "---------------------------------------------------------------------------< Stack start >\n");
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        for (int i = 0; i < stackElements.length; i++) {
            StackTraceElement element = stackElements[i];
            Log.i(TAG, "at " +
                    element.getClassName() +
                    "." +
                    element.getMethodName() +
                    "(" +
                    element.getFileName() +
                    ":" +
                    element.getLineNumber() +
                    ")");
        }
        Log.i(TAG, "---------------------------------------------------------------------------< Stack end >\n");
    }

    public static String getStackTrace() {
        StringBuffer sb = new StringBuffer();
        Throwable ex = new Throwable();
        StackTraceElement[] stackElements = ex.getStackTrace();
        for (int i = 0; i < stackElements.length; i++) {
            StackTraceElement element = stackElements[i];
            sb.append("at " +
                    element.getClassName() +
                    "." +
                    element.getMethodName() +
                    "(" +
                    element.getFileName() +
                    ":" +
                    element.getLineNumber() +
                    ")\n");
        }
        return sb.toString();
    }

    private static String getLineNumber(){
        return String.valueOf(new Throwable().getStackTrace()[STACK_TRACE].getLineNumber());
    }

    private static String getMethodName(){
        return new Throwable().getStackTrace()[STACK_TRACE].getMethodName();
    }

    private static String getClassName(){
        return new Throwable().getStackTrace()[STACK_TRACE].getClassName();
    }

    private static String getFileName(){
        return new Throwable().getStackTrace()[STACK_TRACE].getFileName();
    }
}
