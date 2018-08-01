package com.tarenwang.trwgamesdk.account.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */

public class LoginResponse implements Serializable {

    /**
     * code : 0000
     * msg : 登录申请成功
     * token : 3f5f5f25e1e7899eca77f9d25b61e1ee
     * data : {"uid":125,"nickname":"18616329606"}
     */

    private String code;
    private String msg;
    private String token;
    /**
     * uid : 125
     * nickname : 18616329606
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int uid;
        private String nickname;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }
}
