package com.yooha.privacyplugin.Msg;

import com.yooha.privacyplugin.Util.LogUtil;
import java.util.LinkedList;

public class MsgSender {
    public static final LinkedList mMsg = new LinkedList<HookMessage>(); //消息队列

    public static void showMsg(){
        new Thread() {
            public void run() {
                while(true){
                    try {
                        HookMessage msg = getMsg();
                        if (msg != null){
                            printmsg(msg);
                        }
                    }catch (Throwable th) {
                        LogUtil.error(th.toString());
                    }
                }
            }
        }.start();
    }

    public static void addMsg(HookMessage msg){
        synchronized(mMsg) {
            mMsg.add(msg);
        }
    }

    public static HookMessage getMsg(){
        HookMessage msg = null;
        synchronized(mMsg) {
            if (mMsg.isEmpty()){
                return msg;
            }
            msg = (HookMessage)mMsg.poll();
        }
        return msg;
    }

    public static void printmsg(HookMessage msg){
        String show = ">>> " + msg.mClass + " -> " +
                msg.mMethod + msg.mMethodSignature +
                "\nTotal       ->  " + msg.mCount +
                "\ndescription ->  " + msg.mDescription +
                "\nParams      ->  " + msg.mParams +
                "\nReturn      ->  " + msg.mReturn +
                "\nStackTrace ->  \n" + msg.mStackTrace + "\n\n";
        LogUtil.info(show);
    }
}



