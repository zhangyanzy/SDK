package com.tarenwang.trwgamesdk.account.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zhangyan on 2016/11/14.
 */

public class WXPayResponse implements Serializable {


    /**
     * result : 200
     * msg : 成功
     * ts : 1478350691338
     * data : {"appid":"wxd930ea5d5a258f4f","partnerid":"10000100","prepayid":"1101000000140415649af9fc314aa427","package":"Sign=WXPay","noncestr":"a462b76e7436e98e0ed6e13c64b4fd1c","timestamp":"1397527777","sign":"C54C020ED1BAAB4F6C03C7737FF2D80F"}
     */

    private String result;
    private String msg;
    private int ts;
    /**
     * appid : wxd930ea5d5a258f4f
     * partnerid : 10000100
     * prepayid : 1101000000140415649af9fc314aa427
     * package : Sign=WXPay
     * noncestr : a462b76e7436e98e0ed6e13c64b4fd1c
     * timestamp : 1397527777
     * sign : C54C020ED1BAAB4F6C03C7737FF2D80F
     */

    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTs() {
        return ts;
    }

    public void setTs(int ts) {
        this.ts = ts;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String appid;
        private String partnerid;
        private String prepayid;
        @SerializedName("package")
        private String packageX;
        private String noncestr;
        private String timestamp;
        private String sign;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }

}
