package com.tarenwang.trwgamesdk.account.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */

public class FastLoginRespnse implements Serializable {

    /**
     * code : 0000
     * msg : 快速登录申请成功
     * token : 5bd18c2409e55b8e55f2cae2b52df2d5
     * data : {"uid":123456789,"nickname":"游客123456791"}
     */

    private String code;
    private String msg;
    private String token;
    /**
     * uid : 123456789
     * nickname : 游客123456791
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
        private String username;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
