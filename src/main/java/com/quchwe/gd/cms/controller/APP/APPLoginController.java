package com.quchwe.gd.cms.controller.APP;

import com.quchwe.gd.cms.bean.BaseRequest;
import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;
import com.quchwe.gd.cms.bean.SysUser;
import com.quchwe.gd.cms.repository.SysUserRepository;
import com.quchwe.gd.cms.utils.FileUtils;
import com.quchwe.gd.cms.utils.NormalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by quchwe on 2017/4/5 0005.
 */
@RestController
@RequestMapping(value = "app/v1/user")
public class APPLoginController {

    private static Logger log = LoggerFactory.getLogger(APPLoginController.class);
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
            String str = user.getHeadImage().substring(14, user.getHeadImage().length());

            user.setHeadImage("http://192.168.43.8:8443/image/get/" + str.replace("\\", "/"));
            if (user == null) {
                responseResult.setErrCode(BaseResponse.INVALID_UID.getErrCode());
                responseResult.setErrMsg(BaseResponse.INVALID_UID.getErrMsg());
                log.error("用户不存在");
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("数据库查询出错", e.getMessage());
        }
        responseResult.setResultInfo(user);
        responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
        responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
        log.info("用户登录{}", user.toString());
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

    /**
     * 更新用户信息的一个方法，为求速度，极其凌乱，后面的图片保存在数据库中的路径为本地存放位置，
     * 故在最后修改为下载地址，返回给客户端，怎么设计合理，凭个人爱好
     * @return 用户信息
     */
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public BaseResponseResult<SysUser> updateUserInfo(@RequestParam("phoneNumber") String phoneNumber,
                                                      @RequestParam(value = "sex", required = false) String sex,
                                                      @RequestParam(value = "email", required = false) String email,
                                                      @RequestParam(value = "nickName", required = false) String nickName,
                                                      @RequestParam(value = "address", required = false) String address,
                                                      @RequestParam(value = "age", required = false) int age,
                                                      @RequestParam(value = "drivingId", required = false) String drivingId,
                                                      @RequestParam(value = "carType", required = false) String carType,
                                                      @RequestParam(value = "purchaseDate", required = false) String purchaseDate,
                                                      @RequestParam(value = "signature", required = false) String signature,
                                                      @RequestParam(value = "headImage", required = false) MultipartFile file
    ) {
        BaseResponseResult<SysUser> responseResult = new BaseResponseResult<>();
        SysUser user = userRepository.findByPhoneNumber(phoneNumber);
        if (user == null) {
            responseResult.setErrCode(BaseResponse.INVALID_USER_TOKEN.getErrCode());
            responseResult.setErrMsg("用户凭证失效");
            return responseResult;
        }

        String headImagePath = null;

        if (file != null) {
            List<String> paths = FileUtils.saveImage("headImage", file);
            if (paths == null || paths.size() == 0) {
                log.error("用户头像保存失败，用户id{}", phoneNumber);
                responseResult.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
                responseResult.setErrMsg("更新用户信息失败");
                return responseResult;
            }
            headImagePath = paths.get(0);
        }
        if (address != null) {
            user.setAddress(address);
        }
        if (nickName != null) {
            user.setUserName(nickName);
        }
        user.setAge(age);
        if (carType != null) {
            user.setCarType(carType);
        }
        if (email != null) {
            user.setEmail(email);
        }
        //Date d = new Date(purchaseDate.toString());
        if (purchaseDate != null) {
            user.setPurchaseDate(new Date(purchaseDate));
        }
        if (drivingId != null) {
            user.setDrivingLicenseId(drivingId);
        }
        if (sex != null) {
            user.setSex(sex);
        }
        if (signature != null) {
            user.setSignature(signature);
        }
        if (headImagePath != null) {
            user.setHeadImage(headImagePath);
        }

        try {

            userRepository.updateUserByPhoneNumber(user.getAddress(), user.getAge(), user.getCarType(), user.getDrivingLicenseId(), user.getEmail(),
                    user.getHeadImage(), user.getPurchaseDate(), user.getSex(), user.getSignature(), new Date(), user.getUserName(), phoneNumber);

            String str = user.getHeadImage().substring(14, user.getHeadImage().length());

            /**
             * 设置为本机ip，也可通过
             * InetAddress addr = InetAddress.getLocalHost();
             *ip=addr.getHostAddress().toString;//获得本机IP
             *address=addr.getHostName()toString;//获得本机名称
             *来进行拼接 ，
             */
            user.setHeadImage("http://192.168.43.8:8443/image/get/" + str.replace("\\", "/"));
            responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
            responseResult.setResultInfo(user);
            return responseResult;
        } catch (
                Exception e)

        {
            e.printStackTrace();
            log.error("更新用户信息失败 ，用户id{},日期{},{}", phoneNumber, new Date(), e.getStackTrace());
            responseResult.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
            responseResult.setErrMsg("更新用户信息失败");
            return responseResult;
        }

    }

}
