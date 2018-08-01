package com.tarenwang.trwgamesdk.pay.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heepay.plugin.activity.Constant;
import com.heepay.plugin.api.HeepayPlugin;
import com.tarenwang.trwgamesdk.pay.request.GameOrderBean;
import com.tarenwang.trwgamesdk.pay.request.WXPayRequest;
import com.tarenwang.trwgamesdk.pay.request.WXPayResponse;
import com.tarenwang.trwgamesdk.pay.untils.AliPayTask;
import com.tarenwang.trwgamesdk.pay.untils.PayResult;
import com.tarenwang.trwgamesdk.sdk.TRWParams;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;
import com.tarenwang.trwgamesdk.utils.OkHttpUtils;
import com.tarenwang.trwgamesdk.utils.ResourceUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;
import com.tarenwang.trwgamesdk.utils.TaRenAPI;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinoZhang
 * @description 支付（支付宝，微信，塔人币）
 * @date 2016/11/1
 */

public class ItemPaymentActivity extends Activity {

    private TextView mIndentMoney, mGood, mAccount;
    private ImageView mBack;
    private RadioGroup mRadioGroup;
    private RadioButton mAlipay, mWechatpay, mTrcoinpay;
    private Button mPayNow;
    private String mCheck;
    private GestureDetector mGestureDetector;

