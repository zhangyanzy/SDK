package com.tarenwang.trwgamesdk.floatmenu.manager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.tarenwang.trwgamesdk.sdk.TRWGlobalVariable;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;
import com.tarenwang.trwgamesdk.utils.ResourceUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;


/**
 * Created by zhangyan on 2017/2/6.
 */

public class ExitDialogUtils {

    private Context mContext;
    private Button mSure;
    private Button mCancel;

    private Button mLoginOutSure;
    private Button mLoginOutCancel;

    private Dialog exitDialog;
    private Dialog loginOutDialog;
    private View view;

    private static ExitDialogUtils sInstance;


    public ExitDialogUtils() {

    }

    public static ExitDialogUtils getInstance() {
        if (sInstance == null) {
            sInstance = new ExitDialogUtils();
        }
        return sInstance;
    }

    public View showExitDialog(final Context context) {

        exitDialog = new Dialog(context,
                ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "trw_ExitDialog"));
        view = LayoutInflater.from(context).
                inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_exit_dialog"), null);
        mSure = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "exit_sure"));
        mCancel = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "exit_cancel"));
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.setCancelable(true);
        exitDialog.show();
        exitDialog.setContentView(view);
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TRWSDK.Exit(true);
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideExitDialog();
            }
        });
        return view;
    }

    public View showLoginOutDialog(final Context context) {
        final Activity mActivity = (Activity) context;
        loginOutDialog = new Dialog(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "trw_ExitDialog"));
        view = LayoutInflater.from(context).inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_login_out"), null);
        mLoginOutSure = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_out_sure"));
        mLoginOutCancel = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_out_cancel"));
        loginOutDialog.setCanceledOnTouchOutside(false);
        loginOutDialog.setCancelable(true);
        loginOutDialog.show();
        loginOutDialog.setContentView(view);
        mLoginOutSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TRWGlobalVariable.getInstance().setUid(null);
                TRWGlobalVariable.getInstance().setLogout_flag(true);
                TRWSDK.LogoutResult(true);
                if(mActivity!=null&&!mActivity.isFinishing()){
                    hideLogoutDialog();
                }

            }
        });

        mLoginOutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TRWSDK.LogoutResult(false);
                if(mActivity!=null&&!mActivity.isFinishing()){
                    hideLogoutDialog();
                }
            }
        });
        return view;
    }

    public void hideExitDialog() {
        if (exitDialog != null) {
            exitDialog.dismiss();
        }
    }

    public void hideLogoutDialog() {
        if (loginOutDialog != null) {
            loginOutDialog.dismiss();
        }
    }


}

