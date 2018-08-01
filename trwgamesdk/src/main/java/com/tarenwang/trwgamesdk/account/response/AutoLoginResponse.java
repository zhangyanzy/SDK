package com.tarenwang.trwgamesdk.account.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AutoLoginResponse implements Serializable {

    /**
     * code : 0000
     * msg : 登录成功
     * data : {"uid":123456789,"username":"test001@qq.com","nickname":"塔人测试 001"}
     */

    private String code;
    private String msg;
    /**
     * uid : 123456789
     * username : test001@qq.com
     * nickname : 塔人测试 001
     */

    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int uid;
//        private String username;
        private String nickname;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

//        public String getNickname() {
//            return username;
//        }
//
//        public void setNickname(String username) {
//            this.username = username;
//        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
