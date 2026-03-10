package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Gasoverview;
import com.ruoyi.hh.demo.service.GasoverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * (Gasoverview)表控制层
 *
 * @author Eleven
 * @since 2022-05-25 16:48:03
 */
@RestController
@RequestMapping("gasoverview")
@Api(value="瓦斯总预览Controller",tags={"瓦斯总量信息相关接口"})
public class GasoverviewController {
    /**
     * 服务对象
     */
    @Resource
    private GasoverviewService gasoverviewService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param gasoverview 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiIgnore
    public AjaxResult selectAll(Page<Gasoverview> page, Gasoverview gasoverview) {
        return AjaxResult.success (this.gasoverviewService.page(page, new QueryWrapper<>(gasoverview)));
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
        return AjaxResult.success (this.gasoverviewService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param gasoverview 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiIgnore
    public AjaxResult insert(@RequestBody Gasoverview gasoverview) {
        return AjaxResult.success (this.gasoverviewService.save(gasoverview));
    }

    /**
     * 修改数据
     *
     * @param gasoverview 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiIgnore
    public AjaxResult update(@RequestBody Gasoverview gasoverview) {
        return AjaxResult.success (this.gasoverviewService.updateById(gasoverview));
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
        return AjaxResult.success (this.gasoverviewService.removeByIds(idList));
    }
    /**
     * 获取瓦斯总量信息
     * @return
     */
    @GetMapping("getGasOverviewInfo")
    @ApiOperation("获取瓦斯总量信息")
    public AjaxResult getGasOverview() {
    	List<Gasoverview> list=new ArrayList<Gasoverview>();
    	list=this.gasoverviewService.list();
    	return AjaxResult.success (list);
//    	return list;
    }
}

