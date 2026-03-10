package com.ruoyi.hh.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.Gascontentanalyze;

/**
 * (Gascontentanalyze)表服务接口
 *
 * @author Eleven
 * @since 2022-06-10 16:36:23
 */
public interface GascontentanalyzeService extends IService<Gascontentanalyze> {
	public List<Gascontentanalyze> getGasContent();
}

