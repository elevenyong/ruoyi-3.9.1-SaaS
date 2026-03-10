package com.ruoyi.hh.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.hh.demo.domain.Extractionjudge;

/**
 * (Extractionjudge)表数据库访问层
 *
 * @author Eleven
 * @since 2022-06-13 17:07:02
 */
public interface ExtractionjudgeDao extends BaseMapper<Extractionjudge> {
	public List<Extractionjudge> getExtractionjudgeList();
	public List<Extractionjudge> getExtractionjudgeByStripeCode(String StripeCode);

}

