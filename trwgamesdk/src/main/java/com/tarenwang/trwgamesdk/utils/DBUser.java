package com.tarenwang.trwgamesdk.utils;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/10/26.
 */

public class DBUser {
    public static final class User implements BaseColumns {
        public static final String USERNAME = "username";
        public static final String USERID = "userid";
        public static final String USERTOKEN = "usertoken";
        public static final String USE_LAST_LOGINTIME = "userLastLoginTime";
    }
}
