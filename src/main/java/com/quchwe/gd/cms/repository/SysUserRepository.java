package com.quchwe.gd.cms.repository;

import com.quchwe.gd.cms.bean.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by quchwe on 2017/3/16 0016.
 */
public interface SysUserRepository extends JpaRepository<SysUser,Long>{
    SysUser findByUserName(String userName);

    SysUser findByPhoneNumber(String phoneNumber);
}
