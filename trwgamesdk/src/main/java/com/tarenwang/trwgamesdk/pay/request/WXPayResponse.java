package com.tarenwang.trwgamesdk.pay.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/20.
 */

public class WXPayResponse implements Serializable {


    /**
     * result : 200
     * msg : 成功
     * ts : 1478350691338
     * data : {"agentid":"2076375","tokenid":"H1304230018073A0_ed54ad6ccf811a0c293cb4d0a8905014","billno":"23432544421121"}
     */

    private String result;
    private String msg;
    private long ts;
    /**
     * agentid : 2076375
     * tokenid : H1304230018073A0_ed54ad6ccf811a0c293cb4d0a8905014
     * billno : 23432544421121
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

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String agentid;
        private String tokenid;
        private String billno;

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        public String getTokenid() {
            return tokenid;
        }

        public void setTokenid(String tokenid) {
            this.tokenid = tokenid;
        }

        public String getBillno() {
            return billno;
        }

        public void setBillno(String billno) {
            this.billno = billno;
        }
    }
}
