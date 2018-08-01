package com.tarenwang.trwgamesdk.account.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */

public class AutologinRequest implements Serializable {
    /**
     * service : user.checkToken
     * uid : 123456789
     * game : {"appid":22,"channelid":1000}
     * token : 5bd18c2409e55b8e55f2cae2b52df2d5
     * sign : 4ca0abdb012c8f3b00426539265c8b80
     */

    private String service;
    private int uid;
    /**
     * appid : 22
     * channelid : 1000
     */

    private GameBean game;
    private String token;
    private String sign;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public GameBean getGame() {
        return game;
    }

    public void setGame(GameBean game) {
        this.game = game;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public static class GameBean {
        private int appid;
        private int devicetype;

        public int getAppid() {
            return appid;
        }

        public void setAppid(int appid) {
            this.appid = appid;
        }

        public int getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(int channelid) {
            this.devicetype = channelid;
        }
    }

}
