package com.ruoyi.hh.demo.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.hh.demo.domain.Gascontentanalyze;

/**
 * (Gascontentanalyze)表数据库访问层
 *
 * @author Eleven
 * @since 2022-06-10 16:36:23
 */
public interface GascontentanalyzeDao extends BaseMapper<Gascontentanalyze> {
	public List<Gascontentanalyze> getGasContent();

}

