package com.tarenwang.trwgamesdk.pay.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarenwang.trwgamesdk.pay.request.GameOrderBean;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;
import com.tarenwang.trwgamesdk.utils.ResourceUtils;
import com.tarenwang.trwgamesdk.utils.SDKHelper;

/**
 * @author MinoZhang
 * @date 2016/11/29
 */

public class PayResultActivity extends Activity {
    private ImageView payResultImage,close;
    private TextView payResult,orderTitle,orderNumber,orderGood,orderAmount;
    private Button goBackGame,payAgain;
    private GestureDetector mGestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(),"trw_activity_pay_result"));
        initView();
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListener();
        setGestureDetector();
    }

    private void setGestureDetector() {
        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e2.getX()-e1.getX()>0&&(e1.getX()>=0&&e1.getX()<=3000)){
                    if
                            (Math.abs(e2.getX() - e1.getX()) > Math.abs(e2.getY() - e1.getY())
                            &&
                            Math.abs(velocityX) > 300){
                        overridePendingTransition(0,ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(),"activity_out_from_right"));
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

    private void setListener() {
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        goBackGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0,ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(),"activity_out_from_right"));
            }
        });
        payAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayResultActivity.this,ItemPaymentActivity.class);
                startActivity(intent);
                PayResultActivity.this.overridePendingTransition(ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(),"activity_in_from_left"),ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(),"activity_out_from_right"));
                finish();
            }
        });
    }

    private void initData() throws Exception {
        Intent intent = getIntent();
        int result = Integer.parseInt(intent.getStringExtra("result"));
        GameOrderBean gameOrderBean = TRWSDK.getGameOrderBean();
        switch (result){
            case 1 :
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_succeed"));
                payResult.setText("恭喜你，支付成功");
                orderTitle.setText("订单信息");
                orderNumber.setText("游戏订单号:"+gameOrderBean.getGame_order_id());
                orderGood.setText("商品："+new String(java.net.URLDecoder.decode(gameOrderBean.getProduct_name(),"UTF-8")));
                orderAmount.setText("支付金额：¥"+gameOrderBean.getAmount());
                break;
            case 2 :
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_warn"));
                payResult.setText("支付结果未知");
                orderTitle.setText("           订单正在处理中，请耐心等待\n" + "请查询充值记录订单列表中的订单支付状态");
                orderNumber.setVisibility(View.GONE);
                orderGood.setVisibility(View.GONE);
                orderAmount.setVisibility(View.GONE);
                break;
            case 3:
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_failed"));
                payResult.setText("支付失败");
                orderTitle.setText("充值系统暂不可用，请稍后再试");
                orderNumber.setVisibility(View.GONE);
                orderGood.setVisibility(View.GONE);
                orderAmount.setVisibility(View.GONE);
                break;
            case 4:
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_failed"));
                payResult.setText("支付失败");
                orderTitle.setText("不要重复请求，请稍后再试");
                orderNumber.setVisibility(View.GONE);
                orderGood.setVisibility(View.GONE);
                orderAmount.setVisibility(View.GONE);
                break;
            case 5:
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_failed"));
                payResult.setText("充值失败");
                orderTitle.setText("您已取消订单");
                orderNumber.setVisibility(View.GONE);
                orderGood.setVisibility(View.GONE);
                orderAmount.setVisibility(View.GONE);
                break;
            case 6:
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_failed"));
                payResult.setText("支付失败");
                orderTitle.setText("网络连接失败，请检查网络状况后再试");
                orderNumber.setVisibility(View.GONE);
                orderGood.setVisibility(View.GONE);
                orderAmount.setVisibility(View.GONE);
                break;
            case 7:
                payResultImage.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(),"trw_pay_failed"));
                payResult.setText("支付失败");
                orderTitle.setText("未知错误，请稍后再试");
                orderNumber.setVisibility(View.GONE);
                orderGood.setVisibility(View.GONE);
                orderAmount.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }

    private void initView() {
        close = (ImageView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"back_press"));
        payResultImage = (ImageView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"iv_pay_result"));
        payResult = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"tv_pay_result"));
        orderTitle = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"order_title"));
        orderNumber = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"order_number"));
        orderGood = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"pay_good"));
        orderAmount = (TextView) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"pay_amount"));
        goBackGame = (Button) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"btn_goBackGame"));
        payAgain = (Button) findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(),"btn_payAgain"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(PayResultActivity.this,ItemPaymentActivity.class);
        startActivity(intent);
        PayResultActivity.this.overridePendingTransition(ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(),"activity_in_from_left"),ResourceUtils.getAnimId(SDKHelper.getmInstance().getContext(),"activity_out_from_right"));
        finish();
    }


}
