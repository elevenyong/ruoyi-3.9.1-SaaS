公共字段:
 alter TABLE book ADD  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者';
 alter TABLE book ADD  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
 alter TABLE book ADD `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者';
 alter TABLE book ADD  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
 alter TABLE book ADD `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注';


钻孔信息,冲孔信息,钻孔类别联查

select d.*,r.Roadway,c.BoreholeCategory,p.PurposeBorehole,n.NavigationPointName,f.*
        from
        t_designed_borehole d
        LEFT JOIN t_roadway r ON d.RoadWayId = r.id
        LEFT JOIN  t_borehole_category c ON d.BoreholeCategoryId=c.Id
        LEFT JOIN  t_navigation_point n ON d.NavigationPointID=n.id
        LEFT JOIN  t_purpose_borehole p ON d.PurposeBoreholeId = p.Id
				LEFT JOIN  t_actual_flushing f ON d.Id=f.BroeholeId
WHERE d.RoadWayId=23

冲孔台帐
select f.dateflushing,r.roadway,a.groupid,a.BoreholeNO,a.boreholeLength,f.PunchingParagraph1,f.PunchingParagraph2,(f.PunchingParagraph2-f.PunchingParagraph1) AS PunchingParagraphLength,
				f.TimeBeginFlushing,f.PersonConstruction,f.ActualCoalAmountFlushed,f.ReceptionTime
				from t_actual_flushing f
				LEFT JOIN t_actual_borehole a ON a.id= f.boreholeid
				LEFT JOIN t_roadway r ON a.roadwayid=r.id
				where r.id = #{roadwayid} and
				f.dateflushing BETWEEN '2022-07-01' and '2022-07-20'

冲煤量完成情况
select r.roadway,count(a.BoreholeId) AS boreholecount,sum((a.PunchingParagraph2-a.PunchingParagraph1)) AS PunchingParagraphLength,SUM(a.ActualCoalAmountFlushed) AS ActualCoalAmountFlushed,
				sum(d.DesignedCoalAmountFlush) AS DesignedCoalAmountFlush,sum(a.CompletionRatioFlushedCoal)/count(a.BoreholeId) AS CompletionRatioFlushedCoal
				from t_actual_flushing a
				LEFT JOIN t_actual_borehole b ON a.BoreholeId =b.id
				LEFT JOIN t_designed_flushing d ON a.BoreholeId=d.BoreholeId
				LEFT JOIN t_roadway r ON b.RoadWayId =r.id
				where r.Id= 1 and b.drillingdate BETWEEN '2022-07-01' and '2022-07-20'



冲孔全部信息联查其他
select f.*,r.roadway,a.groupid,a.BoreholeNO,a.boreholeLength,(f.PunchingParagraph2-f.PunchingParagraph1) AS PunchingParagraphLength
				from t_actual_flushing f
				LEFT JOIN t_actual_borehole a ON a.id= f.boreholeid
				LEFT JOIN t_roadway r ON a.roadwayid=r.id
				where r.id = 1 and
				f.dateflushing BETWEEN '2022-07-01' and '2022-07-20'


测点人工对比数据
SELECT m.MeasuringPointName,c.MeasureTime,c.Concentration, s.ConcentrationS,c.ConcentrationRevise, c.Vacuum,s.VacuumS, c.VacuumRevise, c.MixedFlow, s.MixedFlowS,
        c.MixedFlowRevise, c.PureFlow,s.PureFlowS, c.PureFlowRevise, c.Temperature,s.TemperatureS, c.TemperatureRevise, c.COPortable,s.COS,
        c.CORevise, c.BarometerValue,s.PressureS, c.PressureRevise, c.ActualMeasurementPersion, c.TimingPersion
        from
         t_measure_point_contrast c
         LEFT JOIN t_measure_point_sensordata s ON c.MeasurePointId=s.MeasurePointId
         LEFT JOIN t_measuring_point m ON c.MeasurePointId = m.id
         WHERE m.id = 1 and s.Time = c.MeasureTime

         
瓦斯含量/压力台账
SELECT m.MeasuringPointName,a.GroupId,a.BoreholeNO,a.InclinationAngle,a.AzimuthAngle,p.PurposeBorehole,g.SamplingDepth,g.GasContent,g.GasPressure,g.MeasureDate
        from
        t_gas_content_pressure g
        LEFT JOIN t_measuring_point m ON g.MeasurePointId=m.id
        LEFT JOIN t_actual_borehole a ON g.BoreholeId = a.Id
        LEFT JOIN t_purpose_borehole p ON a.PurposeBoreholeId=p.Id
	WHERE 	a.RoadWayId = 23 AND a.GroupId IN (2,6)
	AND a.BoreholeNO IN (1)
	AND g.MeasureDate BETWEEN '2022-07-01 00:00:00' and '2022-07-22 00:00:00'

