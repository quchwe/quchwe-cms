package com.quchwe.gd.cms.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BaseResponse {



    SYSTEM_ERROR("-1", "SYSTEM_ERROR"),
    INQUIRY_SUCCESS("0", " SUCCESS"), //鉴权成功
    INQUIRY_PARA_ERROR("1", "INQUIRY PARA ERROR"), //请求参数不对
    INQUIRY_ERROR("2","INQUIRY_ERROR"),
    INVALID_URL("40001", "INVALID_URL"),
    INVALID_PARAM("40003", "INVALID_PARAM"),
    INVALID_APP_KEY("40004", "INVALID_APP_KEY"),
    INVALID_SIGNATURE("40005", "INVALID_SIGNATURE"),
    INVALID_TIMESTAMP("40006", "INVALID_TIMESTAMP"),
    INVALID_NONCE_STR("40007", "INVALID_NONCE_STR"),
    INVALID_UID("40008", "INVALID_UID"),
    INVALID_USER_TOKEN("40009", "INVALID_USER_TOKEN"),
    APP_KEY_MISSING("41000", "APP_KEY_MISSING"),
    SIGNATURE_MISSING("41001", "SIGNATURE_MISSING"),
    TIMESTAMP_MISSING("41002", "TIMESTAMP_MISSING"),
    NONCE_STR_MISSING("41003", "NONCE_STR_MISSING"),
    UID_MISSING("41004", "UID_MISSING"),
    USER_TOKEN_MISSING("41005", "USER_TOKEN_MISSING"),
    SIGNATURE_EXPIRED("42001", "SIGNATURE_EXPIRED"),
    USER_TOKEN_EXPIRED("42002", "USER_TOKEN_EXPIRED"),
    REQUIRE_GET_METHOD("43001", "REQUIRE_GET_METHOD"),
    REQUIRE_POST_METHOD("43002", "REQUIRE_POST_METHOD"),
    REQUIRE_HTTPS("43003", "REQUIRE_HTTPS"),
    REQUIRE_LOGIN("43004", "REQUIRE_LOGIN"),
    EMPTY_POST_DATA("44001", "EMPTY_POST_DATA"),
    API_FREQ_OUT_OF_LIMIT("45001", "API_FREQ_OUT_OF_LIMIT"),
    API_UNAUTHORIZED("48001", "API_UNAUTHORIZED"),
    API_BLOCKED("48002", "API_BLOCKED");

    //        INQUIRY_SP_NO_RSP("2", "INQUIRY SP NO RSP"), //OTT没有返回消息
//        INQUIRY_SP_RSP_FORMAT_ERROR("3", "INQUIRY SP RSP FORMAT ERROR"), //解析OTT消息失败
//        INQUIRY_DEVICE_NOT_EXISTS("4", "INQUIRY DEVICE NOT EXISTS"),   // 设备不存在
//        INQUIRY_DEVICE_CUSTOMER_MAP_NOT_EXISTS("5",  "INQUIRY DEVICE CUSTOMER MAP NOT EXISTS"),   // 设备用户关系不存在
//        INQUIRY_CUSTOMER_NOT_EXISTS("6",  "INQUIRY CUSTOMER NOT EXISTS"),   // 用户不存在
//        INQUIRY_PS_NOT_EXISTS("7", "INQUIRY PS NOT EXISTS"),  // 节目不存在
//        INQUIRY_CUSTOMER_PHONE_NOT_MAPPING("8", "INQUIRY CUSTOMER PHONE NOT MAPPING"),   // 用户手机号码与设备信息不匹配
//        INQUIRY_NOT_BUY("9",  "INQUIRY NOT BUY"),   // 未订购产品包
//        INQUIRY_FAILED("10", "INQUIRY FAILED"),    // 鉴权失败
//        INQUIRY_CUSTOMER_INVALID("11", "INVALID CUSTOMER"),    // 用户状态无效
//        INQUIRY_CONTENT_NOT_EXIST("12", "Content not exist"), //内容不存在
//        INQUIRY_PPINFOS_NOT_EXIST("13", "PpInfos not exist"), //产品包套餐不存在
    // INQUIRY_NO_SPPEDUP_ORDERED("14", "未订购所需的提速包"); //产品包套餐不存在
    protected String errCode;
    private String errMsg;


    BaseResponse(String code, String msg) {
        this.errCode = code;
        this.errMsg = msg;
    }

//        public static String getMessage(String code) {
//            for (BaseResponse reply : BaseResponse.values()) {
//                if (StringUtils.equals(reply.getErrCode(), code)) {
//                    return reply.getErrMsg();
//                }
//            }
//            return "NO MESSAGE";
//        }

    public String getErrCode() {
        return errCode;
    }


    public String getErrMsg() {
        return errMsg;
    }


}

