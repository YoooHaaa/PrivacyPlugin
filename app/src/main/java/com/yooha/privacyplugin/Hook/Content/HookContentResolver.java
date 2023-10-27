package com.yooha.privacyplugin.Hook.Content;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import de.robv.android.xposed.XposedHelpers;

public class HookContentResolver  extends HookMethods {
    private final String CONTRACT_CONTACTS = "contacts";  //通讯录
    private final String CONTRACT_CALENDAR = "calendar";  //日历
    private final String CONTRACT_BROWSER = "browser";    //浏览器
    private final String CONTRACT_MEDIASTORE = "media";   //多媒体
    private final String CONTRACT_CALL = "call";          //通话记录
    private final String CONTRACT_SMS = "sms";            //短信
    private final String CONTRACT_EMAIL = "email";        //邮件
    private final String CONTRACT_PEOPLE = "people";      //联系人
    private final String CONTRACT_VIDEO = "video";        //视频
    private final String CONTRACT_RADIO = "radio";        //收音机
    private final String CONTRACT_AUDIO = "audio";        //音频
    private final String CONTRACT_IMAGE = "image";        //图片
    private final String CONTRACT_FILE = "files";         //文件
    private final String CONTRACT_DOWNLOAD = "downloads"; //下载
    private final String CONTRACT_SETTING = "settings";   //设置

    public int mCountquery = 0;
    public int mCountinsert = 0;
    public int mCountdelete = 0;
    public int mCountupdate = 0;


    public HookContentResolver(ClassLoader loader, String pkg, int level){
        super( "android.content.ContentResolver", loader, pkg, level);
    }

    public HookContentResolver(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_RISK);
    }

    @Override
    protected void hookMethod() {
        try{
            query();
            insert();
            delete();
            update();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }

    }


    private void query() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "query",
                Uri.class,
                String[].class,
                Bundle.class,
                CancellationSignal.class,
                new YH_MethodHook("android.content.ContentResolver -> query(Uri uri,String[] projection,Bundle queryArgs,CancellationSignal cancellationSignal):获取content敏感信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountquery++;
                            Uri param1 = (Uri)param.args[0];
                            String[] param2 = (String[])param.args[1];
                            String param_2 = "";
                            if (param2 != null){
                                for (int i = 0; i < param2.length; i++){
                                    param_2 += " # " + param2[i];
                                }
                            }
                            Cursor ret = (Cursor)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("query")
                                    .setMethodSignature("(Uri uri,String[] projection,Bundle queryArgs,CancellationSignal cancellationSignal)")
                                    .setReturn(safeString(ret))
                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1), param_2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountquery)
                                    .build();
                            if(!(UriToString(param1).equals("null") || UriToString(param1).equals("Unkown")))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }



    private String UriToString(Uri uri){
        if (uri == null){
            return "null";
        }
        String suri = uri.toString();
        if (suri.contains(CONTRACT_CONTACTS)){
            return "通讯录:" + suri;
        }
        else if (suri.contains(CONTRACT_CALENDAR)){
            return "日历:" + suri;
        }
        else if (suri.contains(CONTRACT_BROWSER)){
            return "浏览器:" + suri;
        }
        else if (suri.contains(CONTRACT_MEDIASTORE)){
            return "多媒体:" + suri;
        }
        else if (suri.contains(CONTRACT_CALL)){
            return "通话:" + suri;
        }
        else if (suri.contains(CONTRACT_SMS)){
            return "短信:" + suri;
        }
        else if (suri.contains(CONTRACT_EMAIL)){
            return "邮件:" + suri;
        }
        else if (suri.contains(CONTRACT_PEOPLE)){
            return "联系人:" + suri;
        }
        else if (suri.contains(CONTRACT_VIDEO)){
            return "视频:" + suri;
        }
        else if (suri.contains(CONTRACT_RADIO)){
            return "收音机:" + suri;
        }
        else if (suri.contains(CONTRACT_AUDIO)){
            return "音频:" + suri;
        }
        else if (suri.contains(CONTRACT_IMAGE)){
            return "图片:" + suri;
        }
        else if (suri.contains(CONTRACT_FILE)){
            return "文件:" + suri;
        }
        else if (suri.contains(CONTRACT_DOWNLOAD)){
            return "下载:" + suri;
        }
        else if (suri.contains(CONTRACT_SETTING)){
            return "设置:" + suri;
        }
        return "Unkown";
    }



    private void insert() {
//        XposedHelpers.findAndHookMethod(this.mCls,
//                this.mLoader,
//                "insert",
//                Uri.class,
//                ContentValues.class,
//                Bundle.class,
//                new YH_MethodHook("android.content.ContentResolver -> insert(Uri url, ContentValues values, Bundle extras):插入content敏感信息"){
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        try{
//                            mCountinsert++;
//                            Uri param1 = (Uri)param.args[0];
//                            ContentValues param2 = (ContentValues)param.args[1];
//                            Uri ret = (Uri)param.getResult();
//                            String stack = LogUtil.getStackTrace();
//                            HookMessage msg = new HookMessage()
//                                    .setLevel(mLevel)
//                                    .setClass(mCls)
//                                    .setMethod("insert")
//                                    .setMethodSignature("(Uri url, ContentValues values, Bundle extras)")
//                                    .setReturn(safeString(ret))
//                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1), ContentValuesToString(param2))))
//                                    .setStackTrace(stack)
//                                    .setCount(mCountinsert);
//                            Server.addMsg(msg);
//                        }catch (Exception e){
//                            LogUtil.error(e.toString());
//                        }
//                    }
//                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "insert",
                Uri.class,
                ContentValues.class,
                new YH_MethodHook("android.content.ContentResolver -> insert(Uri url, ContentValues values):插入content敏感信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountinsert++;
                            Uri param1 = (Uri)param.args[0];
                            ContentValues param2 = (ContentValues)param.args[1];
                            Uri ret = (Uri)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("insert")
                                    .setMethodSignature("(Uri url, ContentValues values)")
                                    .setReturn(safeString(ret))
                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1), ContentValuesToString(param2))))
                                    .setStackTrace(stack)
                                    .setCount(mCountinsert)
                                    .build();
                            if(!(UriToString(param1).equals("null") || UriToString(param1).equals("Unkown")))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private String ContentValuesToString(ContentValues cont){
        if (cont == null){
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Set<String> set = cont.keySet();
        final Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object obj = cont.get(key);
            sb.append("[" + key + "->" + obj.toString() + "]");
        }
        return sb.toString();
    }

    private String safeString(Object uri){
        if (uri == null){
            return "";
        }else{
            return uri.toString();
        }
    }


    private void delete() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "delete",
                Uri.class,
                String.class,
                String[].class,
                new YH_MethodHook("android.content.ContentResolver -> delete(Uri url, String where, String[] selectionArgs):删除content敏感信息") {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try {
                            mCountdelete++;
                            Uri param1 = (Uri) param.args[0];
                            String param2 = (String) param.args[1];
                            int ret = (int) param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("delete")
                                    .setMethodSignature("(Uri url, String where, String[] selectionArgs)")
                                    .setReturn(String.valueOf(ret))
                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1), param2)))
                                    .setStackTrace(stack)
                                    .setCount(mCountdelete)
                                    .build();
                            if(!(UriToString(param1).equals("null") || UriToString(param1).equals("Unkown")))
                                MsgSender.addMsg(msg);
                        } catch (Exception e) {
                            LogUtil.error(e.toString());
                        }
                    }
                });
