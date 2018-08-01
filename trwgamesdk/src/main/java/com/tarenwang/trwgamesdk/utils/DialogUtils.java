package com.tarenwang.trwgamesdk.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tarenwang.gamesdk.secret.Keytool;
import com.tarenwang.trwgamesdk.account.request.FastLoginRequest;
import com.tarenwang.trwgamesdk.account.request.LoginRequest;
import com.tarenwang.trwgamesdk.account.request.RegisterRequest;
import com.tarenwang.trwgamesdk.account.response.FastLoginRespnse;
import com.tarenwang.trwgamesdk.account.response.LoginResponse;
import com.tarenwang.trwgamesdk.account.response.RegisterResponse;
import com.tarenwang.trwgamesdk.account.ui.WebForgetPassword;
import com.tarenwang.trwgamesdk.sdk.TRWGlobalVariable;
import com.tarenwang.trwgamesdk.sdk.TRWParams;
import com.tarenwang.trwgamesdk.sdk.TRWSDK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyan on 2016/10/31.
 */

public class DialogUtils {

    private DBHelper dbHelper = new DBHelper(SDKHelper.getmInstance().getContext());
    private PopupWindow popupWindow;
    private EditText mPhone, mPsw;
    private Dialog dialog;

    private ProgressDialog proDialog;

    private static DialogUtils sInstance;

    private Button mLogin;
    private TextView mFastRegister;

    private DialogUtils() {

    }

    public static DialogUtils getInstance() {
        if (sInstance == null) {
            sInstance = new DialogUtils();
        }
        return sInstance;
    }

    public void showProgress() {
        proDialog = ProgressDialog.show(SDKHelper.getmInstance().getContext(), "", "玩命登录中...");
    }

    public void hideProgress() {
        if (proDialog != null && proDialog.isShowing()) {
            proDialog.dismiss();
        }
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 二次自动登录
     */
    public void showAutomaticLogin(final Context context, final String[] userIds) {
        final Handler handler = new Handler();
        final Dialog dialog = new Dialog(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "trw_dialog"));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        View view;
        view = LayoutInflater.from(context).inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_autologin_view"), null);
        final TextView account = (TextView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "account"));
        Button switchAccount = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "switch_account"));
        final String tempUid = userIds[userIds.length - 1];
        final String tempName = dbHelper.queryUserNameByUserId(tempUid);
        account.setText(tempName);
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String userId = tempUid;
                String userName = tempName;
                String userToken = dbHelper.queryTokenByuserId(tempUid);
                TRWGlobalVariable.getInstance().setUid(tempUid);
                TRWGlobalVariable.getInstance().setLogout_flag(false);
                TRWSDK.LoginComplete(userName, userId, userToken);
                dialog.dismiss();
            }
        };

        switchAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                dialog.dismiss();
                showCommonDialog(context);
            }
        });
        handler.postDelayed(runnable, 3000);
        dialog.setContentView(view);
        dialog.show();
    }

