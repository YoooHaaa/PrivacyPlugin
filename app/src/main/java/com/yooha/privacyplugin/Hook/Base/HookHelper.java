package com.yooha.privacyplugin.Hook.Base;


import com.yooha.privacyplugin.Common.Data;
import com.yooha.privacyplugin.Hook.Anti.HookAnti;
import com.yooha.privacyplugin.Hook.Binder.HookBinderProxy;
import com.yooha.privacyplugin.Hook.Bluetooth.HookBluetoothAdapter;
import com.yooha.privacyplugin.Hook.Bluetooth.HookBluetoothDevice;
import com.yooha.privacyplugin.Hook.Camera.HookCamera;
import com.yooha.privacyplugin.Hook.Clipboard.HookClipboardManager;
import com.yooha.privacyplugin.Hook.Content.HookContentProviderClient;
import com.yooha.privacyplugin.Hook.Content.HookContentProviderProxy;
import com.yooha.privacyplugin.Hook.Content.HookContentResolver;
import com.yooha.privacyplugin.Hook.Location.HookCdmaCellLocation;
import com.yooha.privacyplugin.Hook.Location.HookGsmCellLocation;
import com.yooha.privacyplugin.Hook.Location.HookLocation;
import com.yooha.privacyplugin.Hook.Location.HookLocationManager;
import com.yooha.privacyplugin.Hook.Media.HookMediaDrm;
import com.yooha.privacyplugin.Hook.Net.HookInetAddress;
import com.yooha.privacyplugin.Hook.Net.HookNetworkInfo;
import com.yooha.privacyplugin.Hook.Net.HookNetworkInterface;
import com.yooha.privacyplugin.Hook.Net.HookWifiInfo;
import com.yooha.privacyplugin.Hook.Net.HookWifiManager;
import com.yooha.privacyplugin.Hook.Other.HookContextIml;
import com.yooha.privacyplugin.Hook.PM.HookActivityManager;
import com.yooha.privacyplugin.Hook.PM.HookApplicationPackageManager;
import com.yooha.privacyplugin.Hook.PM.HookPackageManager;
import com.yooha.privacyplugin.Hook.Reflect.HookField;
import com.yooha.privacyplugin.Hook.Reflect.HookReflectField;
import com.yooha.privacyplugin.Hook.Reflect.HookReflectMethod;
import com.yooha.privacyplugin.Hook.Sensor.HookSensorManager;
import com.yooha.privacyplugin.Hook.Setting.HookBuild;
import com.yooha.privacyplugin.Hook.Setting.HookSettingsSecure;
import com.yooha.privacyplugin.Hook.Setting.HookSettingsSystem;
import com.yooha.privacyplugin.Hook.Setting.HookSystemProperties;
import com.yooha.privacyplugin.Hook.Sms.HookSmsManager;
import com.yooha.privacyplugin.Hook.Telephone.HookTelephonyManager;
import com.yooha.privacyplugin.Msg.MsgSender;
import com.yooha.privacyplugin.Util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HookHelper {
    private static HookHelper mHelper;
    private String mPkg = null;
    private ClassLoader mLoader = null;
    private final List<HookMethods> hooks = new ArrayList<>();

    public void hook(){
        for (HookMethods hook : hooks) {
            try {
                hook.hook();
            } catch (Exception err) {
                LogUtil.error(err.toString());
            }
        }
    }

    private HookHelper(String pkg){
        this.mPkg = pkg;
    }

    public static HookHelper sharedInstance(String pkg){
        if (mHelper == null) {
            synchronized (HookHelper.class) {
                if (mHelper == null) {
                    mHelper = new HookHelper(pkg);
                }
            }
        }
        return mHelper;
    }

    public HookHelper setClassLoader(ClassLoader loader){
        this.mLoader = loader;
        return this;
    }

    public HookHelper registerHook() throws Exception {
        if (this.mLoader == null){
            throw new Exception("[HookHelper -> registerHook] NUllClassLoader");
        }
        try{
            MsgSender.showMsg();
            hooks.add(new HookTelephonyManager(this.mLoader, this.mPkg, Data.LEVEL_GENERAL));
            hooks.add(new HookWifiInfo(this.mLoader, this.mPkg, Data.LEVEL_RISK));
            hooks.add(new HookNetworkInterface( this.mLoader, this.mPkg, Data.LEVEL_RISK));
            hooks.add(new HookLocationManager( this.mLoader, this.mPkg, Data.LEVEL_RISK));
            hooks.add(new HookApplicationPackageManager( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookPackageManager( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookSettingsSecure( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookSettingsSystem( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookSystemProperties( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookBluetoothAdapter( this.mLoader, this.mPkg, Data.LEVEL_GENERAL));
            hooks.add(new HookBluetoothDevice( this.mLoader, this.mPkg, Data.LEVEL_GENERAL));
            hooks.add(new HookCamera(this.mLoader, this.mPkg, Data.LEVEL_GENERAL));
            hooks.add(new HookInetAddress( this.mLoader, this.mPkg, Data.LEVEL_GENERAL));
            hooks.add(new HookActivityManager( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookCdmaCellLocation( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookGsmCellLocation( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookWifiManager( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookLocation( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookBuild( this.mLoader, this.mPkg, Data.LEVEL_GENERAL));
            hooks.add(new HookClipboardManager( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
            hooks.add(new HookSmsManager( this.mLoader, this.mPkg,Data.LEVEL_RISK));
            hooks.add(new HookContextIml(this.mLoader,this.mPkg,Data.LEVEL_RISK));
            hooks.add(new HookContentProviderProxy(this.mLoader,this.mPkg,Data.LEVEL_RISK));
            hooks.add(new HookBinderProxy(this.mLoader,this.mPkg,Data.LEVEL_RISK));
            hooks.add(new HookAnti(this.mLoader,this.mPkg,Data.LEVEL_RISK));


//            hooks.add(new HookContentResolver( this.mLoader, this.mPkg, Data.LEVEL_RISK));
//            hooks.add(new HookContentProviderClient( this.mLoader, this.mPkg, Data.LEVEL_RISK));
//            hooks.add(new HookMediaDrm( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
//            hooks.add(new HookSensorManager( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
//            hooks.add(new HookField(this.mLoader,this.mPkg,Data.LEVEL_RISK));
//            hooks.add(new HookReflectField( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
//            hooks.add(new HookReflectMethod( this.mLoader, this.mPkg, Data.LEVEL_SENSITIVE));
//            hooks.add(new HookNetworkInfo( this.mLoader, this.mPkg, Data.LEVEL_GENERAL));

        }catch (Exception err) {
            LogUtil.error(err.toString());
        }
        return this;
    }
}


