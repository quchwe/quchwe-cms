package com.quchwe.gd.cms.controller.APP;

import com.quchwe.gd.cms.bean.*;
import com.quchwe.gd.cms.repository.CarRepository;
import com.quchwe.gd.cms.repository.SysUserRepository;
import com.quchwe.gd.cms.utils.JsonUtils;
import com.quchwe.gd.cms.utils.NormalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by quchwe on 2017/4/18 0018.
 */
@Slf4j
@RestController
@RequestMapping("/app/v1/car")
public class CarController {
    private CarRepository carRepo;
    private SysUserRepository userRepository;

    @Autowired
    public CarController(CarRepository carRepo, SysUserRepository userRepository) {
        this.carRepo = carRepo;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/get")
    public BaseResponseResult<List<Car>> getCars(@RequestParam(value = "phoneNumber") String phoneNumber,
                                                 @RequestParam(value = "userToken") String token) {

        BaseResponseResult<List<Car>> response = new BaseResponseResult<>();

        log.info("请求用户车辆信息，用户手机号：{},userToken：{}", phoneNumber, token);
        if (!NormalUtil.isMobileNO(phoneNumber)) {
            response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            response.setErrMsg("请输入正确的手机号");
            log.error("添加车辆信息，手机号错误，手机号{}", phoneNumber);
            return response;

        }
        try {
            SysUser user = userRepository.findByPhoneNumber(phoneNumber);
            if (null == user) {
                response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
                response.setErrMsg("用户不存在");
                log.error("用户不存在，手机号{}", phoneNumber);
                return response;
            }
            if (token != user.getUserToken()) {
                response.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
                response.setErrMsg("鉴权失败，请重新登录");
                log.error("userToken错误 {}", token);
                return response;
            }

            List<Car> cars = carRepo.findCarsByPhoneNumber(phoneNumber);
            if (null == cars) {
                response.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
                response.setErrMsg("您还没有车辆");
                log.info("用户无车辆，手机号{}", phoneNumber);
                return response;
            }

            response.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
            response.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
            response.setResultInfo(cars);
            return response;

        } catch (Exception e) {
            response.setErrCode(BaseResponse.SYSTEM_ERROR.getErrCode());
            response.setErrMsg("系统异常");
            log.error("系统异常，获取用户车辆信息，手机号:{},token:{},异常信息：{}", phoneNumber, token, e.getStackTrace());
            return response;
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResponseResult<String> addCars(@RequestBody BaseRequest<Car> carReq) {
        String phoneNumber = carReq.getPhoneNumber();
        String token = carReq.getUserToken();


        BaseResponseResult<String> response = new BaseResponseResult<>();

        log.info("添加用户车辆信息，用户手机号：{},userToken：{}", phoneNumber, carReq.getUserToken());
        if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(token)) {
            response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            response.setErrMsg("缺少参数");
            log.error("添加车辆信息，缺少参数", phoneNumber, token);
            return response;
        }


        log.info("添加用户车辆信息，用户手机号：{},userToken：{}", phoneNumber, carReq.getUserToken());

        if (!NormalUtil.isMobileNO(phoneNumber)) {
            response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            response.setErrMsg("手机号错误");
            log.error("添加车辆信息，手机号错误，手机号{}", phoneNumber);
            return response;

        }
        try {
            SysUser user = userRepository.findByPhoneNumber(phoneNumber);
            if (null == user) {
                response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
                response.setErrMsg("用户不存在");
                log.error("用户不存在，手机号{}", phoneNumber);
                return response;
            }
            if (token != user.getUserToken()) {
                response.setErrCode(BaseResponse.INVALID_USER_TOKEN.getErrCode());
                response.setErrMsg("鉴权失败，请重新登录");
                log.error("userToken错误 {}", token);
                return response;
            }

            Car car = carRepo.findCarsByCarId(carReq.getRequestBean().getCarId());
            if (null != car) {
                response.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
                response.setErrMsg("已有车辆");
                log.info("已有该车牌号，手机号{}", car.getCarId());
                return response;
            }

            Car newCar = carRepo.save(carReq.getRequestBean());
            if (newCar != null) {
                response.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
                response.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
                response.setResultInfo("添加成功");
                log.info("车辆添加成功,车辆信息：{}", JsonUtils.toJson(newCar));
                return response;
            }else {
                response.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
                response.setErrMsg(BaseResponse.INQUIRY_ERROR.getErrMsg());
                response.setResultInfo("添加失败");
                log.info("车辆添加失败,车辆信息：{}", JsonUtils.toJson(carReq));
                return response;
            }
        } catch (Exception e) {
            response.setErrCode(BaseResponse.SYSTEM_ERROR.getErrCode());
            response.setErrMsg("系统异常");
            log.error("系统异常，添加用户车辆信息，{},异常信息：{}", JsonUtils.toJson(carReq), e.getStackTrace());
            return response;
        }
    }
}
