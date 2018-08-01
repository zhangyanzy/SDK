package com.tarenwang.trwgamesdk.account.request;

import java.io.Serializable;

/**
 * Created by zhangyan on 2016/11/14.
 */

public class WXPayRequest implements Serializable {

    private String did;
    private String appid;
    private String channel;
    private DataBean data;

//
//    private String app_name;
//    private String scene_type;
//    private String package_name;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
//    public String getApp_name() {
//        return app_name;
//    }

//    public void setApp_name(String app_name) {
//        this.app_name = app_name;
//    }
//
//    public String getScene_type() {
//        return scene_type;
//    }
//
//    public void setScene_type(String scene_type) {
//        this.scene_type = scene_type;
//    }
//
//    public String getPackage_name() {
//        return package_name;
//    }
//
//    public void setPackage_name(String package_name) {
//        this.package_name = package_name;
//    }

    public static class DataBean {
        private String amount;
        private String ext_data;
        private String login_account;
        private String nick_name;
        private String product_id;
        private String product_name;
        private int user_id;
        private String role_id;
        private String server_id;
        private String server_name;
        private String sign;


        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getExt_data() {
            return ext_data;
        }

        public void setExt_data(String ext_data) {
            this.ext_data = ext_data;
        }

        public String getLogin_account() {
            return login_account;
        }

        public void setLogin_account(String login_account) {
            this.login_account = login_account;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public String getServer_id() {
            return server_id;
        }

        public void setServer_id(String server_id) {
            this.server_id = server_id;
        }

        public String getServer_name() {
            return server_name;
        }

        public void setServer_name(String server_name) {
            this.server_name = server_name;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

}
