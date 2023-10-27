package com.yooha.privacyplugin.Hook.Net;

import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Base.HookMethods;
import com.yooha.privacyplugin.Msg.HookMessage;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;
import com.yooha.privacyplugin.Xposed.YH_MethodHook;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Enumeration;

import de.robv.android.xposed.XposedHelpers;

public class HookNetworkInterface extends HookMethods {
    public int mCountgetHardwareAddress = 0;
    public int mCountgetInetAddresses = 0;

    public HookNetworkInterface(ClassLoader loader, String pkg, int level){
        super( "java.net.NetworkInterface", loader, pkg, level);
    }

    public HookNetworkInterface(ClassLoader loader, String pkg){
        this(loader, pkg, Data.LEVEL_GENERAL);
    }


    public void hookMethod(){
        try{
            getHardwareAddress();
            //getInetAddresses();
        }catch (Exception e){
            LogUtil.error(e.toString());
        }
    }

    public void getHardwareAddress(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getHardwareAddress",
                new YH_MethodHook("java.net.NetworkInterface -> getHardwareAddress():获取mac地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetHardwareAddress++;
                            byte[] ret = (byte[])param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getHardwareAddress")
                                    .setMethodSignature("()")
                                    .setReturn(byte2hex(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetHardwareAddress)
                                    .setDescription("获取mac地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }

    public String byte2hex(byte[] bytes){
        if (bytes == null){
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        String tmp = null;
        for(byte b: bytes){
            //将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
            tmp = Integer.toHexString(0xFF & b);
            if(tmp.length() == 1){
                tmp = "0" + tmp;
            }
            sb.append(tmp + " ");
        }
        return sb.toString();
    }

    public static char[] getChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }

    public String InetAddressToString(Enumeration<InetAddress> addr){
        StringBuffer sb = new StringBuffer();
        while (addr.hasMoreElements()){
            InetAddress inetAddress = addr.nextElement();
            if (!inetAddress.isLoopbackAddress()) {
                String ipaddress = inetAddress.getHostAddress();
                sb.append("<" + ipaddress + ">");
            }
        }
        return sb.toString();
    }

    public void getInetAddresses(){
        XposedHelpers.findAndHookMethod(this.mCls,
                this.mLoader,
                "getInetAddresses",
                new YH_MethodHook("java.net.NetworkInterface -> getInetAddresses():获取网络接口绑定的所有IP地址"){
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        try{
                            mCountgetInetAddresses++;
                            Enumeration<InetAddress> ret = (Enumeration<InetAddress>)param.getResult();
                            String stack = LogUtil.getStackTrace();
                            HookMessage msg = new HookMessage.Builder()
                                    .setLevel(mLevel)
                                    .setClass(mCls)
                                    .setMethod("getInetAddresses")
                                    .setMethodSignature("()")
                                    .setReturn(InetAddressToString(ret))
                                    .setStackTrace(stack)
                                    .setCount(mCountgetInetAddresses)
                                    .setDescription("获取网络接口绑定的所有IP地址")
                                    .build();
                            MsgSender.addMsg(msg);
                        }catch (Exception e){
                            LogUtil.error(e.toString());
                        }
                    }
                });
    }
}