钻孔抽采参数测量
select a.GroupId,a.BoreholeNO, p.* from
     t_drainage_parameter p
     LEFT JOIN t_actual_borehole a
     ON p.BoreholeId=a.id
     where a.id= 1 AND p.MeasureDate BETWEEN '2022-07-01' and '2022-07-29'


瓦斯抽采钻孔施工台帐

SELECT r.Roadway,a.GroupId,a.BoreholeNO,a.PullDrillDate,d.AzimuthAngle AS DesAzimuthAngle,a.AzimuthAngle AS ActAzimuthAngle,d.InclinationAngle AS DesInclinationAngle,
a.InclinationAngle AS ActInclinationAngle,d.Depth1stCoalSeamStart AS DesDepth1stCoalSeamStart,a.Depth1stCoalSeamStart AS ActDepth1stCoalSeamStart,a.AdjacentBoreholeNo,
a.DistanceAdjacentBorehole,a.HeightArchBaseline,n.NavigationPointName,a.DistanceToNavigationPoint,a.DistanceToCentralLineTunnel,af.PersonConstruction,af.PersonChecking,
a.PulledGasConcentration,a.PulledCOConcentration,h.HoleSealType,(af.PunchingParagraph2-PunchingParagraph1)AS FlushingLength,
(d.InclinationAngle-a.InclinationAngle) AS ErrInclinationAngle,af.ActualCoalAmountFlushed,af.CompletionRatioFlushedCoal
from t_actual_borehole a
LEFT JOIN t_designed_borehole d ON
CONCAT(a.RoadWayId,a.GroupId,a.BoreholeNO) =CONCAT(d.RoadWayId,d.GroupId,d.BoreholeNO)
LEFT JOIN t_actual_flushing af ON
a.id=af.Id
LEFT JOIN t_navigation_point n ON
a.NavigationPointId =n.id
LEFT JOIN t_sealing_grouting s ON
a.id=s.BoreholeId
LEFT JOIN t_holeseal_type h ON
h.Id = s.HolesealTypeId
LEFT JOIN t_roadway r ON
r.Id = a.RoadWayId
LEFT JOIN t_purpose_borehole p ON
p.Id = a.PurposeBoreholeId

WHERE a.RoadWayId = #{roadWayId}
AND
a.PurposeBoreholeId = #{purposeBoreholeId}
AND
af.ReceptionTime IN #{startdate} AND #{enddate}


钻孔信息
SELECT r.Roadway,a.GroupId,a.BoreholeNO,p.PurposeBorehole,d.AzimuthAngle AS DesAzimuthAngle,a.AzimuthAngle AS ActAzimuthAngle,d.InclinationAngle AS DesInclinationAngle,
a.InclinationAngle AS ActInclinationAngle,d.Depth1stCoalSeamStart AS DesDepth1stCoalSeamStart,a.Depth1stCoalSeamStart AS ActDepth1stCoalSeamStart,
d.BoreholeLength AS DesBoreholeLength,a.BoreholeLength AS ActBoreholeLength,n.NavigationPointName,a.DistanceToNavigationPoint,h.HoleSealType,
(af.PunchingParagraph2-PunchingParagraph1)AS FlushingLength,af.ActualCoalAmountFlushed,af.CompletionRatioFlushedCoal
from t_actual_borehole a
LEFT JOIN t_designed_borehole d ON
CONCAT(a.RoadWayId,a.GroupId,a.BoreholeNO) =CONCAT(d.RoadWayId,d.GroupId,d.BoreholeNO)
LEFT JOIN t_actual_flushing af ON
a.id=af.Id
LEFT JOIN t_navigation_point n ON
a.NavigationPointId =n.id
LEFT JOIN t_sealing_grouting s ON
a.id=s.BoreholeId
LEFT JOIN t_holeseal_type h ON
h.Id = s.HolesealTypeId
LEFT JOIN t_roadway r ON
r.Id = a.RoadWayId
LEFT JOIN t_purpose_borehole p ON
p.Id = a.PurposeBoreholeId
WHERE a.RoadWayId = #{roadWayId}
<if test="groupid!=null">AND a.GroupId IN
    <foreach collection="groupid" item="item" index="index" open="(" separator="," close=")">
        #{item}
    </foreach>
