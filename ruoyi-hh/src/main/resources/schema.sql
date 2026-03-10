-- -- 今天的表
-- SET @sqlstr1 = CONCAT('create table IF NOT EXISTS data_history',DATE_FORMAT(DATE_SUB(curdate(),INTERVAL 0 DAY),'%Y%m%d'),
-- ' (
--   `id` int NOT NULL AUTO_INCREMENT,
--   `measure_pointid` int NULL DEFAULT NULL ,
--   `drainageunitid` int NULL DEFAULT NULL ,
--   `workfaceid` int NULL DEFAULT NULL ,
--   `mixtureflow` double NULL DEFAULT 0 ,
--   `pureflow` double NULL DEFAULT 0 ,
--   `temperature` double NULL DEFAULT 0 ,
--   `ch4concentration` double NULL DEFAULT 0 ,
--   `coconcentration` double NULL DEFAULT 0 ,
--   `co2concentration` double NULL DEFAULT 0 ,
--   `negativepressure` double NULL DEFAULT 0 ,
--   `totalmixtureflow` double NULL DEFAULT 0 ,
--   `totalpureflow` double NULL DEFAULT 0 ,
--   `ethene` double NULL DEFAULT 0 ,
--   `ethyne` double NULL DEFAULT 0 ,
--   `oconcentration` double NULL DEFAULT 0 ,
--   `date` datetime NULL DEFAULT NULL ,
--   PRIMARY KEY (`id`) USING BTREE
-- ) '
-- );
-- PREPARE stmt1 FROM @sqlstr1 ;
-- EXECUTE stmt1 ;
--
-- --明天的表
-- SET @sqlstr2 = CONCAT('create table IF NOT EXISTS data_history',DATE_FORMAT(DATE_SUB(curdate(),INTERVAL 0 DAY),'%Y%m%d'),
-- ' (
--    `id` int NOT NULL AUTO_INCREMENT,
--   `measure_pointid` int NULL DEFAULT NULL ,
--   `drainageunitid` int NULL DEFAULT NULL ,
--   `workfaceid` int NULL DEFAULT NULL ,
--   `mixtureflow` double NULL DEFAULT 0 ,
--   `pureflow` double NULL DEFAULT 0 ,
--   `temperature` double NULL DEFAULT 0 ,
--   `ch4concentration` double NULL DEFAULT 0 ,
--   `coconcentration` double NULL DEFAULT 0 ,
--   `co2concentration` double NULL DEFAULT 0 ,
--   `negativepressure` double NULL DEFAULT 0 ,
--   `totalmixtureflow` double NULL DEFAULT 0 ,
--   `totalpureflow` double NULL DEFAULT 0 ,
--   `ethene` double NULL DEFAULT 0 ,
--   `ethyne` double NULL DEFAULT 0 ,
--   `oconcentration` double NULL DEFAULT 0 ,
--   `date` datetime NULL DEFAULT NULL ,
--   PRIMARY KEY (`id`) USING BTREE
-- ) '
-- );
-- PREPARE stmt2 FROM @sqlstr2 ;
-- EXECUTE stmt2 ;

-- 今天的表
SET @sqlstr1 = CONCAT('create table IF NOT EXISTS t_bk_sensor_data',DATE_FORMAT(DATE_SUB(curdate(),INTERVAL 0 DAY),'%Y%m%d'),
'( `id` int NOT NULL AUTO_INCREMENT,`coal_mine_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''煤矿名称'',
  `measure_point_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点编码'',
  `sensor_type_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''传感器类型名称'',
  `measure_point_local` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点安装位置'',
  `measure_point_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点值'',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点数值单位'',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点状态'',
  `time` datetime NULL DEFAULT NULL COMMENT ''数据时间'',
  PRIMARY KEY (`id`) USING BTREE
)'
);
PREPARE stmt1 FROM @sqlstr1 ;
EXECUTE stmt1 ;

-- 明天的表
SET @sqlstr2 = CONCAT('create table IF NOT EXISTS t_bk_sensor_data',DATE_FORMAT(DATE_SUB(curdate(),INTERVAL -1 DAY),'%Y%m%d'),
'( `id` int NOT NULL AUTO_INCREMENT,`coal_mine_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''煤矿名称'',
  `measure_point_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点编码'',
  `sensor_type_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''传感器类型名称'',
  `measure_point_local` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点安装位置'',
  `measure_point_value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点值'',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点数值单位'',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT ''测点状态'',
  `time` datetime NULL DEFAULT NULL COMMENT ''数据时间'',
  PRIMARY KEY (`id`) USING BTREE
)'
);
PREPARE stmt2 FROM @sqlstr2 ;
EXECUTE stmt2 ;
