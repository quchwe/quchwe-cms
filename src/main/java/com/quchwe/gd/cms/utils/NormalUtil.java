package com.quchwe.gd.cms.utils;

import com.quchwe.gd.cms.bean.BaseRequest;
import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;

/**
 * Created by quchwe on 2017/4/5 0005.
 */
public class NormalUtil {

    public static <T>  boolean  authentication(BaseRequest<T> request, String md5){
        String authenticationStr = request.getUserToken()+request.getTimeStamp()+request.getRequestBean().toString();

        return EncryptionUtil.md5Encryption(authenticationStr).equals(md5);
    }

    public static <T> BaseResponseResult  authenticationError(BaseRequest<T> request){
        BaseResponseResult responseResult = new BaseResponseResult();
        if (request.getRequestBean() == null || !authentication(request, request.getMd5())) {
            responseResult.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_PARA_ERROR.getErrMsg());

        }
        return responseResult;
    }
}
