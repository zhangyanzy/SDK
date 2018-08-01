/*
 * Copyright (c) 2016, Shanghai YUEWEN Information Technology Co., Ltd.
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *  Neither the name of Shanghai YUEWEN Information Technology Co., Ltd. nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY SHANGHAI YUEWEN INFORMATION TECHNOLOGY CO., LTD. AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.tarenwang.trwgamesdk.floatmenu;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;

import com.tarenwang.trwgamesdk.floatmenu.manager.FloatMenuManager;
import com.tarenwang.trwgamesdk.floatmenu.widget.FloatMenu;
import com.tarenwang.trwgamesdk.floatmenu.widget.MenuItem;
import com.tarenwang.trwgamesdk.floatmenu.widget.MenuItemView;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;
import com.tarenwang.trwgamesdk.utils.ResourceUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;

import java.util.ArrayList;

/**
 * author MinoZhang
 * date 2017/2/9
 */
public class FloatMenuService extends Service implements View.OnClickListener {

    //    private ExitDialogUtils exitDialog;
    private Handler mHandler = new Handler();
    private FloatMenu mFloatMenu;
    private int[] menuIcons = new int[]{ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_menu_logout")};

    /**
     * On bind binder.
     *
     * @param intent the intent
     * @return the binder
     */
    @Override
    public IBinder onBind(Intent intent) {
        return new FloatMenuServiceBinder();
    }


    /**
     * On create.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        ArrayList<MenuItem> mMenuItems = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            mMenuItems.add(new MenuItem(menuIcons[i], Consts.MENU_ITEMS[i], android.R.color.tab_indicator_text, this));
        }
        mFloatMenu = new FloatMenu.Builder(this).menuItems(mMenuItems).build();
        mFloatMenu.show();
    }

    /**
     * On click.
     *
     * @param v the v
     */
    @Override
    public void onClick(View v) {
        if (v instanceof MenuItemView) {
            MenuItemView menuItemView = (MenuItemView) v;
            String menuItemLabel = menuItemView.getMenuItem().getLabel();
            switch (menuItemLabel) {
                case Consts.LOGOUT:
                    mFloatMenu.startLoaderAnim();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mFloatMenu.stopLoaderAnim();
                                    if (mFloatMenu != null && mFloatMenu.isShown()) {
                                        FloatMenuManager.getInstance().destroy();
                                    }
                                    TRWSDK.FMLogoutResult(true);
//                                    ExitDialogUtils.getInstance().showLoginOutDialog();
                                }
                            });
                        }
                    }).start();

                    break;
            }
        }
    }

    /**
     * Show float.
     */
    public void showFloat() {
        if (mFloatMenu != null)
            mFloatMenu.show();
    }

    /**
     * Hide float.
     */
    public void hideFloat() {
        if (mFloatMenu != null) {
            mFloatMenu.hide();
        }
    }

    /**
     * Destroy float.
     */
    public void destroyFloat() {
        hideFloat();
        if (mFloatMenu != null) {
            mFloatMenu.destroy();
        }
        mFloatMenu = null;
    }

    /**
     * On destroy.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyFloat();
    }

    public class FloatMenuServiceBinder extends Binder {
        public FloatMenuService getService() {
            return FloatMenuService.this;
        }
    }

}
