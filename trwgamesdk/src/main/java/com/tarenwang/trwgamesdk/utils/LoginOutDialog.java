package com.tarenwang.trwgamesdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tarenwang.trwgamesdk.sdk.ExitCallBack;


/**
 * Created by Administrator on 2017/2/6.
 */

public class LoginOutDialog extends Dialog {

    private Context mContext;
    private Button mSure;
    private Button mCancel;

    private static LoginOutDialog sInstance;

    public LoginOutDialog(Context context) {
        super(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "ExitDialog"));
        mContext = context;
    }

    public LoginOutDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_login_out"));
        this.setCanceledOnTouchOutside(false);
        mSure = (Button) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_out_sure"));
        mCancel = (Button) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_out_cancel"));
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TRWSDK.doOutRes(true,TRWSDK.getmUid());
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}
