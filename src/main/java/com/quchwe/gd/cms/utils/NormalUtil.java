package com.quchwe.gd.cms.utils;

import com.quchwe.gd.cms.bean.BaseRequest;
import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * 判断是否为手机号
     *
     * @param tel 输入字符串
     * @return boolean
     */
    public static boolean isMobileNO(String tel) {
        Pattern p = Pattern.compile("^(13[0-9]|15([0-3]|[5-9])|14[5,7,9]|17[1,3,5,6,7,8]|18[0-9])\\d{8}$");
        Matcher m = p.matcher(tel);
        //System.out.println(m.matches() + "---");
        return m.matches();
    }

    /**
     * 判断是否为车牌号
     * @param id
     * @return
     */
    public static boolean isDrivingId(String id){
        Pattern p = Pattern.compile("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$");
        Matcher m = p.matcher(id);

        return m.matches();
    }
}