//
//    private void initWeixin() {
//        api = WXAPIFactory.createWXAPI(SDKHelper.getmInstance().getContext(), TRWSDK.getmWxAppID(), true);
//        api.registerApp(TRWSDK.getmWxAppID());
//    }

    public View showCommonDialog(final Context context) {
        //初始化微信组件
        dbHelper = new DBHelper(context);
        final Dialog dialog = new Dialog(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "trw_dialog"));
        View view = LayoutInflater.from(context).inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_activity_login_message_dialog"), null);
        final ImageView mDropDown = (ImageView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "open_iv"));
        TextView mRegister = (TextView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_register"));
        TextView mForgetPsw = (TextView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_forget_psw"));
        mLogin = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_login"));
        mFastRegister = (TextView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_fast_register"));
        mPhone = (EditText) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_et_phone"));
        mPsw = (EditText) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "login_et_psw"));
        initLoginUserInfo(mPhone, mPsw);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
        /**
         * 微信登录
         */
//        mWechatLogin.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                if (!api.isWXAppInstalled()) {
//                    Toast.makeText(SDKHelper.getmInstance().getContext(), "您还未安装微信客户端！", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                String state = "com.tarenwang.gamesdk_demo";
//                final SendAuth.Req req = new SendAuth.Req();
//                req.scope = "snsapi_userinfo";
//                req.state = state;
//                api.sendReq(req);
//            }
//        });

        /**
         * 忘记密码点击事件
         */
        mForgetPsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, WebForgetPassword.class);
                context.startActivity(intent);
                dialog.dismiss();
            }
        });

        /**
         * 登录点击事件
         */
        mLogin.setOnClickListener(new View.OnClickListener() {
            Toast toast = null;
            private long lastClick;

            @Override
            public void onClick(View view) {

                long currentClick = System.currentTimeMillis();
                if (currentClick - lastClick < 2000) {
                    lastClick = currentClick;
                    return;
                }
                lastClick = currentClick;

                String[] userIds = dbHelper.queryAllUserId();
                if (userIds.length == 0 || !mPsw.getText().toString().equals(TRWParams.FAKE_CODE)) {
                    String phone = mPhone.getText().toString();
                    String psw = mPsw.getText().toString();
                    /**
                     *判断登陆界面账号和密码是否填写
                     */
//                    if (TextUtils.isEmpty(phone))
                    if (phone.equals("")) {
                        if (toast == null) {
                            Toast.makeText(context, "请输入账号", Toast.LENGTH_SHORT).show();
                        } else {
                            toast.setText("请输入账号");
                        }
                        return;
                    } else if (phone != null && psw.equals("")) {
                        if (toast == null) {
                            Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                        } else {
                            toast.setText("请输入密码");
                        }
                        return;
                    } else {
                        try {
                            userLogin(context, dialog, phone, psw);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                } else {
                    final String tempName = mPhone.getText().toString();
                    final String tempUserId = dbHelper.queryUserIdByUserName(tempName);
                    final String tempToken = dbHelper.queryTokenByuserId(tempUserId);
                    TRWSDK.LoginComplete(tempName, tempUserId,tempToken);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());
                    String loginTime = formatter.format(curDate);
                    dbHelper.delete(tempUserId);
                    dbHelper.insertOrUpdate(tempName, tempUserId, tempToken, loginTime);
                    dialog.dismiss();

                }
            }
        });
        /**
         * 注册点击事件
         */
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                dialog.dismiss();
                showRegisterDialog(context);
            }
        });

        /**
         * 快速注册点击事件
         */
        mFastRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mFastRegister.setEnabled(false);
                String service = "user.guestLogin";
                //创建Bean对象，FastLoginRequest
                FastLoginRequest fastLoginRequest = new FastLoginRequest();
                FastLoginRequest.GameBean gameBean = new FastLoginRequest.GameBean();
                gameBean.setDevicetype(TRWParams.DEVICE_TYPE);
                gameBean.setAppid(TRWSDK.getmAppId());
                fastLoginRequest.setService(service);
                fastLoginRequest.setDeviceid(TRWSDK.getmDeviceId());
                fastLoginRequest.setGame(gameBean);
                //对userGuestpostBean对象进行Json解析
                Gson gson = new Gson();
                String userguestpost = gson.toJson(fastLoginRequest);
                Log.i("asd", "onSuccess: " + userguestpost);
                OkHttpUtils.ResultCallback mResultCallback = new OkHttpUtils.ResultCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        //对响应数据进行数据解析
                        Gson gson = new Gson();
                        FastLoginRespnse fastLoginRespnse = gson.fromJson(response, FastLoginRespnse.class);
                        Log.i("asd", "onSuccess: " + response);
                        switch (fastLoginRespnse.getCode()) {
                            case "0000":
                                String userName = fastLoginRespnse.getData().getUsername();
                                String userToken = fastLoginRespnse.getToken();
                                String userId = String.valueOf(fastLoginRespnse.getData().getUid());
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                                Date curDate = new Date(System.currentTimeMillis());
                                String loginTime = formatter.format(curDate);
                                showBindingDialog(context, userName, userId, userToken);
                                dbHelper.delete(userId);
                                dbHelper.insertOrUpdate(userName, userId, userToken, loginTime);
                                dialog.dismiss();
                                break;
                            default:
                                mFastRegister.setEnabled(true);
                                break;
                        }
                    }

                    private void showBindingDialog(final Context context, final String UserName, final String UserID, final String UserToken) {
                        final Dialog bindingPhoneDialog = new Dialog(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "trw_dialog"));
                        bindingPhoneDialog.setCanceledOnTouchOutside(false);
                        bindingPhoneDialog.setCancelable(false);
                        View view;
                        view = LayoutInflater.from(context).inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_activity_binding_phone_dialog"), null);
                        Button later_binding = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "btn_later_binding"));
                        Button immediately_binding = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "btn_immediately_binding"));
                        //设置button的点击事件，“以后绑定”回到MainActivity，“立即绑定”跳转到绑定手机号页面
                        later_binding.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bindingPhoneDialog.dismiss();
                                //登录成功
