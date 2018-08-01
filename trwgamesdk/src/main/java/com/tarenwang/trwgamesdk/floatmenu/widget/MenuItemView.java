package com.tarenwang.trwgamesdk.floatmenu.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tarenwang.trwgamesdk.utils.ResourceUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;

/**
 * author:MinoZhang
 * date:2017/2/7
 */

public class MenuItemView extends LinearLayout {
    private ImageView menuLogo;
    private TextView menuTitle;
    private View menuItemDivider;
    private MenuItem mMenuItem;

    public MenuItem getMenuItem() {
        return mMenuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        mMenuItem = menuItem;
    }

    public MenuItemView(Context context, MenuItem item) {
        super(context);
        this.mMenuItem = item;
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_menu_item"), this);
        menuLogo = (ImageView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "menuLogoImg"));
        menuTitle = (TextView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "menuItemTxt"));
        menuItemDivider = view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "menuItemDivider"));
        setBackgroundColor(Color.TRANSPARENT);
        setLayoutParams(FloatMenuUtils.createWrapParams());
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER);
        menuLogo.setImageResource(mMenuItem.getIcon());
        menuLogo.setClickable(false);
        menuTitle.setText(mMenuItem.getLabel());
        menuTitle.setTextColor(context.getResources().getColor(mMenuItem.getTextColor()));
        if (mMenuItem.showDivider) {
            menuItemDivider.setVisibility(VISIBLE);
        } else {
            menuItemDivider.setVisibility(INVISIBLE);
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup parent = (ViewGroup)getParent();
                parent.setAnimation(FloatMenuUtils.getExitScaleAlphaAnimation(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }));
            }
        });
        setOnClickListener(mMenuItem.getOnClickListener());
        view.bringToFront();
    }
    public void setImageView(@DrawableRes int drawableRes) {
        menuLogo.setImageResource(drawableRes);
    }


    public void setDividerGone(boolean dividerGone){
        menuItemDivider.setVisibility(dividerGone?GONE:VISIBLE);
    }
}
