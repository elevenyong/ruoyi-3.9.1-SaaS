package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Constructionmanage;
import com.ruoyi.hh.demo.domain.SumByQuarterEnt;
import com.ruoyi.hh.demo.domain.TaskProgressData;
import com.ruoyi.hh.demo.domain.Taskprogress;
import com.ruoyi.hh.demo.service.ConstructionmanageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (Constructionmanage)表控制层
 *
 * @author Eleven
 * @since 2022-06-07 17:08:32
 */
@RestController
@RequestMapping("constructionmanage")
@Api(value="施工管理Controller",tags={"（工程集控-施工管理）施工管理数据相关接口  2022-06-07"})
public class ConstructionmanageController {
    /**
     * 服务对象
     */
    @Resource
    private ConstructionmanageService constructionmanageService;
    @Resource
    private com.ruoyi.hh.demo.service.TaskprogressService TaskprogressService;

    /**
     * 分页查询所有数据
     *
     * @param page               分页对象
     * @param constructionmanage 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiIgnore
    public AjaxResult selectAll(Page<Constructionmanage> page, Constructionmanage constructionmanage) {
        return AjaxResult.success(this.constructionmanageService.page(page, new QueryWrapper<>(constructionmanage)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiIgnore
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.constructionmanageService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param constructionmanage 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiIgnore
    public AjaxResult insert(@RequestBody Constructionmanage constructionmanage) {
        return AjaxResult.success(this.constructionmanageService.save(constructionmanage));
   
    }
    @GetMapping("insert")
    public AjaxResult insertTaskProgress() {
    	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
    	for(int i=24;i<53;i++) {
    		Date nowDate=new Date();
    		Calendar calendar=Calendar.getInstance();
    		calendar.setTime(nowDate);
    		calendar.add(Calendar.DATE, i);
    		String  dString = sim.format(calendar.getTime());
    		//四、将String转化为date，须要注意java.sql.Date.valueOf()函数只能接受参数类型为yyyy-MM-dd类型的
    		Date data = java.sql.Date.valueOf(dString);
    		System.out.println(calendar.getTime());
    		Taskprogress taskprogress=new Taskprogress();
    		taskprogress.setTaskid(3);
    		taskprogress.setWorkdate(data);
    		double d=Math.random()/10;
    		taskprogress.setCompletepercentage(Double.parseDouble(String.format("%.2f", d)));
    		TaskprogressService.save(taskprogress);
    	}
    	  
    	return AjaxResult.success("");
    }

    /**
     * 修改数据
     *
     * @param constructionmanage 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiIgnore
    public AjaxResult update(@RequestBody Constructionmanage constructionmanage) {
        return AjaxResult.success(this.constructionmanageService.updateById(constructionmanage));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiIgnore
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.constructionmanageService.removeByIds(idList));
    }
    /**
     * 获取施工管理列表
     * @return
     */
    @GetMapping("getConstructionManageList")
    @ApiOperation("查询数据库中施工管理数据列表")
    public AjaxResult getConstructionManageList() {
    	return AjaxResult.success(this.constructionmanageService.list());
    }
    /**
     * 获取所有任务列表
     * @return
     */
    @GetMapping("getTaskProgress")
    @ApiOperation("根据时间查询施工进度")
    public AjaxResult getTaskProgress() {
    	List<TaskProgressData> list=this.constructionmanageService.getTaskProgress();
    	for(TaskProgressData taskProgressData:list) {
    		List<SumByQuarterEnt> sumByQuarterEnts=this.constructionmanageService.sumByQuarter(taskProgressData.getTaskId());
    		taskProgressData.setSumByQuarterEnts(sumByQuarterEnts);
    	}
    	return AjaxResult.success(list);
    }
    
    
    
}

