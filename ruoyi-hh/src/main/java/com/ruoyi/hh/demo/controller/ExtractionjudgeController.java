package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Extractionjudge;
import com.ruoyi.hh.demo.service.ExtractionjudgeService;
import com.ruoyi.hh.demo.service.StripeinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Extractionjudge)表控制层
 *
 * @author Eleven
 * @since 2022-06-13 17:07:02
 */
@RestController
@RequestMapping("extractionjudge")
@Api(value="条带抽采信息Controller",tags={"（进程评判）条带抽采信息相关接口 2022-06-13"})
public class ExtractionjudgeController{
    /**
     * 服务对象
     */
    @Resource
    private ExtractionjudgeService extractionjudgeService;
    @Resource
    private StripeinfoService stripeinfoService;

    /**
     * 分页查询所有数据
     *
     * @param index 页码下标（从0开始）
     * @param size  每页的数量
     * @return 分页结果
     */
    @PostMapping("selectAll")
    public AjaxResult selectAll(int index, int size) {
        Page<Extractionjudge> page = new Page<Extractionjudge>(index, size);
        return AjaxResult.success(this.extractionjudgeService.page(page, new QueryWrapper<>()));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.extractionjudgeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param extractionjudge 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody Extractionjudge extractionjudge) {
        return AjaxResult.success(this.extractionjudgeService.save(extractionjudge));
    }

    /**
     * 修改数据
     *
     * @param extractionjudge 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    public AjaxResult update(@RequestBody Extractionjudge extractionjudge) {
        return AjaxResult.success(this.extractionjudgeService.updateById(extractionjudge));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.extractionjudgeService.removeByIds(idList));
    }
    /**
     * 获取条带信息列表
     * @return
     */
    @ApiOperation(value="获取所有条带信息")
    @GetMapping("getStripeinfoList")
    public AjaxResult getStripeinfoList() {
    	return AjaxResult.success(this.stripeinfoService.list());
    }
    
    /**
     * 进程评判-抽采信息列表
     * @return
     */
    @GetMapping("getExtractionjudgeList")
    @ApiOperation(value="获取所有条带对应的抽采信息")
    public AjaxResult getExtractionjudgeList(){
    	return AjaxResult.success(this.extractionjudgeService.getExtractionjudgeList());
    }
    /**
     * 根据条带编号查询抽采信息
     * @param StripeCode
     * @return
     */
    @PostMapping("getExtractionjudgeByStripeCode")
    @ApiOperation(value="根据条带编码查询对应的瓦斯抽采信息")
    public AjaxResult getExtractionjudgeByStripeCode(String StripeCode) {
    	return AjaxResult.success(this.extractionjudgeService.getExtractionjudgeByStripeCode(StripeCode));
    }
    
    
}

