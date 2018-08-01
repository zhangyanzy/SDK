package com.tarenwang.trwgamesdk.pay.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class AlipayResponse implements Serializable {
    /**
     * result : 200
     * msg : 成功
     * ts : 1478350691338
     * data : {"order_info":"app_id=2015052600090779&biz_content=%7B%22body%22%3A%22%E8%A5%BF%E6%B8%B8%E8%90%8C%E8%90%8C-%E7%94%B5%E4%BF%A1%E4%B8%80%E5%8C%BA-%E8%87%B3%E5%B0%8A%E7%A4%BC%E5%8C%85%22%2C%22subject%22%3A%22%E8%87%B3%E5%B0%8A%E7%A4%BC%E5%8C%85%22%2C%22out_trade_no%22%3A%2270501111111S001111119%22%2C%22total_amount%22%3A%226800.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%220%22%2C%22passback_params%22%3A%221234_1001_3000_10239301233_400010_1478322123%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fpay.tarenwang.com%2Fapi%2Fmobile%2Fnotify%2F&sign_type=RSA&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D"}
     */

    private String result;
    private String msg;
    private long ts;
    /**
     * order_info : app_id=2015052600090779&biz_content=%7B%22body%22%3A%22%E8%A5%BF%E6%B8%B8%E8%90%8C%E8%90%8C-%E7%94%B5%E4%BF%A1%E4%B8%80%E5%8C%BA-%E8%87%B3%E5%B0%8A%E7%A4%BC%E5%8C%85%22%2C%22subject%22%3A%22%E8%87%B3%E5%B0%8A%E7%A4%BC%E5%8C%85%22%2C%22out_trade_no%22%3A%2270501111111S001111119%22%2C%22total_amount%22%3A%226800.00%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22goods_type%22%3A%220%22%2C%22passback_params%22%3A%221234_1001_3000_10239301233_400010_1478322123%22%7D&charset=utf-8&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fpay.tarenwang.com%2Fapi%2Fmobile%2Fnotify%2F&sign_type=RSA&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D
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
        private String order_info;

        public String getOrder_info() {
            return order_info;
        }

        public void setOrder_info(String order_info) {
            this.order_info = order_info;
        }
    }
}
