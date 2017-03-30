package com.quchwe.gd.cms.controller;

import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;
import com.quchwe.gd.cms.bean.SysUser;
import com.quchwe.gd.cms.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
@Controller
@RequestMapping("/login_and_sign_up")
public class LoginController {
    @Autowired
    SysUserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String loginAndSignUp() {
        return "/loginAndSignUp";
    }

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register() {

        System.out.printf("rtt");
        return "/register";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseResult login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        BaseResponseResult responseResult = new BaseResponseResult();

        System.out.println("userName = [" + userName + "], password = [" + password + "]");
        SysUser user = userRepository.findByUserName(userName);


        if (user == null) {
            responseResult.setErrCode(BaseResponse.INVALID_UID.getErrCode());
            responseResult.setErrMsg(BaseResponse.INVALID_UID.getErrMsg());
            return responseResult;
        }
        if (user.getPassword() != null && user.getPassword().equals(password)) {
            responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
            return responseResult;
        }
        responseResult.setErrCode(BaseResponse.SYSTEM_ERROR.getErrCode());
        responseResult.setErrMsg(BaseResponse.SYSTEM_ERROR.getErrMsg());
        return responseResult;
    }




    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseResult register(@RequestBody SysUser user) {


        System.out.println(user.getUserName());
        BaseResponseResult responseResult = new BaseResponseResult();
        if (user == null) {
            responseResult.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_PARA_ERROR.getErrMsg());
            return responseResult;
        }
        SysUser sysUser = userRepository.save(user);
        if (sysUser != null) {
            responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
            return responseResult;
        } else if (sysUser == null) {
            responseResult.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_ERROR.getErrMsg());
            return responseResult;
        }

        responseResult.setErrCode(BaseResponse.SYSTEM_ERROR.getErrCode());
        responseResult.setErrMsg(BaseResponse.SYSTEM_ERROR.getErrMsg());

        return responseResult;
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world <img src='http://avatar.csdn.net/2/9/5/1_lnwxwcl.jpg'>";

    }

}
