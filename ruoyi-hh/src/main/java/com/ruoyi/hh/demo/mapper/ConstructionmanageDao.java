package com.ruoyi.hh.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.hh.demo.domain.Constructionmanage;
import com.ruoyi.hh.demo.domain.SumByQuarterEnt;
import com.ruoyi.hh.demo.domain.TaskProgressData;

/**
 * (Constructionmanage)表数据库访问层
 *
 * @author Eleven
 * @since 2022-06-07 17:08:32
 */
public interface ConstructionmanageDao extends BaseMapper<Constructionmanage> {
	public List<Constructionmanage> getConstructionManageList();
	public List<TaskProgressData> getTaskProgress();
	public List<SumByQuarterEnt> sumByQuarter(int TaskId);
}

