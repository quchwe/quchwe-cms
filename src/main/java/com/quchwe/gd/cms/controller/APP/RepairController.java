package com.quchwe.gd.cms.controller.APP;

import com.quchwe.gd.cms.bean.*;
import com.quchwe.gd.cms.repository.CarRepository;
import com.quchwe.gd.cms.repository.RepairInfoRepository;
import com.quchwe.gd.cms.repository.SysUserRepository;
import com.quchwe.gd.cms.utils.FileUtils;
import com.quchwe.gd.cms.utils.JsonUtils;
import com.quchwe.gd.cms.utils.NormalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * Created by quchwe on 2017/4/10 0010.
 */

@RestController
@RequestMapping(value = "/app/v1/repair")
@Slf4j
public class RepairController {


    private RepairInfoRepository mRepairInfoRep;
    private SysUserRepository mUserRepo;
    private CarRepository mCarRepo;

    @Autowired
    public RepairController(RepairInfoRepository repairInfoRepository, SysUserRepository userRepository, CarRepository mCarRepo) {
        this.mRepairInfoRep = repairInfoRepository;
        this.mUserRepo = userRepository;
        this.mCarRepo = mCarRepo;
    }

    @RequestMapping(value = "/create_repair", method = RequestMethod.GET)
    public BaseResponseResult<String> createRepairGet() {
        BaseResponseResult responseResult = new BaseResponseResult();

        responseResult.setErrCode(BaseResponse.REQUIRE_POST_METHOD.getErrCode());
        responseResult.setErrMsg(BaseResponse.REQUIRE_POST_METHOD.getErrMsg());
        return responseResult;
    }


    @RequestMapping(value = "/create_repair", method = RequestMethod.POST)
    public BaseResponseResult<String> createRepair(@RequestParam("phoneNumber") String phoneNumber,
                                                   @RequestParam("accidentType") String accidentType,
                                                   @RequestParam("description") String description,
                                                   @RequestParam("drivingId") String drivingId,
                                                   @RequestParam("file0") MultipartFile file,
                                                   @RequestParam(value = "file1", required = false) MultipartFile file1,
                                                   @RequestParam(value = "file2", required = false) MultipartFile file2
    ) {

        BaseResponseResult responseResult = new BaseResponseResult();
        log.info("----------维修上报---");

        List<String> paths = FileUtils.saveImage("createPair", file, file1, file2);
        if (paths == null || paths.size() == 0) {
            log.error("维修图片保存失败，用户id{}", phoneNumber);
            responseResult.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
            responseResult.setErrMsg("上传记录失败");
            return responseResult;
        }
        String imagePath = "";

        for (String s : paths) {
            imagePath += s + ",";
        }

        try {

            RepairInfo info = new RepairInfo();

            info.setAccidentType(accidentType);
            info.setCreateTime(new Date());
            info.setDescpription(description);
            info.setrepairProgress("created");
            info.setDrivingId(drivingId);
            info.setFilePath(imagePath);
            info.setPhoneNumber(phoneNumber);

            mRepairInfoRep.save(info);

            responseResult.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
            responseResult.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
            return responseResult;
        } catch (Exception e) {
            log.error("保存维修记录失败，用户ID{},日期" + (new Date()), phoneNumber);
            log.error("维修图片保存失败，用户id{}", phoneNumber);
            responseResult.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
            responseResult.setErrMsg("上传记录失败");
            return responseResult;
        }


    }


    @RequestMapping(value = "/get_repair")
    public BaseResponseResult<List<RepairInfo>> getRepairInfo(@RequestParam("phoneNumber") String phoneNumber,
                                                              @RequestParam(value = "carId", required = false) String carId,
                                                              @RequestParam("userToken") String token) {

        BaseResponseResult<List<RepairInfo>> response = new BaseResponseResult<>();

        log.info("请求用户车辆维修信息，用户手机号：{},userToken：{}", phoneNumber, token);
        if (!NormalUtil.isMobileNO(phoneNumber)) {
            response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
            response.setErrMsg("请输入正确的手机号");
            log.error("请求车辆维修信息，手机号错误，手机号{}", phoneNumber);
            return response;

        }
        try {
            SysUser user = mUserRepo.findByPhoneNumber(phoneNumber);
            if (null == user) {
                response.setErrCode(BaseResponse.INQUIRY_PARA_ERROR.getErrCode());
                response.setErrMsg("用户不存在");
                log.error("请求车辆维修信息，用户不存在，手机号{}", phoneNumber);
                return response;
            }
            if (token != user.getUserToken()) {
                response.setErrCode(BaseResponse.INQUIRY_ERROR.getErrCode());
                response.setErrMsg("鉴权失败，请重新登录");
                log.error("请求车辆维修信息，userToken错误 {}", token);
                return response;
            }
            List<RepairInfo> infos = null;
            if (null != carId && !carId.equals("")) {
                infos = mRepairInfoRep.findCarsByCarIdAndPhoneNumber(carId, phoneNumber);
            } else {
                infos = mRepairInfoRep.findByPhoneNumber(phoneNumber);
            }

            if (infos == null) {
                response.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
                response.setErrMsg("无对应车辆维修信息信息");
                log.error("请求车辆维修信息，无数据，手机号：{},车牌号,{}", phoneNumber, carId);
                return response;
            }

            response.setErrCode(BaseResponse.INQUIRY_SUCCESS.getErrCode());
            response.setErrMsg(BaseResponse.INQUIRY_SUCCESS.getErrMsg());
            response.setResultInfo(infos);
            log.info("车辆维修信息返回成功，infos", JsonUtils.toJson(infos));
            return response;

        } catch (Exception e) {
            response.setErrCode(BaseResponse.SYSTEM_ERROR.getErrCode());
            response.setErrMsg("系统异常");
            log.error("系统异常，获取用户车辆维修信息，手机号:{},carId:{},token:{},异常信息：{}", phoneNumber, carId, token, e.getStackTrace());
            return response;
        }
    }
}
