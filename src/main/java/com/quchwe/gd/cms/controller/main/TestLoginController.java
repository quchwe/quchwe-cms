package com.quchwe.gd.cms.controller.main;

import com.quchwe.gd.cms.bean.BaseRequest;
import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;
import com.quchwe.gd.cms.bean.SysUser;
import com.quchwe.gd.cms.repository.SysUserRepository;
import com.quchwe.gd.cms.utils.NormalUtil;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by quchwe on 2017/4/5 0005.
 */
@RestController(value = "/user")
public class TestLoginController {

    private static Logger log = LoggerFactory.getLogger(TestLoginController.class);
    @Autowired
    SysUserRepository userRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public BaseResponseResult<SysUser> login(@RequestBody BaseRequest<SysUser> sysUserBaseRequest) {
        // System.out.println(user.getMd5());
        BaseResponseResult responseResult = new BaseResponseResult();
        if (sysUserBaseRequest.getRequestBean() == null || !NormalUtil.authentication(sysUserBaseRequest, sysUserBaseRequest.getMd5())) {
            responseResult.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_PARA_ERROR.getErrMsg());
            log.error("鉴权失败");
            return responseResult;
        }
        SysUser user = null;
        try {
            user = userRepository.findByPhoneNumber(sysUserBaseRequest.getRequestBean().getPhoneNumber());

            if (user == null) {
                responseResult.setErrCode(BaseResponse.INVALID_UID.getErrCode());
                responseResult.setErrMsg(BaseResponse.INVALID_UID.getErrMsg());
                log.error("用户不存在");
                return responseResult;
            }
        } catch (Exception e) {
            log.error("数据库查询出错", e.getMessage());
        }
        responseResult.setResultInfo(user);
        responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
        responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
        return responseResult;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public BaseResponseResult<String> register(@RequestBody BaseRequest<SysUser> userRequest) {
        BaseResponseResult responseResult = new BaseResponseResult();
        if (null == userRequest.getRequestBean() || !NormalUtil.authentication(userRequest, userRequest.getMd5())) {
            responseResult.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_PARA_ERROR.getErrMsg());
            log.error("鉴权失败");
            return responseResult;
        }
        try {
            if (null != userRepository.findByPhoneNumber(userRequest.getRequestBean().getPhoneNumber())) {
                responseResult.setErrCode(BaseResponse.INVALID_UID.getErrCode());
                responseResult.setErrMsg("用户已存在");
                return responseResult;
            }

            if (null != userRepository.save(userRequest.getRequestBean())) {
                responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
                responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
                return responseResult;
            }
        } catch (Exception e) {
            log.error("数据库错误，{}", e.getMessage());
        }
        return null;

    }


}
