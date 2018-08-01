package com.tarenwang.gamesdk_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tarenwang.gamesdk_demo.R;
import com.tarenwang.trwgamesdk.sdk.LoginCallBack;
import com.tarenwang.trwgamesdk.sdk.TRWGlobalVariable;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button mBtn;
    public static final int APP_ID = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trw_activity_main);
        mBtn = (Button) findViewById(R.id.testlogin);
        mBtn.setOnClickListener(this);
    }


    /**
     * 初始化
     * 1：传参
     * 2：展开登录Dialog
     */
    private void init() {
        TRWSDK.initialize(this, APP_ID);
        if (TRWGlobalVariable.getInstance().getInit()) {
        TRWSDK.doLogin(this, new LoginCallBack() {
            @Override
            public void onSuccessful(String nikeName, String s1, String s2) {
                String nickName = nikeName;
                String uId = s1;
                Log.i("asd", "onSuccessful: " + "nikeName" + nikeName + "+" + "uId" + s1 + "+" + "token" + s2);
                Intent intent = new Intent();
                intent.putExtra("uId", uId);
                intent.putExtra("nickName", nickName);
                intent.setClass(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });
    }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.testlogin:
                Log.i("asd", "onClick: " + "登录事件响应");
                init();
                break;
            default:
                break;
        }
    }

}