package com.tarenwang.trwgamesdk.pay.untils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tarenwang.trwgamesdk.pay.activity.PayResultActivity;
import com.tarenwang.trwgamesdk.pay.request.AlipayRequest;
import com.tarenwang.trwgamesdk.pay.request.AlipayResponse;
import com.tarenwang.trwgamesdk.pay.request.GameOrderBean;
import com.tarenwang.trwgamesdk.sdk.TRWParams;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;
import com.tarenwang.trwgamesdk.utils.OkHttpUtils;
import com.tarenwang.trwgamesdk.utils.TaRenAPI;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MinoZhang
 * @date 2016/11/29
 */

public class AliPayTask {
    private Context mContext;
    private String payInfo;
    private Activity mActivity;
    private GameOrderBean gameOrderBean;

    public AliPayTask(Context context, Activity activity){
        this.mContext = context;
        this.mActivity = activity;
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((String) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
//                  String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            Log.i("asd", "handleMessage: " + resultStatus);
            // 判断resultStatus 为"9000"则代表支付成功,具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Intent intent = new Intent(mContext,PayResultActivity.class);
                intent.putExtra("result","1");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                mActivity.finish();
                TRWSDK.PayComplete(true);
            } else {
                // 判断resultStatus 为非“9000”则代表可能支付失败
                // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")||TextUtils.equals(resultStatus,"6004")) {
                    Intent intent = new Intent(mContext,PayResultActivity.class);
                    intent.putExtra("result","2");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    mActivity.finish();
                    TRWSDK.PayComplete(false);

                }else if(TextUtils.equals(resultStatus,"4000")){
                    Intent intent = new Intent(mContext,PayResultActivity.class);
                    intent.putExtra("result","3");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    mActivity.finish();
                    TRWSDK.PayComplete(false);
                }else if(TextUtils.equals(resultStatus,"5000")){
                    Intent intent = new Intent(mContext,PayResultActivity.class);
                    intent.putExtra("result","4");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    mActivity.finish();
                    TRWSDK.PayComplete(false);
                }else if(TextUtils.equals(resultStatus,"6001")){
                    Intent intent = new Intent(mContext,PayResultActivity.class);
                    intent.putExtra("result","5");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    mActivity.finish();
                    TRWSDK.PayComplete(false);
                }else if(TextUtils.equals(resultStatus,"6002")){
                    Intent intent = new Intent(mContext,PayResultActivity.class);
                    intent.putExtra("result","6");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    mActivity.finish();
                    TRWSDK.PayComplete(false);
                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    Intent intent = new Intent(mContext,PayResultActivity.class);
                    intent.putExtra("result","7");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    mActivity.finish();
                    TRWSDK.PayComplete(false);

                }
            }

        }

    };
        public void aliPay() throws UnsupportedEncodingException {
            //将游戏端传入的json字符串转化为bean对象
            gameOrderBean =TRWSDK.getGameOrderBean();
            //通过bean对象取值
            String serverId = gameOrderBean.getServer_id();
            int userId = gameOrderBean.getUser_id();
            String roleId = gameOrderBean.getRole_id();
            String productId = gameOrderBean.getProduct_id();
            String loginAccount = gameOrderBean.getLogin_account();
            String nickName = gameOrderBean.getNick_name();
            String serverName = gameOrderBean.getServer_name();
            String productName = gameOrderBean.getProduct_name();
            String deviceId = TRWSDK.getmDeviceId();
            String appId = String.valueOf(TRWSDK.getmAppId());
            String channelId = String.valueOf(TRWParams.DEVICE_TYPE);
            String mAmount = gameOrderBean.getAmount();
            String extData = gameOrderBean.getExt_data();
            String sign = gameOrderBean.getSign();
            //将请求参数拼接转化为json字符串进行请求
            AlipayRequest alipayRequest = new AlipayRequest();
            AlipayRequest.DataBean dataBean = new AlipayRequest.DataBean();
            dataBean.setAmount(mAmount);
            dataBean.setExt_data(extData);
            dataBean.setLogin_account(loginAccount);
            dataBean.setNick_name(nickName.toLowerCase());
            dataBean.setProduct_id(productId);
            dataBean.setProduct_name(productName.toLowerCase());
            dataBean.setUser_id(userId);
            dataBean.setRole_id(roleId);
            dataBean.setServer_id(serverId);
            dataBean.setServer_name(serverName.toLowerCase());
            dataBean.setSign(sign);
            alipayRequest.setDid(deviceId);
            alipayRequest.setAppid(appId);
            alipayRequest.setChannel(channelId);
            alipayRequest.setData(dataBean);
            Gson mGson = new Gson();
            String aliPayRequest = mGson.toJson(alipayRequest);
            Log.i("asd", "aliPay: "+aliPayRequest);
            OkHttpUtils.ResultCallback mResultCallback = new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    Log.i("alipayresponse", "onSuccess: "+response);
                    Gson mGson = new Gson();
                    AlipayResponse aliPayResponse = mGson.fromJson(response, AlipayResponse.class);
                    switch (aliPayResponse.getResult()) {
                        case "200":
                            payInfo = aliPayResponse.getData().getOrder_info().toString();
                            Runnable payRunnable = new Runnable() {
                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(mActivity);
                                    String result = alipay.pay(payInfo, true);
                                    Message msg = new Message();
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                            break;
                        default:
                            break;
                    }

                }

                @Override
                public void onFailure(Exception e) {
                    Toast.makeText(mContext, "当前网络状态不好，请检查后再试", Toast.LENGTH_SHORT).show();
                }
            };
            List<OkHttpUtils.Param> params = new ArrayList<>();
            params.add(new OkHttpUtils.Param("", aliPayRequest));
            OkHttpUtils.post(TaRenAPI.ALIPAY_URL, mResultCallback, params);
        }

    }
