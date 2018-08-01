package com.tarenwang.trwgamesdk.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/10/26.
 */

public class Validator {
    /**
     * 匹配是否为手机号码
     */
    public static boolean isMobileNO(String mobiles) {
        String str = "^((13[0-9])|(147)|(15[0,2,3,5-9])|(17[0,6-8])|(18[0-3,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isUserNumber(String mobiles){
        boolean re = false;
        //控制前2位：13,14，15,17,18，总长度11位子
        if (TextUtils.isEmpty(mobiles)){
            return false;
        }
        if (mobiles.length() == 11){
            if (mobiles.startsWith("13")){
                re = true;
            }else if (mobiles.startsWith("14")){
                re = true;
            }else if (mobiles.startsWith("15")){
                re = true;
            }else if (mobiles.startsWith("17")){
                re = true;
            }else if (mobiles.startsWith("18")){
                re = true;
            }
        }
        return re;

    }
}
