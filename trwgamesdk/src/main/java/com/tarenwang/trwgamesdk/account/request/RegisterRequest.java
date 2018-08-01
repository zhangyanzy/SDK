package com.tarenwang.trwgamesdk.account.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/10/26.
 */

public class RegisterRequest implements Serializable {

    /**
     * service : user.directReg
     * username : test001@qq.com
     * deviceid : 1E2DFA89-496A-47FD-9941-DF1FC4E6484A
     * password : 123456
     * game : {"appid":22,"devicetype":1002}
     */

    private String service;
    private String username;
    private String deviceid;
    private String password;
    /**
     * appid : 22
     * devicetype : 1002
     */

    private GameBean game;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GameBean getGame() {
        return game;
    }

    public void setGame(GameBean game) {
        this.game = game;
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

        public void setDevicetype(int devicetype) {
            this.devicetype = devicetype;
        }
    }
}
