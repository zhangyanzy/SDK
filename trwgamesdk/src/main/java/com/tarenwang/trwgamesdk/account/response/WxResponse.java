package com.tarenwang.trwgamesdk.account.response;

import java.io.Serializable;

/**
 * Created by zhangyan on 2016/11/8.
 */

public class WxResponse implements Serializable {

    /**
     * code : 0000
     * msg : 微信登录成功
     * token : 5bd18c2409e55b8e55f2cae2b52df2d5
     * data : {"uid":123456789,
     *             "nickname":"微信123456791"}
     * uid : 123456789
     * nickname : 微信123456791
     */

    private String code;
    private String msg;
    private String token;

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
        private long uid;
        private String nickname;

        public long getUid() {
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
