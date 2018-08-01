package com.tarenwang.trwgamesdk.sdk;

/**
 * author MinoZhang
 * date 2017/2/15
 */

public class TRWGlobalVariable {

    private static TRWGlobalVariable mGlobalVariable;
    private String uid = null;
    private Boolean logout_flag = false ;
    private Boolean isInit = false;

    private TRWGlobalVariable(){

    }
    public static TRWGlobalVariable getInstance(){
        if(mGlobalVariable ==null){
            mGlobalVariable = new TRWGlobalVariable();
        }
        return mGlobalVariable;
    }
    public Boolean getInit() {
        return isInit;
    }

    public void setInit(Boolean init) {
        isInit = init;
    }

    public Boolean getLogout_flag() {
        return logout_flag;
    }

    public void setLogout_flag(Boolean logout_flag) {
        this.logout_flag = logout_flag;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    }

