package com.tarenwang.gamesdk_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tarenwang.gamesdk_demo.R;
import com.tarenwang.trwgamesdk.floatmenu.manager.FloatMenuManager;
import com.tarenwang.trwgamesdk.pay.request.GameOrderBean;
import com.tarenwang.trwgamesdk.sdk.ExitCallBack;
import com.tarenwang.trwgamesdk.sdk.FMLogoutCallBack;
import com.tarenwang.trwgamesdk.sdk.LogoutCallBack;
import com.tarenwang.trwgamesdk.sdk.PayCallBack;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        Button mPay = (Button) findViewById(R.id.testPay);
        mPay.setOnClickListener(this);
        Button mExit = (Button) findViewById(R.id.exit);
        mExit.setOnClickListener(this);
        Button mLogout = (Button) findViewById(R.id.logout);
        mLogout.setOnClickListener(this);
        Button mStart = (Button) findViewById(R.id.startFloatMenu);
        mStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testPay:
                getOrder();
                break;
            case R.id.exit:
                exit();
                break;
            case R.id.logout:
                logout();
                break;
            case R.id.startFloatMenu:
                startFloatMenu();
                break;
            default:
        }
    }

    private void startFloatMenu() {
        TRWSDK.startFloatMenu(GameActivity.this, new FMLogoutCallBack() {
            @Override
            public void onSuccess(boolean success) {
                TRWSDK.doLogout(GameActivity.this, new LogoutCallBack() {
                    @Override
                    public void isLogout(boolean isLogout) {
                        if (isLogout) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            GameActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(GameActivity.this, "您已取消切换账号", Toast.LENGTH_SHORT).show();
                            FloatMenuManager.getInstance().startFloatView(GameActivity.this);
                        }
                    }
                });
            }


        });
    }

    private void logout() {
        TRWSDK.doLogout(GameActivity.this, new LogoutCallBack() {
            @Override
            public void isLogout(boolean isLogout) {
                if (isLogout) {
                    Intent intent = new Intent(GameActivity.this, MainActivity.class);
                    GameActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(GameActivity.this, "您已取消切换账号", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    private void exit() {
        TRWSDK.doExit(GameActivity.this, new ExitCallBack() {
            @Override
            public void onSuccess(boolean success) {
                //确认退出程序
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
    }

    public void getOrder() {
        //将所需参数拼接成json字符串
        String serverId = "411";
        Intent intent = getIntent();
        int userId = Integer.parseInt(intent.getStringExtra("uId"));
        Log.i("asd", "userId: " + userId);
        String gameOrderId = "101231212";
        String roleId = "10034562180";
        String productId = "300001";
        String loginAccount = String.valueOf(userId);
        String nickName = null;
        String serverName = null;
        String productName = null;
        try {
            nickName = java.net.URLEncoder.encode(intent.getStringExtra("nickName"), "UTF-8");
            serverName = java.net.URLEncoder.encode("电信一区", "UTF-8");
            productName = java.net.URLEncoder.encode("至尊礼包", "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String amount = "0.01";
        String appId = String.valueOf(TRWSDK.getmAppId());
        String channelId = String.valueOf(com.tarenwang.gamesdk_demo.activity.TRWParams.CHANNEL_ID);
        String payKey = "qdxno7zeywzwdfi501ttwh9t926wq6ja";
        String extData = appId + "_" + channelId + "_" + serverId + "_" + userId + "_"
                + roleId + "_" + productId + "_" + gameOrderId;



        String sSign = "amount=" + amount + "&ext_data=" + extData + "&login_account=" +
                "" + loginAccount + "&nick_name=" + nickName + "&paykey=" + payKey + "&product_id="
                + productId + "&product_name=" + productName + "&role_id=" + roleId + "&server_id="
                + serverId + "&server_name=" + serverName + "&user_id=" + userId;
        String sign = (com.tarenwang.gamesdk_demo.activity.MD5Util.getStringMD5(sSign.toLowerCase()));


        GameOrderBean gameOrderBean = new GameOrderBean();
        gameOrderBean.setAmount(amount);
        gameOrderBean.setExt_data(extData);
        gameOrderBean.setLogin_account(loginAccount);
        gameOrderBean.setNick_name(nickName);
        gameOrderBean.setProduct_id(productId);
        gameOrderBean.setProduct_name(productName);
        gameOrderBean.setUser_id(userId);
        gameOrderBean.setRole_id(roleId);
        gameOrderBean.setServer_id(serverId);
        gameOrderBean.setServer_name(serverName);
        gameOrderBean.setSign(sign);
        gameOrderBean.setGame_order_id(gameOrderId);





        TRWSDK.doPay(GameActivity.this, gameOrderBean, new PayCallBack() {
            @Override
            public void onSuccessful(boolean success) {
                Toast.makeText(GameActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(boolean failure) {
                Toast.makeText(GameActivity.this, "支付存在问题，请查询订单", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
