package com.ruoyi.hh.demo.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.hh.demo.domain.Extractionjudge;

/**
 * (Extractionjudge)表服务接口
 *
 * @author Eleven
 * @since 2022-06-13 17:07:02
 */
public interface ExtractionjudgeService extends IService<Extractionjudge> {
	public List<Extractionjudge> getExtractionjudgeList();
	public List<Extractionjudge> getExtractionjudgeByStripeCode(String StripeCode);

}

