package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Drillholeinfo;
import com.ruoyi.hh.demo.service.DrillholeinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Drillholeinfo)表控制层
 *
 * @author Eleven
 * @since 2022-05-17 14:58:28
 */
@RestController
@RequestMapping("drillholeinfo")
@Api(value="钻孔数据Controller",tags={"钻孔数据相关接口"})
public class DrillholeinfoController {
    /**
     * 服务对象
     */
    @Resource
    private DrillholeinfoService drillholeinfoService;

    /**
     * 分页查询所有数据
     *
     * @param page          分页对象
     * @param drillholeinfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiIgnore
    public AjaxResult selectAll(Page<Drillholeinfo> page, Drillholeinfo drillholeinfo) {
        return AjaxResult.success(this.drillholeinfoService.page(page, new QueryWrapper<>(drillholeinfo)));
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
        return AjaxResult.success(this.drillholeinfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param drillholeinfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiIgnore
    public AjaxResult insert(@RequestBody Drillholeinfo drillholeinfo) {
        return AjaxResult.success(this.drillholeinfoService.save(drillholeinfo));
    }

    /**
     * 修改数据
     *
     * @param drillholeinfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiIgnore
    public AjaxResult update(@RequestBody Drillholeinfo drillholeinfo) {
        return AjaxResult.success(this.drillholeinfoService.updateById(drillholeinfo));
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
        return AjaxResult.success(this.drillholeinfoService.removeByIds(idList));
    }

    /**
     * 获取所有钻孔信息列表信息
     * @return 返回所有钻孔信息
     */
    @GetMapping("getDrillHoleInfoList")
    @ApiOperation("获取钻孔信息列表，参数index为页面下标，size为单页面内容的个数")
    public AjaxResult getDrillHoleInfoList (int index,int size){
        Page<Drillholeinfo> page=new Page<Drillholeinfo>(index,size);
        return AjaxResult.success(this.drillholeinfoService.page(page,null));
    }
    
    /**
     * 根据钻孔编号获取钻孔信息
     * @return 返回所有钻孔信息
     */
    @GetMapping("getDrillHoleInfoByNo")
    @ApiOperation("获取钻孔信息列表，参数drillsiteno为钻孔编号")
    public AjaxResult getDrillHoleInfoByNo (String holeno){
    	QueryWrapper<Drillholeinfo> queryWrapper=new QueryWrapper<>();
    	queryWrapper.eq("holeno", holeno);
        return AjaxResult.success(this.drillholeinfoService.getOne(queryWrapper));
    }


}

