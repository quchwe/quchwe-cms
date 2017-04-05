package com.quchwe.gd.cms.bean;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
public class BaseResponseResult<T> {
    private String errCode;
    private String errMsg;
    private T resultInfo;


    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo) {
        this.resultInfo = resultInfo;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
