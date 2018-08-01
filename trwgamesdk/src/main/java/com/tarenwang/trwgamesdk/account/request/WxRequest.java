package com.tarenwang.trwgamesdk.account.request;

import java.io.Serializable;

/**
 * Created by zhangyan on 2016/11/8.
 */

public class WxRequest implements Serializable {


    private String service;
    private String deviceid;
    private GameBean game;
    //private String sign;


    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public GameBean getGame() {
        return game;
    }

    public void setGame(GameBean game) {
        this.game = game;
    }

//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }

    public static class GameBean {
        private String appid;
        private String devicetype;
        private String weixinappid;
        private String weixincode;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(String channelid) {
            this.devicetype = channelid;
        }

        public String getWeixinappid() {
            return weixinappid;
        }

        public void setWeixinappid(String weixinappid) {
            this.weixinappid = weixinappid;
        }

        public String getWeixincode() {
            return weixincode;
        }

        public void setWeixincode(String weixincode) {
            this.weixincode = weixincode;
        }
    }
}
