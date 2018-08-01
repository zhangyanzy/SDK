package com.tarenwang.trwgamesdk.utils;

import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhangyan on 2016/10/26.
 */

public class TipsToast extends Toast {

    private Context context;

    public TipsToast(Context context) {
        super(context);
    }

    public static TipsToast makeText(Context context, int iconResId, CharSequence topText, CharSequence bottomText, CharSequence accountText,
                                     int duration) {
        TipsToast result = new TipsToast(context);

        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_view_tips"), null);
        v.getBackground().setAlpha(220);
        TextView top_tv = (TextView) v.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "tips_msg"));
        TextView bottom_tv = (TextView) v.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "bottom_msg"));
        TextView account_tv = (TextView) v.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "account"));
        ImageView tip_icon = (ImageView) v.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "tips_icon"));
        top_tv.setText(topText);
        bottom_tv.setText(bottomText);
        account_tv.setText(accountText);
        tip_icon.setImageResource(iconResId);
        result.setView(v);
        // setGravity方法用于设置位置，此处为垂直居中
        result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        result.setDuration(duration);

        return result;
    }

    public static TipsToast makeText(Context context, int resId, int duration)
            throws Resources.NotFoundException {
        return (TipsToast) makeText(context, context.getResources().getText(resId),
                duration);
    }

    public void setIcon(int iconResId) {
        if (getView() == null) {
            throw new RuntimeException(
                    "This Toast was not created with Toast.makeText()");
        }
        ImageView iv = (ImageView) getView().findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "tips_icon"));
        if (iv == null) {
            throw new RuntimeException(
                    "This Toast was not created with Toast.makeText()");
        }
        iv.setImageResource(iconResId);
    }

    @Override
    public void setText(CharSequence s) {
        if (getView() == null) {
            throw new RuntimeException(
                    "This Toast was not created with Toast.makeText()");
        }

        TextView tip_tv = (TextView) getView().findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "tips_msg"));
        TextView bottom_tv = (TextView) getView().findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "bottom_msg"));
        TextView account_tv = (TextView) getView().findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "account"));
        if (tip_tv == null) {
            throw new RuntimeException(
                    "This Toast was not created with Toast.makeText()");
        }
        tip_tv.setText(s);
        bottom_tv.setText(s);
        account_tv.setText(s);
    }
}
