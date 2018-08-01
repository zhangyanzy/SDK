package com.tarenwang.trwgamesdk.sdk;

/**
 * Created by zhangyan on 2017/2/7.
    注销回调
 */


public interface LoginOutCallBack {

    void onLogOutSuccess(boolean success);

    void onLogOutError(boolean error);
}
