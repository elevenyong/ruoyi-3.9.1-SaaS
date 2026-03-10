package com.ruoyi.hh.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import com.ruoyi.hh.demo.domain.Gascontentanalyze;
import com.ruoyi.hh.demo.mapper.GascontentanalyzeDao;
import com.ruoyi.hh.demo.service.GascontentanalyzeService;
import org.springframework.stereotype.Service;

/**
 * (Gascontentanalyze)表服务实现类
 *
 * @author Eleven
 * @since 2022-06-10 16:36:23
 */
@Service("gascontentanalyzeService")
public class GascontentanalyzeServiceImpl extends ServiceImpl<GascontentanalyzeDao, Gascontentanalyze> implements GascontentanalyzeService {

	
	@Resource
	private GascontentanalyzeDao gascontentanalyzeDao;
	@Override
	public List<Gascontentanalyze> getGasContent() {
		// TODO Auto-generated method stub
		return gascontentanalyzeDao.getGasContent();
	}

}