//                                TipsToast.makeText(context, ResourceUtils.getMip(SDKHelper.getmInstance().getContext(), "trw_ok"), "登陆成功", "欢迎您！", userName, Toast.LENGTH_SHORT).show();
                                TRWGlobalVariable.getInstance().setUid(UserID);
                                TRWGlobalVariable.getInstance().setLogout_flag(false);
                                TRWSDK.LoginComplete(UserName, UserID, UserToken);
                            }
                        });
                        immediately_binding.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bindingPhoneDialog.dismiss();
                                showRegisterDialog(context);
                            }
                        });
                        //显示bindingDialog
                        bindingPhoneDialog.setContentView(view);
                        bindingPhoneDialog.show();

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(context, "当前网络状况不佳，请检查网络连接", Toast.LENGTH_SHORT).show();
                    }
                };
                List<OkHttpUtils.Param> params = new ArrayList<>();
                params.add(new OkHttpUtils.Param("", userguestpost));
                OkHttpUtils.post(TaRenAPI.BASE_URL, mResultCallback, params);
            }
        });
        mDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    if (!popupWindow.isShowing()) {
                        popupWindow.showAsDropDown(mPhone);
                        mDropDown.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_close_pop"));
                    } else {
                        popupWindow.dismiss();
                    }
                    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            mDropDown.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_open"));
                        }
                    });
                } else {
                    // 如果有已经登录过账号
                    if (dbHelper.queryAllUserName().length > 0) {
                        initPopView(dbHelper.queryAllUserName());
                        if (!popupWindow.isShowing()) {
                            popupWindow.showAsDropDown(mPhone);
                        } else {
                            popupWindow.dismiss();
                        }
                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                mDropDown.setImageResource(ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_open"));
                            }
                        });
                    } else {
                        Toast.makeText(context, "无账号记录", Toast.LENGTH_LONG).show();
                    }

                }

            }
        });
        return view;
    }


    /**
     * 注册
     *
     * @param context
     * @return
     */
    public View showRegisterDialog(final Context context) {
        dbHelper = new DBHelper(context);
        final Dialog dialog = new Dialog(context, ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "trw_dialog"));
        View view = LayoutInflater.from(context).inflate(ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_dialog_register"), null);
        mPhone = (EditText) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "register_et_phone"));
        final EditText mPassword = (EditText) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "register_et_password"));
        Button mNext = (Button) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "regitster_btn_next"));
        TextView mHave = (TextView) view.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "have_number"));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
        mHave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showCommonDialog(context);
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = mPhone.getText().toString();
                String password = mPassword.getText().toString();
                if (mobile.equals("") || password.equals("")) {
                    Toast.makeText(context, "请填写完整信息", Toast.LENGTH_SHORT).show();
                } else if (!Validator.isUserNumber(mobile)) {
                    Toast.makeText(context, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(context, "密码长度不能小于6位", Toast.LENGTH_SHORT).show();
                } else {
                    register(dialog, mobile, password);
                }
            }
        });
        return view;
    }

    private void register(final Dialog dialog, final String phone, String psw) {
        String service = "user.directReg";
        int appid = TRWSDK.getmAppId();
        int Devicetype = TRWParams.DEVICE_TYPE;
        //创建bean对象进行gson解析
        RegisterRequest registerRequest = new RegisterRequest();
        final RegisterRequest.GameBean registerGameBean = new RegisterRequest.GameBean();
        registerGameBean.setAppid(appid);
        registerGameBean.setDevicetype(Devicetype);
        registerRequest.setUsername(phone);
        registerRequest.setPassword(Keytool.encode(SDKHelper.getmInstance().getContext(), psw));
        registerRequest.setDeviceid(TRWSDK.getmDeviceId());
        registerRequest.setService(service);
        registerRequest.setGame(registerGameBean);
        //对userpostBean对象进行Gson解析
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String userpost = gson.toJson(registerRequest);
        Log.i("asd", "post: "+userpost);
        //进行post请求
        OkHttpUtils.ResultCallback mResultCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson1 = new Gson();
                RegisterResponse registerResponse = gson1.fromJson(response, RegisterResponse.class);
                Log.i("asd", "onSuccess: "+response);
                switch (registerResponse.getCode()) {
                    case "0000":
                        String userName = phone;
                        String nickName = registerResponse.getData().getNickname();
                        String userId = String.valueOf(registerResponse.getData().getUid());
                        String userToken = registerResponse.getToken();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                        Date curDate = new Date(System.currentTimeMillis());
                        String loginTime = formatter.format(curDate);
                        dbHelper.insertOrUpdate(userName, userId, userToken, loginTime);
                        TRWGlobalVariable.getInstance().setUid(userId);
                        TRWGlobalVariable.getInstance().setLogout_flag(false);
                        TRWSDK.LoginComplete(nickName, userId, userToken);
//                        TipsToast.makeText(SDKHelper.getmInstance().getContext(), ResourceUtils.getMip(SDKHelper.getmInstance().getContext(), "trw_ok"), "注册&登陆成功", "欢迎您！", userName, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    case "1008":
                        TipsToast.makeText(SDKHelper.getmInstance().getContext(), ResourceUtils.getMip(SDKHelper.getmInstance().getContext(), "trw_error"), "用户已存在,\n请重新注册", "", "", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(SDKHelper.getmInstance().getContext(), "当前网络状况不佳，请检查网络连接", Toast.LENGTH_SHORT).show();

            }
        };
        List<OkHttpUtils.Param> params = new ArrayList<>();
        params.add(new OkHttpUtils.Param("", userpost));
        OkHttpUtils.post(TaRenAPI.BASE_URL, mResultCallback, params);
    }

    private void initPopView(String[] usernames) {
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < usernames.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", usernames[i]);
            map.put("drawable", ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_close"));
            list.add(map);
        }
        MyAdapter dropDownAdapter = new MyAdapter(SDKHelper.getmInstance().getContext(), list, ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_dropdown_item"),
                new String[]{"name", "drawable"}, new int[]{ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "textview"),
                ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "delete")});
        ListView listView = new ListView(SDKHelper.getmInstance().getContext());
        listView.setAdapter(dropDownAdapter);
        popupWindow = new PopupWindow(listView, mPhone.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(ResourceUtils.getStyleId(SDKHelper.getmInstance().getContext(), "Animation_ZoomLight"));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(SDKHelper.getmInstance().getContext().getResources(), (Bitmap) null));


    }

    /**
     * 用户登陆,做个人信息判断
     */
    private void userLogin(final Context context, final Dialog dialog, final String phone, String mPsw) throws Exception {
        String service = "user.directLogin";
        //创建Bean对象，将参数通过set方法赋值
        LoginRequest loginRequest = new LoginRequest();
        LoginRequest.GameBean gameBean = new LoginRequest.GameBean();
        gameBean.setAppid(TRWSDK.getmAppId());
        gameBean.setDevicetype(TRWParams.DEVICE_TYPE);
        loginRequest.setUsername(phone);
        loginRequest.setDeviceid(TRWSDK.getmDeviceId());
        loginRequest.setPassword(Keytool.encode(SDKHelper.getmInstance().getContext(), mPsw));
        loginRequest.setService(service);
        loginRequest.setGame(gameBean);
        //对userpostBean对象进行gson解析
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String userpost = gson.toJson(loginRequest);
        Log.i("asd", "loginPost: "+userpost);
        //进行post请求
        OkHttpUtils.ResultCallback mResultcallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.i("asd", "loginOnSuccess: "+response);
                //对响应数据进行数据解析
                hideProgress();
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                switch (loginResponse.getCode()) {
                    case "0000":
                        String userName = phone;
                        String nickName = loginResponse.getData().getNickname();
                        Log.i("asd", "onSuccess: "+nickName);
                        String userId = String.valueOf(loginResponse.getData().getUid());
                        String userToken = loginResponse.getToken();
                        String[] userIds = dbHelper.queryAllUserId();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
                        Date curDate = new Date(System.currentTimeMillis());
                        String loginTime = formatter.format(curDate);
                        here:
                        for (int i = 0; i < userIds.length; i++) {
                            if (userId.equals(userIds[i])) {
                                dbHelper.delete(userId);
                                dbHelper.insertOrUpdate(userName, userId, userToken, loginTime);
                                break here;
                            }
                        }
                        dbHelper.insertOrUpdate(userName, userId, userToken, loginTime);
                        TRWGlobalVariable.getInstance().setUid(userId);
                        TRWGlobalVariable.getInstance().setLogout_flag(false);
//                        TipsToast.makeText(context, ResourceUtils.getMip(SDKHelper.getmInstance().getContext(), "trw_ok"), "登陆成功", "欢迎您！", userName, Toast.LENGTH_SHORT).show();
                        //登陆成功进入到主页面
                        TRWSDK.LoginComplete(nickName, userId, userToken);
                        dialog.dismiss();
//                        finish();
                        break;
                    case "1010":
                        TipsToast.makeText(context, ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_error"), "登陆失败", "账号或密码不正确", "", Toast.LENGTH_SHORT).show();
                        break;
                    case "1013":
                        TipsToast.makeText(context, ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_error"), "登陆失败", "账号或密码不正确", "", Toast.LENGTH_SHORT).show();
                        break;
                    case "1014":
                        TipsToast.makeText(context, ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_error"), "登陆失败", "账号未注册", "", Toast.LENGTH_SHORT).show();
                        break;

                    case "9999":
                        TipsToast.makeText(context, ResourceUtils.getDrawableId(SDKHelper.getmInstance().getContext(), "trw_error"), "登录失败", "", "", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "当前网络状况不佳，请检查网络连接", Toast.LENGTH_SHORT).show();
            }
        };
        List<OkHttpUtils.Param> params = new ArrayList<>();
        params.add(new OkHttpUtils.Param("", userpost));
        OkHttpUtils.post(TaRenAPI.BASE_URL, mResultcallback, params);
        showProgress();
    }

    private void initLoginUserInfo(EditText mPhone, final EditText mPsw) {
        String[] usernames = dbHelper.queryAllUserName();
        if (TRWGlobalVariable.getInstance().getUid() == null) {
            if (usernames.length > 0) {
                String tempName = usernames[usernames.length - 1];
                mPhone.setText(tempName);
                mPhone.setSelection(tempName.length());
                mPsw.setText(TRWParams.FAKE_CODE);
            } else {
                mPhone.setText("");
                mPsw.setText("");
            }
        } else {
            String tempName = usernames[usernames.length - 1];
            String tempUid = dbHelper.queryUserIdByUserName(tempName);
            mPhone.setText(tempName);
            mPhone.setSelection(tempName.length());
            mPsw.setText("");
            dbHelper.delete(tempUid);
        }
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                mPsw.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 账号下拉框(popupWindow)适配器
     */
    class MyAdapter extends SimpleAdapter {

        private List<HashMap<String, Object>> data;

        public MyAdapter(Context context, List<HashMap<String, Object>> data,
                         int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(SDKHelper.getmInstance().getContext()).inflate(
                        ResourceUtils.getLayoutId(SDKHelper.getmInstance().getContext(), "trw_dropdown_item"), null);
                holder.btn = (ImageView) convertView
                        .findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "delete"));
                holder.tv = (TextView) convertView.findViewById(ResourceUtils.getId(SDKHelper.getmInstance().getContext(), "textview"));
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(data.get(position).get("name").toString());
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] usernames = dbHelper.queryAllUserName();
                    mPhone.setText(usernames[position]);
                    mPsw.setText(TRWParams.FAKE_CODE);
                    popupWindow.dismiss();
                }
            });
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPhone.setText("");
                    mPsw.setText("");
                    String[] usernames = dbHelper.queryAllUserName();
                    if (usernames.length > 0) {
                        dbHelper.delete(dbHelper.queryUserIdByUserName(usernames[position]));
                        popupWindow.dismiss();
                        String[] newusernames = dbHelper.queryAllUserName();
                        if (newusernames.length > 0) {
                            initPopView(newusernames);
                            popupWindow.showAsDropDown(mPhone);
                        } else {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                    }
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        private TextView tv;
        private ImageView btn;
    }
}