</if>
<if test="purposeBoreholeId!=null">AND a.PurposeBoreholeId IN
    <foreach collection="purposeBoreholeId" item="item" index="index" open="(" separator="," close=")">
        #{item}
    </foreach>
</if>
AND
af.ReceptionTime BETWEEN  #{startdate} AND #{enddate}


预抽效果指标分析

SELECT e.EvaluateUnitName,sum(a.BoreholeLength),sum(d.Capacity),sum(d.GasTotalReserves)
FROM t_evaluate_unit e
LEFT JOIN t_drainage_unit d ON d.EvaluationUnitId =e.Id
LEFT JOIN t_measuring_point p ON p.DrainageUnitId=d.Id
LEFT JOIN t_actual_borehole a ON a.MeasurePointId=p.id
GROUP BY WorkFaceId

修改增值从1
ALTER TABLE 表名 AUTO_INCREMENT = 1
查询外键引用
select * from INFORMATION_SCHEMA.KEY_COLUMN_USAGE  where REFERENCED_TABLE_NAME = 't_measuring_point'




插入查询出来的数据
insert into INTO t_sensor_data (SensorCode, PointName, SubStationID, PortNO, UnitName, MonitoringValue, State, `MaxValue`, MaxTime, `MinValue`, MinTime, SumValue, SumCount, AvgValue, StartTime, EndTime) SELECT StartTime,EndTIme,MinValue,MinTime,MaxV,MaxTime,AvgValue,ValidTime,Period FROM analogdata


联查传感器信息:
SELECT r.Roadway,m.MeasuringPointName,t.Kind,t.UnitName,i.SensorCode,i.SensorName, d.SubStationId,d.MonitoringValue,d.`MaxValue`,d.MaxTime,d.MinValue,d.MinTime,ROUND(SUM(d.SumValue),2) AS SumValue,ROUND(SUM(d.AvgValue)/COUNT(d.AvgValue),2) AS AvgValue,d.StartTime,d.EndTime
 from t_sensor_data d LEFT JOIN t_sensor_info i
ON d.SensorId=i.ID
LEFT JOIN t_sensor_type t ON t.id=i.SensorTypeId
LEFT JOIN t_measuring_point m ON m.id= i.MeasurePointId
LEFT JOIN t_roadway r ON r.id=m.RoadWayId
WHERE DATE_FORMAT(StartTime,'%Y-%m-%d') = '2022-02-25' AND m.id =1 GROUP BY  t.UnitName

SELECT r.Roadway,m.MeasuringPointName,t.Kind,t.UnitName,i.SensorCode,i.SensorName, d.SubStationId,d.MonitoringValue,d.`MaxValue`,d.MaxTime,d.MinValue,d.MinTime,ROUND(SUM(d.SumValue),2) AS SumValue,ROUND(SUM(d.AvgValue)/COUNT(d.AvgValue),2) AS AvgValue,d.StartTime,d.EndTime
from t_sensor_data d LEFT JOIN t_sensor_info i
ON d.SensorInfoId=i.ID
LEFT JOIN t_sensor_type t ON t.id=i.SensorTypeId
LEFT JOIN t_measuring_point m ON m.id= i.MeasurePointId
LEFT JOIN t_roadway r ON r.id=m.RoadWayId
WHERE DATE_FORMAT(StartTime,'%Y-%m-%d') = #{date}
<if test="measurePointId!=null">AND m.id IN
    <foreach collection="measurePointId" item="item" index="index" open="(" separator="," close=")">
        #{item}
    </foreach>
</if>
 GROUP BY  t.UnitName,m.id

