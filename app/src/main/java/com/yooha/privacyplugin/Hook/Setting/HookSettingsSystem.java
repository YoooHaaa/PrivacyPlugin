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

public class HookSettingsSystem extends HookMethods {
    public int mCountgetString = 0;

    public HookSettingsSystem(ClassLoader loader, String pkg, int level){
        super( "android.provider.Settings$System", loader, pkg, level);
    }

    public HookSettingsSystem(ClassLoader loader, String pkg){
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
                new YH_MethodHook("android.provider.Settings$System -> getStringForUser(ContentResolver cr,String name,int userHandle):获取系统设置"){
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


}
