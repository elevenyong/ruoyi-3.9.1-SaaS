package com.ruoyi.hh.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.hh.demo.domain.Drillproductionbill;

/**
 * (Drillproductionbill)表数据库访问层
 *
 * @author Eleven
 * @since 2022-06-08 17:49:26
 */
public interface DrillproductionbillDao extends BaseMapper<Drillproductionbill> {
	public List<String> getDrillsiteCode();
	public List<String> getDrillsiteLocal();
	public List<String> getDrillpurpose();
	public List<String> getHoleNO();

	List<Drillproductionbill> getAllDrillProductionBill();
}

