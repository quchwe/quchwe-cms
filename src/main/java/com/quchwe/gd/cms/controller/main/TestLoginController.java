package com.quchwe.gd.cms.controller.main;

import com.quchwe.gd.cms.bean.BaseRequest;
import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;
import com.quchwe.gd.cms.bean.SysUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by quchwe on 2017/4/5 0005.
 */
@RestController(value = "/login")
public class TestLoginController {

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponseResult login(@RequestBody BaseRequest<SysUser> user){
        System.out.println(user.getMd5());
        BaseResponseResult responseResult =  new BaseResponseResult();
        responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
        responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
        return responseResult;
    }

}
