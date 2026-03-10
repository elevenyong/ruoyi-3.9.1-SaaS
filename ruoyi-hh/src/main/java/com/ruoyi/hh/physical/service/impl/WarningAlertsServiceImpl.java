package com.ruoyi.hh.physical.service.impl;

import com.ruoyi.hh.demo.domain.TActualBorholeException;
import com.ruoyi.hh.demo.domain.TGeoFault;
import com.ruoyi.hh.demo.service.ITGeoFaultService;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.mapper.WarningAlertsMapper;
import com.ruoyi.hh.physical.service.ITActualBoreholeService;
import com.ruoyi.hh.physical.service.ITCoalinfoService;
import com.ruoyi.hh.physical.service.WarningAlertsService;
import com.ruoyi.hh.simulate.domain.SimulationMeasurepointData;
import com.ruoyi.hh.simulate.service.ISimulationMeasurepointDateService;
import com.ruoyi.hh.util.GeoUtil;
import com.ruoyi.hh.util.LimitQueue;
import com.ruoyi.hh.util.data.simulationRealTimeData;
import com.ruoyi.system.service.ISysConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WarningAlertsServiceImpl implements WarningAlertsService {
    @Resource
    private ITGeoFaultService geofaultService;

    @Resource
    private ITActualBoreholeService actualBoreholeService;

    @Resource
    private ITCoalinfoService coalinfoService;

    @Resource
    private simulationRealTimeData sensorWarningSources;

    @Resource
    private WarningAlertsMapper warningAlertsMapper;

    @Resource
    private ISimulationMeasurepointDateService simulationMeasurepointDateService;

    @Resource
    private ISysConfigService configService;

    /**
     * 查询异常信息
     *
     * @param id 异常信息主键
     * @return 异常信息
     */
    @Override
    public WarningAlerts selectWarningAlertsById(Integer id) {
        return warningAlertsMapper.selectWarningAlertsById(id);
    }

    /**
     * 查询异常信息列表
     *
     * @param tWarningAlerts 异常信息
     * @return 异常信息
     */
    @Override
    public List<WarningAlerts> selectWarningAlertsList(WarningAlerts tWarningAlerts) {
        return warningAlertsMapper.selectWarningAlertsList(tWarningAlerts);
    }

    /**
     * 新增异常信息
     *
     * @param tWarningAlerts 异常信息
     * @return 结果
     */
    @Override
    public int insertWarningAlerts(WarningAlerts tWarningAlerts) {
        return warningAlertsMapper.insertWarningAlerts(tWarningAlerts);
    }

    /**
     * 修改异常信息
     *
     * @param tWarningAlerts 异常信息
     * @return 结果
     */
    @Override
    public int updateWarningAlerts(WarningAlerts tWarningAlerts) {
        return warningAlertsMapper.updateWarningAlerts(tWarningAlerts);
    }

    /**
     * 批量删除异常信息
     *
     * @param ids 需要删除的异常信息主键
     * @return 结果
     */
    @Override
    public int deleteWarningAlertsByIds(Long[] ids) {
        return warningAlertsMapper.deleteWarningAlertsByIds(ids);
    }

    /**
     * 删除异常信息信息
     *
     * @param id 异常信息主键
     * @return 结果
     */
    @Override
    public int deleteWarningAlertsById(Long id) {
        return warningAlertsMapper.deleteWarningAlertsById(id);
    }

    /**
     * 获取异常信息
     *
     * @param x
     * @param y
     * @param z
     * @param dis
     * @param type
     * @param workingFaceId
     * @return
     */
    @Override
    public List<WarningAlerts> getWarningAlertsWithParams(Double x, Double y, Double z, Double dis, String type, Integer workingFaceId) {
        ArrayList<WarningAlerts> faultsWarnings = getFaultsWarningAlerts();
        ArrayList<WarningAlerts> drillingWarnings = getDrillingWarningAlerts();
        ArrayList<WarningAlerts> coalbedsWarnings = getCoalbedsWarningAlerts();
        List<String> types = new ArrayList<>();
        Collections.addAll(types, type.split(","));
        List<WarningAlerts> warningLists = new ArrayList<>();
        if (faultsWarnings != null && types.contains("0")) {
            warningLists.addAll(faultsWarnings);
        }
        if (drillingWarnings != null && types.contains("1")) {
            warningLists.addAll(drillingWarnings);
        }
        if (coalbedsWarnings != null && types.contains("2")) {
            warningLists.addAll(coalbedsWarnings);
        }
        if (types.contains("3")) {
            ArrayList<WarningAlerts> sensorsWarning = getSensorWarningAlerts();
            if (sensorsWarning != null) {
                warningLists.addAll(sensorsWarning);
            }
        }
        List<WarningAlerts> resultLists = new ArrayList<>();
        for (WarningAlerts alerts : warningLists) {
            double d = GeoUtil.getDis3D(alerts.getX(), alerts.getY(), alerts.getZ(), x, y, z);
            if (d <= dis) {
                alerts.setLevel(alerts.getType());
                resultLists.add(alerts);
                alerts.setWorkingFaceId(workingFaceId);
                List<WarningAlerts> alertsList = warningAlertsMapper.selectWarningAlertsList(new WarningAlerts(alerts.getX(), alerts.getY(), alerts.getZ(), alerts.getType(), alerts.getWorkingFaceId()));
                // 传感器异常允许同一点位多次记录，其他异常不允许同一点位多次记录
                if (alertsList.size() > 0 && alerts.getType() != 3) {
                    System.out.println(alerts.getId() + ";" + alerts.getAlertMessage() + ";记录已存在");
                } else {
                    alerts.setStatus(0);
                    warningAlertsMapper.insertWarningAlerts(alerts);
                }
            }
        }

        return resultLists;
    }


    /**
     * 获取所有断层信息
     *
     * @return 断层信息
     */
    @Override
    public ArrayList<WarningAlerts> getFaultsWarningAlerts() {
        List<TGeoFault> faults = geofaultService.selectTGeoFaultList(null);
        ArrayList<WarningAlerts> faultsAlerts = new ArrayList<>();
        for (TGeoFault fault : faults) {
            faultsAlerts.add(new FaultsWarningAlerts(fault, fault.getGeoFaultPoints()));
        }
        return faultsAlerts;
    }

    /**
     * 依条件判断获取断层信息
     *
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @return WarningList
     */
    @Override
    public ArrayList<WarningAlerts> getFaultsWarningAlerts(double x, double y, double z) {
        List<TGeoFault> faults = geofaultService.selectTGeoFaultList(null);
        //正断层	距断层一定距离（落差5m以上的50m,落差5m以下的30m)的应力集中带（新田矿 2021.10.2）
        //逆断层	断层带附近（50m范围)、扭转区域，（黔金矿 10.8、新田矿 10.5）
        ArrayList<WarningAlerts> faultsAlerts = new ArrayList<>();
        for (TGeoFault fault : faults) {
            // 坐标距断层中心点距离
            double dis = GeoUtil.getDis3D(x, y, z, fault.getCenterX(), fault.getCenterY(), fault.getCenterZ());

            if (fault.getFaultType().equals(0)) {
                // 正断层
                if (fault.getFaultHeight() >= 5) {
                    if (dis <= 50) {
                        faultsAlerts.add(new FaultsWarningAlerts(fault, fault.getGeoFaultPoints()));
                    }
                } else {
                    if (dis <= 30) {
                        //faultsAlerts.add(new WarningAlerts(fault.getCenterX(), fault.getCenterY(), fault.getCenterZ(), "断层"));
                        faultsAlerts.add(new FaultsWarningAlerts(fault, fault.getGeoFaultPoints()));
                    }
                }
            } else {
                // 逆断层
                if (dis <= 50) {
                    //faultsAlerts.add(new WarningAlerts(fault.getCenterX(), fault.getCenterY(), fault.getCenterZ(), "断层"));
                    faultsAlerts.add(new FaultsWarningAlerts(fault, fault.getGeoFaultPoints()));
                }
            }
        }
        return faultsAlerts;
    }

    /**
     * 获取打钻中出现的异常
     *
     * @return 钻孔异常信息
     */
    @Override
    public ArrayList<WarningAlerts> getDrillingWarningAlerts() {
        List<TActualBorholeException> exceptionList = actualBoreholeService.selectTActualBoreholeWithException();
        ArrayList<WarningAlerts> actualBoreholeExpAlerts = new ArrayList<>();
        for (TActualBorholeException exp : exceptionList) {
            TActualBorehole holeData = actualBoreholeService.selectTActualBoreholeById(exp.getId());
            actualBoreholeExpAlerts.add(new ActualBorholeWarningAlerts(exp, holeData));
        }
        return actualBoreholeExpAlerts;
    }

    /**
     * 传感器异常
     *
     * @return 传感器异常
     */
    public ArrayList<WarningAlerts> getSensorWarningAlerts() {
        int historyDataLimit = 200;
        LimitQueue<SimulationMeasurepointData> exceptionQueue = sensorWarningSources.getExceptionQueue();

        List<SimulationMeasurepointData> sensorData = new ArrayList();
        ArrayList<WarningAlerts> sensorsWarning = new ArrayList<>();
        while (!exceptionQueue.isEmpty()) {
            sensorData.add(exceptionQueue.poll());
        }
        for (SimulationMeasurepointData sensor : sensorData) {
            List<String> exceptionfield = sensor.getErrfield();
            if (exceptionfield.contains("CO") || exceptionfield.contains("CH")) {
                SimulationMeasurepointData querySensor = new SimulationMeasurepointData();
                querySensor.setMeasurePointId(sensor.getMeasurePointId());
                List<SimulationMeasurepointData> historyDataById = simulationMeasurepointDateService.selectSimulationHistoryDataById(querySensor.getMeasurePointId(), historyDataLimit);
                sensorsWarning.add(new SensorWarningAlerts(-91d, 180d, -71d, 3, "传感器数据异常", "测点：" + sensor.getMeasurePointName() + "，" + sensor.getExceptionfield().toString().replaceAll("CO", "一氧化碳(CO)").replaceAll("CH", "甲烷(CH4)") + "值数据超限，请注意！", sensor, historyDataById));
            }
        }
        return sensorsWarning;
    }

    /**
     * 煤层异常
     *
     * @return 煤层异常
     */
    @Override
    public ArrayList<WarningAlerts> getCoalbedsWarningAlerts() {

        String coalThick = configService.selectConfigByKey("sys.alarm.coalThick");
        String coalThickChange = configService.selectConfigByKey("sys.alarm.coalThickChange");
        String coalAngleChange = configService.selectConfigByKey("sys.alarm.coalAngleChange");
        double heightLimit = Double.valueOf(coalThick);
        double heightChangeLimit = Double.valueOf(coalThickChange);
        double angleChangeLimit = Double.valueOf(coalAngleChange);


        List<TCoalinfo> tCoalinfos = coalinfoService.selectTCoalinfoList(new TCoalinfo());
        ArrayList<WarningAlerts> coalbedsExpAlerts = new ArrayList<>();
        for (TCoalinfo tCoalinfo : tCoalinfos) {
            List<TCoalPoint> pointinfoList = tCoalinfo.getCoalPointinfoList();

            // 移除所有断层点数据信息
            List<TCoalPoint> pureCoalPointInfo = new ArrayList<>();
            for (TCoalPoint TCoalPoint : pointinfoList) {
                if (TCoalPoint.getDataType() != 3) {
                    pureCoalPointInfo.add(TCoalPoint);
                }
            }
            pointinfoList = pureCoalPointInfo;

            // 排序
            Collections.sort(pointinfoList);
            TCoalPoint lastPointInfo = null;
            TCoalPoint llastPointInfo = null;
            for (TCoalPoint pointInfo : pointinfoList) {
                // 煤层过厚判断
                if (pointInfo.getCoalThick() >= heightLimit) {
                    coalbedsExpAlerts.add(new CoalbedsWarningAlerts(pointInfo, "煤层厚度异常，厚度：" + pointInfo.getCoalThick() + "m", pointinfoList));
                }
                // 煤层厚度变化判断
                if (lastPointInfo != null) {
                    double v = (pointInfo.getCoalThick() - lastPointInfo.getCoalThick()) / GeoUtil.getDis3D(pointInfo.getFloorCoordinatex(), pointInfo.getFloorCoordinatey(), pointInfo.getFloorCoordinatez(), lastPointInfo.getFloorCoordinatex(), lastPointInfo.getFloorCoordinatey(), lastPointInfo.getFloorCoordinatez());
                    if (v >= heightChangeLimit) {
                        coalbedsExpAlerts.add(new CoalbedsWarningAlerts(new TCoalPoint(-1, -1, (pointInfo.getFloorCoordinatex() + lastPointInfo.getFloorCoordinatex()) / 2, (pointInfo.getFloorCoordinatey() + lastPointInfo.getFloorCoordinatey()) / 2, (pointInfo.getFloorCoordinatez() + lastPointInfo.getFloorCoordinatez()) / 2, 1), "煤层厚度变化异常，厚度改变率：" + Math.round(v * 10000) * 0.0001, pointinfoList));
                    }
                }
                // 煤层倾角变化判断
                //p0 k0  p1 k1 p2 k2  p3
                //                   0
                //                 /
                //                /
                //               /
                // 0-----0-----0/
                //
                // ∆k01=k1-k0 = 0  非变化点
                //
                // ∆k12=k2-k1 > a?
                //        k13 > b?  将P2记为变化点
                //
                if (lastPointInfo != null && llastPointInfo != null) {
                    double k0 = (lastPointInfo.getFloorCoordinatez() - llastPointInfo.getFloorCoordinatez()) / (lastPointInfo.getFloorCoordinatex() - llastPointInfo.getFloorCoordinatex());
                    double k1 = (pointInfo.getFloorCoordinatez() - lastPointInfo.getFloorCoordinatez()) / (pointInfo.getFloorCoordinatex() - lastPointInfo.getFloorCoordinatex());
                    if (Math.abs(k0 - k1) > angleChangeLimit) {
                        double k02 = (pointInfo.getFloorCoordinatez() - llastPointInfo.getFloorCoordinatez()) / (pointInfo.getFloorCoordinatex() - llastPointInfo.getFloorCoordinatex());
                        if (k02 >= angleChangeLimit) {
                            coalbedsExpAlerts.add(new CoalbedsWarningAlerts(lastPointInfo, "编号：" + tCoalinfo.getCoalSerial() + "，煤层倾角变化，倾角：" + Math.round(Math.atan(k02) * 180 / Math.PI * 10000) * 0.0001 + "°", pointinfoList));
                        }
                    }
                }
                llastPointInfo = lastPointInfo;
                lastPointInfo = pointInfo;
            }

        }
        return coalbedsExpAlerts;
    }


}
