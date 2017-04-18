package com.quchwe.gd.cms.repository;

import com.quchwe.gd.cms.bean.RepairInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by quchwe on 2017/4/10 0010.
 */
public interface RepairInfoRepository extends JpaRepository<RepairInfo, Long> {
    List<RepairInfo> findCarsByCarIdAndPhoneNumber(String carId, String phoneNumber);

    List<RepairInfo> findByPhoneNumber(String phoneNumber);
}
