package com.ruoyi.hh.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.Drillproductionbill;

/**
 * (Drillproductionbill)表服务接口
 *
 * @author Eleven
 * @since 2022-06-08 17:49:26
 */
public interface DrillproductionbillService extends IService<Drillproductionbill> {
    public List<String> getDrillsiteCode();

    public List<String> getDrillsiteLocal();

    public List<String> getDrillpurpose();

    public List<String> getHoleNO();

    List<Drillproductionbill> getAllDrillProductionBill();
}