    private ProgressDialog proDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_activity_item_payment"));
        initView();
        try {
            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListener();
        setGestureDetector();
        Log.i("asd", "onCreate" + "Going");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proDialog != null) {
            proDialog.dismiss();
        }
    }

    /**
     * 手指触摸动作监听
     */
    private void setGestureDetector() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getX() - e1.getX() > 0 && (e1.getX() >= 0 && e1.getX() <= 3000)) {
                    if
                            (Math.abs(e2.getX() - e1.getX()) > Math.abs(e2.getY() - e1.getY())
                            &&
                            Math.abs(velocityX) > 300) {
                        overridePendingTransition(0, ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(), "trw_activity_out_from_right"));
                        finish();
                        onBackPressed();
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 按系统back键，退出该Activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(), "trw_activity_out_from_right"));
        finish();
    }

    /**
     * 启动汇付宝安全支付服务
     */
    private void startHeepayServiceJar(WXPayResponse wxPayResponse) {
        String paramStr = wxPayResponse.getData().getTokenid() + "," + wxPayResponse.getData().getAgentid() + ","
                + wxPayResponse.getData().getBillno() + "," + 30;
        HeepayPlugin.pay(this, paramStr);
        Log.i("asd", "paramStr:" + paramStr);
    }

    /**
     * 接收支付通知结果
     * 01：成功
     * 00：处理中
     * -1：失败
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.RESULTCODE) {
            String respCode = data.getExtras().getString("respCode");
            String respMessage = data.getExtras().getString("respMessage");
            if (!TextUtils.isEmpty(respCode)) {
                if ("01".equals(respCode)) {
                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                }
                if ("02".equals(respCode)) {
                    Toast.makeText(getApplicationContext(), "处理中...", Toast.LENGTH_SHORT).show();
                }
                if ("03".equals(respCode)) {
                    Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
            if (!TextUtils.isEmpty(respMessage)) {
                //同步返回的结果必须放置到服务端进行验证，建议商户依赖异步通知
                PayResult result = new PayResult(respMessage);
                //同步需要返回的验证信息
                String resultInfo = result.getResult();
                String resultStatus = result.getResultStatus();
                //判断resultInfo为“9000”则代表支付成功，具体状态码代表可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        Toast.makeText(this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }
    }

    /**
     * 设置监听事件
     */
    private void setListener() {

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mrb = (RadioButton) group.findViewById(checkedId);
                mCheck = mrb.getTag().toString();
            }
        });

        mPayNow.setOnClickListener(new View.OnClickListener() {
            private long lastClick;
            @Override
            public void onClick(View view) {

                long currentClick = System.currentTimeMillis();
                if (currentClick - lastClick < 2000) {
                    lastClick = currentClick;
                    return;
                }
                lastClick = currentClick;
                Log.i("asd", "onClick: "+mCheck);
                if (mCheck == null) {
                    Toast.makeText(ItemPaymentActivity.this, "请先选择支付方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mCheck.equals("0")) {
                    Log.i("asd", "onClick: "+"开启支付");
                    AliPayTask aliPayTask = new AliPayTask(getApplicationContext(), ItemPaymentActivity.this);
                    try {
                        aliPayTask.aliPay();
                        Log.i("asd", "onClick: "+"调用");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                if (mCheck.equals("1")) {
                    Log.i("asd", "onClick: "+"微信");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        List<String> permissionList = new ArrayList<>();
                        if (ContextCompat.checkSelfPermission(SDKHelper.getmInstance().getContext(), Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.INTERNET);
                        }
                        if (ContextCompat.checkSelfPermission(SDKHelper.getmInstance().getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.READ_PHONE_STATE);
                        }
                        if (ContextCompat.checkSelfPermission(SDKHelper.getmInstance().getContext(), Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
                        }
                        if (ContextCompat.checkSelfPermission(SDKHelper.getmInstance().getContext(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
                        }
                        if (ContextCompat.checkSelfPermission(SDKHelper.getmInstance().getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }
                        if (!permissionList.isEmpty()) {
                            ActivityCompat.requestPermissions((Activity) SDKHelper.getmInstance().getContext(), permissionList.toArray(new String[permissionList.size()]), 1);
                        } else {
                            try {
                                getWXOrder();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        try {
                            getWXOrder();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(SDKHelper.getmInstance().getContext(), "某个权限被拒绝了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    try {
                        getWXOrder();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 获取微信支付订单
     */
    private void getWXOrder() throws Exception {

        PackageInfo pkg = getPackageManager().getPackageInfo(getApplication().getPackageName(), 0);
        String appName = pkg.applicationInfo.loadLabel(getPackageManager()).toString();
        String packageName = getPackageName();
        String deviceId = TRWSDK.getmDeviceId();

        String appId = String.valueOf(TRWSDK.getmAppId());
        String channel = String.valueOf(TRWParams.DEVICE_TYPE);
        GameOrderBean gameOrderBean = TRWSDK.getGameOrderBean();
        String loginAccount = gameOrderBean.getLogin_account();
        String server_Id = gameOrderBean.getServer_id();
        int user_Id = gameOrderBean.getUser_id();
        String role_Id = gameOrderBean.getRole_id();
        String product_Id = gameOrderBean.getProduct_id();
        String nick_name = gameOrderBean.getNick_name();
        String serverName = gameOrderBean.getServer_name();
        String productName = gameOrderBean.getProduct_name();
        String extData = gameOrderBean.getExt_data();
        String sign = gameOrderBean.getSign();
        String mAmount = gameOrderBean.getAmount();

        WXPayRequest request = new WXPayRequest();
        WXPayRequest.DataBean dataBean = new WXPayRequest.DataBean();

        request.setDid(deviceId);
        request.setAppid(appId);
        request.setChannel(channel);
        request.setPay_type("30");
        request.setPackage_name(packageName);
        request.setApp_name(appName);
        request.setScene_type("Android");
        request.setData(dataBean);

        dataBean.setExt_data(extData);
        dataBean.setAmount(mAmount);
        dataBean.setLogin_account(loginAccount);
        dataBean.setNick_name(nick_name);
        dataBean.setProduct_id(product_Id);
        dataBean.setProduct_name(productName);
        dataBean.setUser_id(String.valueOf(user_Id));
        dataBean.setRole_id(role_Id);
        dataBean.setServer_id(server_Id);
        dataBean.setServer_name(serverName);
        dataBean.setSign(sign);
        Gson gson = new Gson();
        String Request = gson.toJson(request);

        Log.i("asd", "WXPayRequest:" + Request);
        Log.i("asd", "packageName:" + packageName);
        Log.i("asd", "appName:" + appName);


        OkHttpUtils.ResultCallback mResultCallBack = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.i("asd", "OnSuccess" + response);
                Gson gson = new Gson();
                WXPayResponse wxPayResponse = gson.fromJson(response, WXPayResponse.class);
                switch (wxPayResponse.getResult()) {
                    case "200":
                        startHeepayServiceJar(wxPayResponse);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        List<OkHttpUtils.Param> params = new ArrayList<>();
        params.add(new OkHttpUtils.Param("", Request));
        OkHttpUtils.post(TaRenAPI.WX_PAY, mResultCallBack, params);

    }


    private void setData() throws Exception {
        GameOrderBean gameOrderBean = TRWSDK.getGameOrderBean();
        mIndentMoney.setText(gameOrderBean.getAmount());
        mAccount.setText(new String(java.net.URLDecoder.decode(gameOrderBean.getNick_name(), "UTF-8")));
        Log.i("account", "setData: "+gameOrderBean.getNick_name()+new String(java.net.URLDecoder.decode(gameOrderBean.getNick_name(), "UTF-8")));
        mGood.setText(new String(java.net.URLDecoder.decode(gameOrderBean.getProduct_name(), "UTF-8")));
    }

    private void initView() {
        mIndentMoney = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "indent_money"));
        mAccount = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "account"));
        mBack = (ImageView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "back_press"));
        mGood = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "good_info"));
        mRadioGroup = (RadioGroup) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "rg_choose"));
        mAlipay = (RadioButton) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "alipay"));
        mWechatpay = (RadioButton) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "wechatpay"));
        mTrcoinpay = (RadioButton) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "trcoinpay"));
        mPayNow = (Button) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "pay_now"));
    }


}
