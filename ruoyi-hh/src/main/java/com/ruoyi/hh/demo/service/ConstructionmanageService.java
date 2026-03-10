package com.ruoyi.hh.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.Constructionmanage;
import com.ruoyi.hh.demo.domain.SumByQuarterEnt;
import com.ruoyi.hh.demo.domain.TaskProgressData;

/**
 * (Constructionmanage)表服务接口
 *
 * @author Eleven
 * @since 2022-06-07 17:08:33
 */
public interface ConstructionmanageService extends IService<Constructionmanage> {
	public List<Constructionmanage> getConstructionManageList();
	public List<TaskProgressData> getTaskProgress();
	public List<SumByQuarterEnt> sumByQuarter(int TaskId);
}

