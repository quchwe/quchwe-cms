package com.quchwe.gd.cms.controller.APP;

import com.quchwe.gd.cms.bean.BaseResponse;
import com.quchwe.gd.cms.bean.BaseResponseResult;
import com.quchwe.gd.cms.bean.RepairInfo;
import com.quchwe.gd.cms.repository.RepairInfoRepository;
import com.quchwe.gd.cms.utils.FileUtils;
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
@RequestMapping(value = "/")
@Slf4j
public class RepairController {


    private RepairInfoRepository mRepairInfoRep;

    @Autowired
    public RepairController(RepairInfoRepository repairInfoRepository) {
        this.mRepairInfoRep = repairInfoRepository;
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
            imagePath += s+",";
        }

        try {

            RepairInfo info = new RepairInfo();

            info.setAccidentType(accidentType);
            info.setCreateTime(new Date());
            info.setDescpription(description);
            info.setrepairProgress("created");
            info.setDrivingId(drivingId);
            info.setFilePath(imagePath);
            info.setUserId(phoneNumber);

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


}
