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


    /**
     * modifying声明对数据库内容的修改，如更新，删除，@Transactional,事务操作，@Query 查询语句，语句中数字，与方法参数位置一一对应
     * @param address
     * @param age
     * @param carType
     * @param drivingId
     * @param email
     * @param headImage
     * @param purDate
     * @param sex
     * @param signature
     * @param updateTime
     * @param loginName
     * @param phoneNumber
     * @return
     */
    @Modifying
    @Transactional
    @Query("UPDATE SysUser u SET u.address =?1,u.age =?2,u.carType = ?3,u.drivingLicenseId =?4,u.email=?5,u.headImage =?6," +
            "u.purchaseDate=?7,u.sex=?8,u.signature=?9,u.updateTime=?10 ,u.loginName=?11 WHERE u.phoneNumber = ?12")
    int updateUserByPhoneNumber(String address, int age, String carType, String drivingId, String email,
                                String headImage, Date purDate, String sex, String signature, Date updateTime,
                                String loginName, String phoneNumber);


}
