package com.quchwe.gd.cms.repository;

import com.quchwe.gd.cms.bean.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUserName(String userName);

    SysUser findByPhoneNumber(String phoneNumber);

    @Query("UPDATE SysUser u SET u.address =?1,u.age =?2,u.carType = ?3,u.drivingLicenseId =?4,u.email=?5,u.headImage =?6,u.purchaseDate=?7,u.sex=?8,u.updateTime=?9 WHERE u.phoneNumber = ?10")
    @Modifying
    @Transactional
    int updateUserByPhoneNumber(String address, int age, String carType, String drivingId, String email,
                                    String headImage, Date purDate, String sex,Date updateTime,String phoneNumber);


}
