package com.quchwe.gd.cms.repository;

import com.quchwe.gd.cms.bean.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by quchwe on 2017/4/18 0018.
 */
public interface CarRepository extends JpaRepository<Car,Long> {
    List<Car> findCarsByPhoneNumber(String phoneNumber);
    Car findCarsByCarIdAndPhoneNumber(String carId,String phoneNumber);

    Car findCarsByCarId(String carId);
}
