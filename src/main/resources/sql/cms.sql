create table user(`UID` BIGINT(20) PRIMARY key not null,
`USERNAME` varchar(50) NOT NULL,
`USERPASSWORD` VARCHAR(50) NOT NULL,
`SEX` CHAR(2),
`AGE` INT,
`ADDRESS` VARCHAR(50),
`PHONENUMBER` VARCHAR(50),
`EMAIL` VARCHAR(50),
`CARID` VARCHAR(50) NOT NULL,
`PURCHASEDATE` datetime,
`DRIVINGLICENSEID` VARCHAR(50),
`CARTYPE` VARCHAR(50),
`DISCHARGEDATE` datetime,
`CREATETIME` datetime,
`UPDATETIME` datetime,
`LOGINNAME` VARCHAR(50)
);

CREATE TABLE REPAIR_RECORD(`ID` BIGINT NOT NULL  PRIMARY KEY AUTO_INCREMENT,
  `USER_ID` VARCHAR(50) NOT NULL,
 `REPAIRE_DATE` datetime,
  `ACCIDENT_TYPE` VARCHAR(50),
  `CREATE_TIME` DATETIME,
  `DESCRIPTION` VARCHAR(1000),
  `REPAIR_PROGRESS` VARCHAR(50),
  `FILE_PATH` VARCHAR(1000)
);

UPDATE sys_user u SET u.address =?1,u.age =?2,u.car_type = ?3,u.driving_license_id =?4,u.email=?5,u.head_image =?6,u.purchase_date=?7,u.update_time=?8
WHERE u.phone_number = ?9