package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Analogpoint;
import com.ruoyi.hh.demo.service.AnalogpointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Analogpoint)表控制层
 *
 * @author Eleven
 * @since 2022-05-17 14:58:28
 */
@RestController
@RequestMapping("analogpoint")
@Api(value="传感器点位数据Controller",tags={"传感器点位相关接口"})
public class AnalogpointController{
    /**
     * 服务对象
     */
    @Resource
    private AnalogpointService analogpointService;

    /**
     * 分页查询所有数据
     *
     * @param page        分页对象
     * @param analogpoint 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiIgnore
    public AjaxResult selectAll(Page<Analogpoint> page, Analogpoint analogpoint) {
        return AjaxResult.success(this.analogpointService.page(page, new QueryWrapper<>(analogpoint)));
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
        return AjaxResult.success(this.analogpointService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param analogpoint 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiIgnore
    public AjaxResult insert(@RequestBody Analogpoint analogpoint) {
        return AjaxResult.success(this.analogpointService.save(analogpoint));
    }

    /**
     * 修改数据
     *
     * @param analogpoint 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiIgnore
    public AjaxResult update(@RequestBody Analogpoint analogpoint) {
        return AjaxResult.success(this.analogpointService.updateById(analogpoint));
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
        return AjaxResult.success(this.analogpointService.removeByIds(idList));
    }

    /**
     * 获取所有传感器点位列表信息
     * @return
     */
    @GetMapping("getAnalogPointList")
    @ApiOperation("获取所有传感器的点位信息，参数index为页面下标，size为单个页面的个数")
    public AjaxResult getAnalogPointList(int index,int size){
        Page<Analogpoint> page=new Page<>(index,size);
        return AjaxResult.success(this.analogpointService.page(page,null));
    }
}