钻孔施工台帐
SELECT r.Roadway,a.groupid,a.borehole_no,a.drilling_date,sh.shift_info,
d.azimuth_angle AS desAzimuthangle,a.azimuth_angle AS actAzimuthangle,ROUND(d.inclination_angle,2) AS desInclinationangle,ROUND(a.inclination_angle,2) AS actInclinationangle,ROUND((a.inclination_angle-d.inclination_angle),2) AS inclinationangleErr,
a.bore_diameter,ROUND(d.borehole_length,2) AS desboreholelength,ROUND(a.borehole_length,2) AS actboreholelength,ROUND(d.depth_1st_coal_seam_start,2),a.depth_1st_coal_seam_start,ROUND((a.depth_1st_coal_seam_start-d.depth_1st_coal_seam_start),2)AS seamcoalErr,
ROUND((a.depth_1st_coal_seam_start-d.depth_1st_coal_seam_start)/a.depth_1st_coal_seam_start,2) AS seamcoalErrratio,d.depth_1st_coal_seam_end AS descoalseamend, a.depth_1st_coal_seam_end AS actcoalseamend,ROUND((a.depth_1st_coal_seam_end-d.depth_1st_coal_seam_end)/a.depth_1st_coal_seam_end,2)
 AS Errratio,n.navigation_point_name,a.distance_to_navigation_point,a.distance_to_navigation_point,p.purpose_borehole,ht.hole_seal_type,a.pulled_gas_concentration,a.chargehand AS drillingchargehand,a.gaschecker,a.pull_drill_date,sg.checker_pipeinsertion,sg.length_pipeInsertion,sg.date_pipeinsertion,sg.chargehand AS sgchargehand,sg.grouting_date,sg.actual_amount_bag_grouting,sg.exception_label,af.date_flushing,CONCAT(af.punching_paragraph1,'-',af.punching_paragraph2) AS punching_paragraph,ROUND((af.punching_paragraph2-af.punching_paragraph1),2)AS punchinglength,
af.total_time_flushing,af.water_pressure_during_flushing,af.chargehand AS afchargehand,af.actual_coal_amount_flushed,af.reception_time FROM
t_sealing_grouting s
LEFT JOIN t_actual_borehole a ON a.id=s.boreholeid
LEFT JOIN t_designed_borehole d ON CONCAT(a.roadwayid,a.groupid,a.borehole_no)=CONCAT(d.roadwayid,d.groupid,d.borehole_no)
LEFT JOIN t_designed_flushing df ON df.boreholeid=d.id
LEFT JOIN t_actual_flushing af ON af.boreholeid=a.id
LEFT JOIN t_roadway r ON r.id = a.roadwayid
LEFT JOIN t_shiftinfo sh ON sh.id=s.ShiftId
LEFT JOIN t_navigation_point n ON a.navigation_pointId=n.id
LEFT JOIN t_purpose_borehole p ON a.purpose_boreholeId=p.id
LEFT JOIN t_sealing_grouting sg ON sg.boreholeid=a.id
LEFT JOIN t_holeseal_type ht ON ht.id=sg.holeseal_typeid


查询工作面抽采动态
SELECT
	w.working_face_name,
	w.work_face_serial,
	m.id AS miningareaid,
	m.mining_area,
	c.id AS coalid,
	c.coal_serial,
	c.coal_thick,
	c.coal_reserves,
	w.towards_length,
	w.cut_length,
	w.gas_content_original,(
		w.gas_content_original * c.coal_reserves
	) AS gas_total_reserves,
	gas_amount_reach_standard,
	count( d.id ) AS countdrainageunit
FROM
	t_working_face w
	LEFT JOIN t_coalinfo c ON w.coal_id = c.id
	LEFT JOIN t_mining_area m ON w.mining_area_id = m.id
	LEFT JOIN t_drainage_unit d ON d.work_face_id = w.id
GROUP BY
	w.id




增加baseentity
ALTER TABLE
t_mining_area
add column  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '创建者';
ALTER TABLE
t_mining_area
add column  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间';
ALTER TABLE
t_mining_area
add column  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '更新者';
ALTER TABLE
t_mining_area
add column  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间';
ALTER TABLE
t_mining_area
add column  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注';

新建表t_stressmeter_info:
CREATE TABLE t_stressmeter_info (
                                    id                    INTEGER PRIMARY KEY AUTOINCREMENT,
                                    measure_point_code    VARCHAR,
                                    area_name             VARCHAR,
                                    sensor_type_name      VARCHAR,
                                    sensor_local          VARCHAR,
                                    stressmeter_depth     DOUBLE,
                                    installation_time     TIME,
                                    X                     DOUBLE,
                                    Y                     DOUBLE,
                                    Z                     DOUBLE,
                                    direction             VARCHAR,
                                    initialization_stress DOUBLE,
                                    updateTime            TIME
);
新建表t_stressmeter_data:
CREATE TABLE t_stressmeter_data (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    measure_point_code VARCHAR,
    sensor_type_name VARCHAR,
    sensor_local VARCHAR,
    value DOUBLE,
    status INTEGER,
    date VARCHAR
);
COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

