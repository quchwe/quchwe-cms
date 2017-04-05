package com.quchwe.gd.cms.bean;

/**
 * Created by quchwe on 2017/4/5 0005.
 */

public class BaseRequest<T> {
    String userToken;
    String md5;
    long timeStamp;
    T requestBean;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public T getRequestBean() {
        return requestBean;
    }

    public void setRequestBean(T requestBean) {
        this.requestBean = requestBean;
    }
}
