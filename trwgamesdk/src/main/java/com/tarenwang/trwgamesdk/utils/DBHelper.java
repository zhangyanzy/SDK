package com.tarenwang.trwgamesdk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * author MinoZhang
 * date 2016/10/26
 */

public class DBHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "userInfo.db";
    public static final String USER_TABLE_NAME = "user_table";
    public static final String[] USER_COLS = {DBUser.User.USERNAME,  DBUser.User.USERID,
            DBUser.User.USERTOKEN,DBUser.User.USE_LAST_LOGINTIME};
    private SQLiteDatabase db;
    private DBOpenHelper dbOpenHelper;

    public DBHelper(Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
        establishDb();
    }

    /**
     * 打开数据库
     */
    private void establishDb() {
        if (this.db == null) {
            this.db = this.dbOpenHelper.getWritableDatabase();
        }
    }

    /**
     * 插入一条记录
     *
     * @param map
     *            要插入的记录
     * @param tableName
     *            表名
     * @return 插入记录的id -1表示插入不成功
     */
    public long insertOrUpdate(String userName, String userId, String userToken,String userLastLoginTime) {
        boolean isUpdate = false;
        String[] userIds = queryAllUserId();
        for (int i = 0; i < userIds.length; i++) {
            if (userId.equals(userIds[i])) {
                isUpdate = true;
                break;
            }
        }
        long id = -1;
        if (isUpdate) {
            id = update(userName,userId,userToken,userLastLoginTime);
        } else {
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put(DBUser.User.USERNAME, userName);
                values.put(DBUser.User.USERID, userId);
                values.put(DBUser.User.USERTOKEN, userToken);
                values.put(DBUser.User.USE_LAST_LOGINTIME,userLastLoginTime);
                id = db.insert(USER_TABLE_NAME, null, values);
            }
        }
        return id;
    }

    /**
     * 删除一条记录
     *
     * @param userName
     *            用户名
     * @param tableName
     *            表名
     * @return 删除记录的id -1表示删除不成功
     */
    public long delete(String userId) {
        long id = db.delete(USER_TABLE_NAME, DBUser.User.USERID + " = '" + userId
                + "'", null);
        return id;
    }

    /**
     * 更新一条记录
     *
     * @param
     *
     * @param tableName
     *            表名
     *@param userToken  @return 更新过后记录的id -1表示更新不成功
     */
    public long update(String userName,  String userId, String userToken,String userLastLoginTime) {
        ContentValues values = new ContentValues();
        values.put(DBUser.User.USERNAME, userName);
        values.put(DBUser.User.USERID, userId);
        values.put(DBUser.User.USERTOKEN, userToken);
        values.put(DBUser.User.USE_LAST_LOGINTIME,userLastLoginTime);
        long id = db.update(USER_TABLE_NAME, values, DBUser.User.USERNAME + " = '"
                + userName + "'", null);
        return id;
    }

    /**
     * 根据userId查询Token
     *
     * @param userToken
     *            用户名
     * @param tableName
     *            表名
     * @return Hashmap 查询的记录
     */
    public String queryTokenByuserId(String userId) {
        String sql = "select * from " + USER_TABLE_NAME + " where "
                + DBUser.User.USERID + " = '" +userId+ "'";
        Cursor cursor = db.rawQuery(sql, null);
        String userToken = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            userToken = cursor.getString(cursor.getColumnIndex(DBUser.User.USERTOKEN));
        }

        return userToken;
    }
    /**
     * 根据userId查询上次登陆时间
     *
     * @param userName
     *            用户名
     * @param tableName
     *            表名
     * @return Hashmap 查询的记录
     */
    public String queryLastLoginTimeByUserId(String uId) {
        String sql = "select * from " + USER_TABLE_NAME + " where "
                + DBUser.User.USERNAME + " = '" + uId + "'";
        Cursor cursor = db.rawQuery(sql, null);
        String userLastLoginTime = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            userLastLoginTime = cursor.getString(cursor.getColumnIndex(DBUser.User.USE_LAST_LOGINTIME));
        }

        return userLastLoginTime;
    }

    /**
     * 通过userId查询userName
     * @param userName
     * @return
     */
    public String queryUserNameByUserId(String userId) {
        String sql = "select * from " + USER_TABLE_NAME + " where "
                + DBUser.User.USERID + " = '" +userId + "'";
        Cursor cursor = db.rawQuery(sql, null);
        String userName = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            userName = cursor.getString(cursor.getColumnIndex(DBUser.User.USERNAME));
        }
        return userName;
    }  /**
     * 通过userName查询userId
     * @param userName
     * @return
     */
    public String queryUserIdByUserName(String userName) {
        String sql = "select * from " + USER_TABLE_NAME + " where "
                + DBUser.User.USERNAME + " = '" +userName + "'";
        Cursor cursor = db.rawQuery(sql, null);
        String userId = "";
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            userId  = cursor.getString(cursor.getColumnIndex(DBUser.User.USERID));
        }
        return userId ;
    }

    /**
     * 查询所有用户名
     *
     * @param tableName
     *            表名
     * @return 所有记录的list集合
     */
    public String[] queryAllUserName() {
        if (db != null) {
            Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null,
                    null, null);
            int count = cursor.getCount();
            String[] userNames = new String[count];
            if (count > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < count; i++) {
                    userNames[i] = cursor.getString(cursor
                            .getColumnIndex(DBUser.User.USERNAME));
                    cursor.moveToNext();
                }
            }
            return userNames;
        } else {
            return new String[0];
        }

    }  /**
     * 查询所有uId
     * @param tableName 表名
     * @return 所有记录的list集合
     */
    public String[] queryAllUserId() {
        if (db != null) {
            Cursor cursor = db.query(USER_TABLE_NAME, null, null, null, null,
                    null, null);
            int count = cursor.getCount();
            String[] userIds = new String[count];
            if (count > 0) {
                cursor.moveToFirst();
                for (int i = 0; i < count; i++) {
                    userIds[i] = cursor.getString(cursor
                            .getColumnIndex(DBUser.User.USERID));
                    cursor.moveToNext();
                }
            }
            return userIds;
        } else {
            return new String[0];
        }

    }

    /**
     * 关闭数据库
     */
    public void cleanup() {
        if (this.db != null) {
            this.db.close();
            this.db = null;
        }
    }

    /**
     * 数据库辅助类
     */
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        //创建表
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + USER_TABLE_NAME + " (" + DBUser.User._ID
                    + " integer primary key," + DBUser.User.USERNAME + " text, "
                    + DBUser.User.USERID + " text, "+ DBUser.User.USERTOKEN+" text, "+DBUser.User.USE_LAST_LOGINTIME+" INTEGER)");
        }
        //更新表
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
            onCreate(db);
        }

    }

}
