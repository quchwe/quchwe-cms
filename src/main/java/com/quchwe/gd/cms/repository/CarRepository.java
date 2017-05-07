package com.quchwe.gd.cms.repository;

import com.quchwe.gd.cms.bean.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by quchwe on 2017/4/18 0018.
 */
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByPhoneNumber(String phoneNumber);

    Car findCarsByCarIdAndPhoneNumber(String carId, String phoneNumber);

    Car findCarsByCarId(String carId);

    @Modifying
    @Transactional
    @Query("update Car c SET c.carType=?1,c.category = ?2,c.description=?3,c.displacement=?4," +
            "c.files=?5,c.miles=?6,c.origin=?7,c.speed=?8 where c.phoneNumber=?9 and c.carId = ?10")
    int updateByMobAndCarId(String carType, String category,String desc,String dis,String files,String miles,
                            String origin,String speed,String phoneNumber,String carId);
}
