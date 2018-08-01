package com.tarenwang.trwgamesdk.utils;

import android.content.Context;
import android.widget.Toast;

import com.tarenwang.trwgamesdk.sdk.TRWSDK;


/**
 * Created by Administrator on 2016/10/27.
 */

public class SDKHelper {
    private static SDKHelper mInstance;
    private Context context;
//    private IWXAPI api;


    private SDKHelper() {

    }
//    public IWXAPI getApi(){
//        if (mInstance == null){
//            api = WXAPIFactory.createWXAPI(context, TRWSDK.getmWxAppID(),true);
//            api.registerApp(TRWSDK.getmWxAppID());
//        }
//        return api;
//    }

    public static SDKHelper getmInstance() {
        if (mInstance == null) {
            mInstance = new SDKHelper();
        }
        return mInstance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public static void showMessage(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
