package com.tarenwang.trwgamesdk.pay.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class AlipayRequest implements Serializable {


    /**
     * did : 352746067498602
     * appid : 1234
     * channel : 1001
     * data : {"amount":6800,"ext_data":"1234_1001_3000_10239301233_10034562180_400010_1478322123","login_account":"stonechan","nick_name":"%e6%b8%b8%e5%ae%a2","product_id":"400010","product_name":"%e8%87%b3%e5%b0%8a%e7%a4%bc%e5%8c%85","user_id":"10239301233","role_id":"10034562180","server_id":"3000","server_name":"%e7%94%b5%e4%bf%a1%e4%b8%80%e5%8c%ba","sign":"3737b23ac8ab3b2a66e1be2cc086ba7d"}
     */

    private String did;
    private String appid;
    private String channel;
    /**
     * amount : 6800.0
     * ext_data : 1234_1001_3000_10239301233_10034562180_400010_1478322123
     * login_account : stonechan
     * nick_name : %e6%b8%b8%e5%ae%a2
     * product_id : 400010
     * product_name : %e8%87%b3%e5%b0%8a%e7%a4%bc%e5%8c%85
     * user_id : 10239301233
     * role_id : 10034562180
     * server_id : 3000
     * server_name : %e7%94%b5%e4%bf%a1%e4%b8%80%e5%8c%ba
     * sign : 3737b23ac8ab3b2a66e1be2cc086ba7d
     */

    private DataBean data;

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

    public DataBean getData(DataBean dataBean) {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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
