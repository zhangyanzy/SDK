package com.tarenwang.trwgamesdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zhangyan on 2016/11/29.
 */

public class DialogLoadingUtils {

    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_dialog_loading"), null);
        LinearLayout layout = (LinearLayout) v.findViewById
                (ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "trw_dialog_loading_view"));
        TextView textView = (TextView) v.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "textview"));
        textView.setText(msg);

        Dialog loadingDialog = new Dialog(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "dialog"));
        loadingDialog.setCancelable(true);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));



        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "PopWindowAnimStyle"));
        loadingDialog.show();

        return loadingDialog;
    }

    public static void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }

}