//        XposedHelpers.findAndHookMethod(this.mCls,
//                this.mLoader,
//                "delete",
//                Uri.class,
//                Bundle.class,
//                new YH_MethodHook("android.content.ContentResolver -> delete(Uri url, Bundle extras):删除content敏感信息") {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        try {
//                            mCountdelete++;
//                            Uri param1 = (Uri) param.args[0];
//                            int ret = (int) param.getResult();
//                            String stack = LogUtil.getStackTrace();
//                            HookMessage msg = new HookMessage.Builder()
//                                    .setLevel(mLevel)
//                                    .setClass(mCls)
//                                    .setMethod("delete")
//                                    .setMethodSignature("(Uri url, Bundle extras)")
//                                    .setReturn(String.valueOf(ret))
//                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1))))
//                                    .setStackTrace(stack)
//                                    .setCount(mCountdelete)
//                                    .build();
//                            Server.addMsg(msg);
//                        } catch (Exception e) {
//                            LogUtil.error(e.toString());
//                        }
//                    }
//                });
    }



    private void update() {
//        XposedHelpers.findAndHookMethod(this.mCls,
//                this.mLoader,
//                "update",
//                Uri.class,
//                ContentValues.class,
//                Bundle.class,
//                new YH_MethodHook("android.content.ContentResolver -> update(Uri uri, ContentValues values, Bundle extras):更新content敏感信息"){
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                        try{
//                            mCountupdate++;
//                            Uri param1 = (Uri)param.args[0];
//                            ContentValues param2 = (ContentValues)param.args[1];
//                            int ret = (int)param.getResult();
//                            String stack = LogUtil.getStackTrace();
//                            HookMessage msg = new HookMessage.Builder()
//                                    .setLevel(mLevel)
//                                    .setClass(mCls)
//                                    .setMethod("update")
//                                    .setMethodSignature("(Uri url, ContentValues values, Bundle extras)")
//                                    .setReturn(String.valueOf(ret))
//                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1), ContentValuesToString(param2))))
//                                    .setStackTrace(stack)
//                                    .setCount(mCountupdate)
//                                    .build();
//                            if(!(UriToString(param1).equals("null") || UriToString(param1).equals("Unkown")))
//                                Server.addMsg(msg);
//                        }catch (Exception e){
//                            LogUtil.error(e.toString());
//                        }
//                    }
//                });
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "update",
                Uri.class,
                ContentValues.class,
                String.class,
                String[].class,
                new YH_MethodHook("android.content.ContentResolver -> update(Uri uri, ContentValues values, String where, String[] selectionArgs):更新content敏感信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountupdate++;
                            Uri param1 = (Uri)param.args[0];
                            ContentValues param2 = (ContentValues)param.args[1];
                            int ret = (int)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("update")
                                    .setMethodSignature("(Uri uri, ContentValues values, String where, String[] selectionArgs)")
                                    .setReturn(String.valueOf(ret))
                                    .setParams(new ArrayList(Arrays.asList(UriToString(param1), ContentValuesToString(param2))))
                                    .setStackTrace(stack)
                                    .setCount(mCountupdate)
                                    .build();
                            if(!(UriToString(param1).equals("null") || UriToString(param1).equals("Unkown")))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}