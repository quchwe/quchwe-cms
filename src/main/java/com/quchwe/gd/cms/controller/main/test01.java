package com.quchwe.gd.cms.controller.main;

import com.quchwe.gd.cms.bean.BaseResponse;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
public class test01 {
    public static final String o = "1";

    public static void main(String[] args) {
        System.out.println();
        System.out.println("test01.main");
        System.out.println("args = [" + args + "]");
        System.out.println(BaseResponse.APP_KEY_MISSING.getErrMsg());
        System.out.println(BaseResponse.APP_KEY_MISSING.getErrCode());
    }
}
