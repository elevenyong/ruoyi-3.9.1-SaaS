package com.ruoyi.hh.demo.service.impl;/*
package com.ruoyi.demo.service.impl;

import com.ruoyi.demo.domain.*;
import com.ruoyi.demo.service.ITGeoFaultService;
import com.ruoyi.demo.service.WarningAlertsService;
import com.ruoyi.quartz.domain.TCoalPoint;
import com.ruoyi.hh.physical.domain.TCoalinfo;
import com.ruoyi.quartz.service.ITActualBoreholeService;
import com.ruoyi.quartz.service.ITCoalPointService;
import com.ruoyi.quartz.service.ITCoalinfoService;
import com.ruoyi.quartz.util.GeoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class WarningAlertsServiceImpl implements WarningAlertsService {
    @Resource
    private ITGeoFaultService geofaultService;

    @Resource
    private ITActualBoreholeService actualBoreholeService;

    @Resource
    private ITCoalinfoService coalinfoService;

    public static final ConcurrentLinkedQueue<?> SensorWarningDataQueue = new ConcurrentLinkedQueue<>();

    */
/**
     * 获取所有断层信息
     *
     * @return 断层信息
     *//*

    @Override
    public ArrayList<WarningAlerts> getFaultsWarningAlerts() {
        List<TGeoFault> faults = geofaultService.selectTGeoFaultList(null);
        ArrayList<WarningAlerts> faultsAlerts = new ArrayList<>();
        for (TGeoFault fault : faults) {
            faultsAlerts.add(new FaultsWarningAlerts(fault, fault.getGeoFaultPoints()));
        }
        return faultsAlerts;
    }

    */
/**
     * 依条件判断获取断层信息
     *
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @return WarningList
     *//*

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

    */
/**
     * 获取打钻中出现的异常
     *
     * @return 钻孔异常信息
     *//*

    @Override
    public ArrayList<WarningAlerts> getDrillingWarningAlerts() {
        List<TActualBorholeException> exceptionList = actualBoreholeService.selectTActualBoreholeWithException();
        ArrayList<WarningAlerts> actualBoreholeExpAlerts = new ArrayList<>();
        for (TActualBorholeException exp : exceptionList) {
            actualBoreholeExpAlerts.add(new ActualBorholeWarningAlerts(exp));
        }
        return actualBoreholeExpAlerts;
    }

    */
/**
     * 传感器异常
     *
     * @return 传感器异常
     *//*

    public ArrayList<WarningAlerts> getSensorWarningAlerts() {
        Object peek = SensorWarningDataQueue.peek();
        if (peek != null) {

        }
        return new ArrayList<>();
    }

    */
/**
     * 煤层异常
     *
     * @return 煤层异常
     *//*

    @Override
    public ArrayList<WarningAlerts> getCoalbedsWarningAlerts() {
        double heightLimit = 3;
        double heightChangeLimit = 2;
        double angleChangeLimit = 10;
        List<TCoalinfo> tCoalinfos = coalinfoService.selectTCoalinfoList(new TCoalinfo());
        ArrayList<WarningAlerts> coalbedsExpAlerts = new ArrayList<>();
        for (TCoalinfo tCoalinfo : tCoalinfos) {
            List<TCoalPoint> pointinfoList = tCoalinfo.getTCoalPointList();
            // 排序
            Collections.sort(pointinfoList);
            TCoalPoint lastPointInfo = null;
            TCoalPoint llastPointInfo = null;
            for (TCoalPoint pointInfo : pointinfoList) {
                // 煤层过厚判断
                if (pointInfo.getCoalThick() >= heightLimit) {
                    coalbedsExpAlerts.add(
                            new CoalbedsWarningAlerts(pointInfo, "煤层厚度异常，厚度：" + pointInfo.getCoalThick() + "m", pointinfoList)
                    );
                }
                // 煤层厚度变化判断
                if (lastPointInfo != null) {
                    double v = (pointInfo.getCoalThick() - lastPointInfo.getCoalThick()) /
                            GeoUtil.getDis3D(
                                    pointInfo.getFloorCoordinatex(), pointInfo.getFloorCoordinatey(), pointInfo.getFloorCoordinatez(),
                                    lastPointInfo.getFloorCoordinatex(), lastPointInfo.getFloorCoordinatey(), lastPointInfo.getFloorCoordinatez());
                    if (v >= heightChangeLimit) {
                        coalbedsExpAlerts.add(
                                new CoalbedsWarningAlerts(new TCoalPoint(-1, -1,
                                        (pointInfo.getFloorCoordinatex() + lastPointInfo.getFloorCoordinatex()) / 2,
                                        (pointInfo.getFloorCoordinatey() + lastPointInfo.getFloorCoordinatey()) / 2,
                                        (pointInfo.getFloorCoordinatez() + lastPointInfo.getFloorCoordinatez()) / 2, 1), "煤层厚度变化异常，厚度改变率：" + Math.round(v * 10000) * 0.0001, pointinfoList)

                        );
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
                    if (Math.abs(k0 - k1) > 0.1) {
                        double k02 = (pointInfo.getFloorCoordinatez() - llastPointInfo.getFloorCoordinatez()) / (pointInfo.getFloorCoordinatex() - llastPointInfo.getFloorCoordinatex());
                        if (k02 >= 0.1) {
                            coalbedsExpAlerts.add(new CoalbedsWarningAlerts(lastPointInfo, "煤层倾角变化，倾角：" + Math.round(Math.atan(k02) * 180 / Math.PI * 10000) * 0.0001 + "°", pointinfoList));
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
*/
