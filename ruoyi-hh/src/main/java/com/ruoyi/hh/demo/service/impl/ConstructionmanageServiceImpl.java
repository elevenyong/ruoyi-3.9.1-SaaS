package com.ruoyi.hh.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.Constructionmanage;
import com.ruoyi.hh.demo.domain.SumByQuarterEnt;
import com.ruoyi.hh.demo.domain.TaskProgressData;
import com.ruoyi.hh.demo.mapper.ConstructionmanageDao;
import com.ruoyi.hh.demo.service.ConstructionmanageService;
import org.springframework.stereotype.Service;

/**
 * (Constructionmanage)表服务实现类
 *
 * @author Eleven
 * @since 2022-06-07 17:08:33
 */
@Service("constructionmanageService")
public class ConstructionmanageServiceImpl extends ServiceImpl<ConstructionmanageDao, Constructionmanage> implements ConstructionmanageService {

	@Resource
	private ConstructionmanageDao constructionmanageDao;
	@Override
	public List<Constructionmanage> getConstructionManageList() {
		// TODO Auto-generated method stub
		return constructionmanageDao.getConstructionManageList();
	}

	@Override
	public List<TaskProgressData> getTaskProgress() {
		// TODO Auto-generated method stub
		return constructionmanageDao.getTaskProgress();
	}

	@Override
	public List<SumByQuarterEnt> sumByQuarter(int TaskId) {
		// TODO Auto-generated method stub
		return constructionmanageDao.sumByQuarter(TaskId);
	}

}

