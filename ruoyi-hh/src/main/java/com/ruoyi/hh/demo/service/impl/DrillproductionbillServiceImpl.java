package com.ruoyi.hh.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.Drillproductionbill;
import com.ruoyi.hh.demo.mapper.DrillproductionbillDao;
import com.ruoyi.hh.demo.service.DrillproductionbillService;
import org.springframework.stereotype.Service;

/**
 * (Drillproductionbill)表服务实现类
 *
 * @author Eleven
 * @since 2022-06-08 17:49:26
 */
@Service("drillproductionbillService")
public class DrillproductionbillServiceImpl extends ServiceImpl<DrillproductionbillDao, Drillproductionbill> implements DrillproductionbillService {

	@Resource
	private DrillproductionbillDao drillproductionbillDao;
	@Override
	public List<String> getDrillsiteCode() {
		// TODO Auto-generated method stub
		return drillproductionbillDao.getDrillsiteCode();
	}

	@Override
	public List<String> getDrillsiteLocal() {
		// TODO Auto-generated method stub
		return drillproductionbillDao.getDrillsiteLocal();
	}

	@Override
	public List<String> getDrillpurpose() {
		// TODO Auto-generated method stub
		return drillproductionbillDao.getDrillpurpose();
	}

	@Override
	public List<String> getHoleNO() {
		// TODO Auto-generated method stub
		return drillproductionbillDao.getHoleNO();
	}

	public List<Drillproductionbill> getAllDrillProductionBill(){
		return drillproductionbillDao.getAllDrillProductionBill();
	}

}

