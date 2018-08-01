package com.tarenwang.trwgamesdk.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * @description 判断屏幕横竖屏工具类
 * @author MinoZhang
 * @date 2016/12/2
 */

public class ScreenOrientationUtil {
    /**
     * 返回当前屏幕是否为竖屏
     * @param context
     * @return 当且仅当当前屏幕为竖屏时返回true，否则返回false
     */
    public static boolean isScreenOritationPortrait(Context context){
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
