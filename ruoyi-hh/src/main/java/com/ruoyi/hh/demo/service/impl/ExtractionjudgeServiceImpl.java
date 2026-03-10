package com.ruoyi.hh.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.Extractionjudge;
import com.ruoyi.hh.demo.mapper.ExtractionjudgeDao;
import com.ruoyi.hh.demo.service.ExtractionjudgeService;
import org.springframework.stereotype.Service;

/**
 * (Extractionjudge)表服务实现类
 *
 * @author Eleven
 * @since 2022-06-13 17:07:02
 */
@Service("extractionjudgeService")
public class ExtractionjudgeServiceImpl extends ServiceImpl<ExtractionjudgeDao, Extractionjudge> implements ExtractionjudgeService {

	@Resource
	private ExtractionjudgeDao extractionjudgeDao;
	@Override
	public List<Extractionjudge> getExtractionjudgeList() {
		// TODO Auto-generated method stub
		return extractionjudgeDao.getExtractionjudgeList();
	}

	@Override
	public List<Extractionjudge> getExtractionjudgeByStripeCode(String StripeCode) {
		// TODO Auto-generated method stub
		return extractionjudgeDao.getExtractionjudgeByStripeCode(StripeCode);
	}

}

