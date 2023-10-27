package com.yooha.privacyplugin.Hook.Content;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

public class HookContentProviderProxy extends HookMethods {
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


    private Class<?> ICancellationSignal;

    public int mCountquery = 0;
    public int mCountinsert = 0;
    public int mCountdelete = 0;
    public int mCountupdate = 0;


    public HookContentProviderProxy(ClassLoader loader, String pkg, int level){
        super( "android.content.ContentProviderProxy", loader, pkg, level);
    }

    public HookContentProviderProxy(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_RISK);
    }

    @Override
    protected void hookMethod() {
        try{
            this.ICancellationSignal=Class.forName("android.os.ICancellationSignal");
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
                String.class,
                Uri.class,
                String[].class,
                Bundle.class,
                this.ICancellationSignal,
                new YH_MethodHook("android.content.ContentProviderProxy -> query(String callingPkg,Uri uri,String[] projection,Bundle queryArgs,ICancellationSignal cancellationSignal):获取content敏感信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String stack = LogUtil.getStackTrace();
                            if(filterStack(stack))
                                return;
                            mCountquery++;
                            String param1=(String) param.args[0];
                            Uri param2 = (Uri)param.args[1];
                            String[] param3 = (String[])param.args[2];
                            String param_3 = "";
                            if (param3 != null){
                                for (int i = 0; i < param3.length; i++){
                                    param_3 += " # " + param3[i];
                                }
                            }
                            Cursor ret = (Cursor)param.getResult();

                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("query")
                                    .setMethodSignature("(String callingPkg,Uri uri,String[] projection,Bundle queryArgs,ICancellationSignal cancellationSignal)")
                                    .setReturn(safeString(ret))
                                    .setParams(new ArrayList(Arrays.asList(param1,UriToString(param2), param_3)))
                                    .setStackTrace(stack)
                                    .setCount(mCountquery)
                                    .build();
                            if(!(UriToString(param2).equals("null") || UriToString(param2).equals("Unkown"))){
                                MsgSender.addMsg(msg);
                            }
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
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "insert",
                String.class,
                Uri.class,
                ContentValues.class,
                new YH_MethodHook("android.content.ContentProviderProxy -> insert(String callingPkg, Uri url, ContentValues values):插入content敏感信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String stack = LogUtil.getStackTrace();
                            if(filterStack(stack))
                                return;
                            mCountinsert++;
                            String callingPkg=(String) param.args[0];
                            Uri param2 = (Uri)param.args[1];
                            ContentValues param3 = (ContentValues)param.args[2];
                            Uri ret = (Uri)param.getResult();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("insert")
                                    .setMethodSignature("(String callingPkg, Uri url, ContentValues values)")
                                    .setReturn(safeString(ret))
                                    .setParams(new ArrayList(Arrays.asList(callingPkg,UriToString(param2), ContentValuesToString(param3))))
                                    .setStackTrace(stack)
                                    .setCount(mCountinsert)
                                    .build();
                            if(!(UriToString(param2).equals("null") || UriToString(param2).equals("Unkown")))
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
                String.class,
                Uri.class,
                String.class,
                String[].class,
                new YH_MethodHook("android.content.ContentProviderProxy -> delete(String callingPkg, Uri url, String where, String[] selectionArgs):删除content敏感信息") {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try {
                            String stack = LogUtil.getStackTrace();
                            if(filterStack(stack))
                                return;
                            mCountdelete++;
                            String callingPkg = (String) param.args[0];
                            Uri param2 = (Uri) param.args[1];
                            String param3 = (String) param.args[2];
                            int ret = (int) param.getResult();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("delete")
                                    .setMethodSignature("(String callingPkg, Uri url, String where, String[] selectionArgs)")
                                    .setReturn(String.valueOf(ret))
                                    .setParams(new ArrayList(Arrays.asList(callingPkg, UriToString(param2), param3)))
                                    .setStackTrace(stack)
                                    .setCount(mCountdelete)
                                    .build();
                            if(!(UriToString(param2).equals("null") || UriToString(param2).equals("Unkown")))
                                MsgSender.addMsg(msg);
                        } catch (Exception e) {
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }



    private void update() {
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "update",
                String.class,
                Uri.class,
                ContentValues.class,
                String.class,
                String[].class,
                new YH_MethodHook("android.content.ContentProviderProxy -> update(String callingPkg, Uri uri, ContentValues values, String where, String[] selectionArgs):更新content敏感信息"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            String stack = LogUtil.getStackTrace();
                            if(filterStack(stack))
                                return;
                            mCountupdate++;
                            String callingPkg=(String)param.args[0];
                            Uri param2 = (Uri)param.args[1];
                            ContentValues param3 = (ContentValues)param.args[2];
                            int ret = (int)param.getResult();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("update")
                                    .setMethodSignature("(String callingPkg, Uri uri, ContentValues values, String where, String[] selectionArgs)")
                                    .setReturn(String.valueOf(ret))
                                    .setParams(new ArrayList(Arrays.asList(callingPkg, UriToString(param2), ContentValuesToString(param3))))
                                    .setStackTrace(stack)
                                    .setCount(mCountupdate)
                                    .build();
                            if(!(UriToString(param2).equals("null") || UriToString(param2).equals("Unkown")))
                                MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    private boolean filterStack(String stack){
        String[] stacks = stack.split("\n");
        if (stacks.length >= 5){
            String call = stacks[4];
            if (call.contains("android.content.ContentResolver") || call.contains("android.content.ContentProviderClient")){
                return true;
            }
        }
        return false;
    }
}