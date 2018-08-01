package com.tarenwang.trwgamesdk.sdk;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.tarenwang.trwgamesdk.floatmenu.manager.FloatMenuManager;
import com.tarenwang.trwgamesdk.pay.activity.ItemPaymentActivity;
import com.tarenwang.trwgamesdk.pay.request.GameOrderBean;
import com.tarenwang.trwgamesdk.utils.DBHelper;
import com.tarenwang.trwgamesdk.utils.DialogUtils;
import com.tarenwang.trwgamesdk.floatmenu.manager.ExitDialogUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/25.
 */

public class TRWSDK {

    public static final String SDK_VERSION = "1.0.0";
    private static Context mContext;
    private static int mAppId;
    private static String mAppName;
    private static String mPackageName;

    public static String getmAppName() {
        return mAppName;
    }


    public static String getmPackageName() {
        return mPackageName;
    }


    private static String mDeviceId;

    public static String getmWxAppID() {
        return mWxAppID;
    }

    public static void setmWxAppID(String mWxAppID) {
        TRWSDK.mWxAppID = mWxAppID;
    }

    private static String mWxAppID;
    private static LoginCallBack mLoginCallBack;
    private static FMLogoutCallBack mFMLogoutCallBack;
    private static PayCallBack mPayCallBack;
    private static ExitCallBack mExitCallBack;
    private static LogoutCallBack mLogoutCallBack;
    private static GameOrderBean mGameOrderBean;


    public TRWSDK() {

    }

    public static void initialize(Context context, int appId) {
//        mAppName = appName;
//        mPackageName = packageName;
        mContext = context;
        mAppId = appId;
        SDKHelper.getmInstance().setContext(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(SDKHelper.getmInstance().getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (!permissionList.isEmpty()) {
                ActivityCompat.requestPermissions((Activity) SDKHelper.getmInstance().getContext(), permissionList.toArray(new String[permissionList.size()]), 1);
            } else {
                try {
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    mDeviceId = tm.getDeviceId();
                    TRWGlobalVariable.getInstance().setInit(true);
                    Log.i("phone", "initialize: "+mDeviceId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            mDeviceId = tm.getDeviceId();
            TRWGlobalVariable.getInstance().setInit(true);
            Log.i("phone", "initialize: "+mDeviceId);
        }
    }

    public static void startFloatMenu(Context context, FMLogoutCallBack fmLogoutCallBack) {
        mFMLogoutCallBack = fmLogoutCallBack;
        FloatMenuManager.getInstance().startFloatView(context);
    }

    public static void doLogin(Context context, LoginCallBack loginCallBack) {
        mLoginCallBack = loginCallBack;
        if (TRWGlobalVariable.getInstance().getUid() == null) {
            if (TRWGlobalVariable.getInstance().getLogout_flag() == false) {
                checkUser(context);
            } else {
                DialogUtils.getInstance().showCommonDialog(context);
            }
        } else {
            DialogUtils.getInstance().showCommonDialog(context);
        }
    }

    public static void doPay(Context context, GameOrderBean gameOrderBean, PayCallBack payCallBack) {
        mPayCallBack = payCallBack;
        mGameOrderBean = gameOrderBean;
        Intent intent = new Intent(context, ItemPaymentActivity.class);
        context.startActivity(intent);
    }

    private static void checkUser(Context context) {
        String[] userIds = new DBHelper(context).queryAllUserId();
        if (userIds.length == 0) {
            DialogUtils.getInstance().showCommonDialog(context);
        } else {
            DialogUtils.getInstance().showAutomaticLogin(context, userIds);
        }

    }

    public static void doExit(Context context, ExitCallBack exitCallBack) {
        mExitCallBack = exitCallBack;
        FloatMenuManager.getInstance().destroy();
        ExitDialogUtils.getInstance().showExitDialog(context);
    }

    public static void doLogout(Context context, LogoutCallBack logoutCallBack) {
        mLogoutCallBack = logoutCallBack;
        ExitDialogUtils.getInstance().showLoginOutDialog(context);
    }

    public static GameOrderBean getGameOrderBean() {
        return mGameOrderBean;
    }


    public static int getmAppId() {
        return mAppId;
    }

    public static String getmDeviceId() {
        return mDeviceId;
    }


    public static void Exit(boolean isExit) {
        if (isExit) {
            mExitCallBack.onSuccess(isExit);
        }
    }

    public static void LoginComplete(String nickName, String userId, String token) {
        mLoginCallBack.onSuccessful(nickName, userId, token);
    }


    public static void LogoutResult(boolean isLogout) {
        if (isLogout) {
            mLogoutCallBack.isLogout(isLogout);
        } else {
            mLogoutCallBack.isLogout(isLogout);
        }
    }

    public static void FMLogoutResult(boolean isFMLogout) {
        if (isFMLogout) {
            mFMLogoutCallBack.onSuccess(isFMLogout);
        }
    }

    public static void PayComplete(boolean status) {

        if (status) {
            mPayCallBack.onSuccessful(status);
        } else {
            mPayCallBack.onFailure(status);
        }
    }


}

