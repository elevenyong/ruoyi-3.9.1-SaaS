package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Sensortype;
import com.ruoyi.hh.demo.service.SensortypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Sensortype)表控制层
 *
 * @author Eleven
 * @since 2022-06-09 10:19:56
 */
@RestController
@RequestMapping("sensortype")
@Api(value="传感器数据类型 Controller",tags={"（防突信息）传感器类型数据相关接口  2022-06-09"})
public class SensortypeController {
    /**
     * 服务对象
     */
    @Resource
    private SensortypeService sensortypeService;

    /**
     * 分页查询所有数据
     *
     * @param index 页码下标（从0开始）
     * @param size  每页的数量
     * @return 分页结果
     */
    @PostMapping("selectAll")
    @ApiIgnore
    public AjaxResult selectAll(int index, int size) {
        Page<Sensortype> page = new Page<Sensortype>(index, size);
        return AjaxResult.success(this.sensortypeService.page(page, new QueryWrapper<>()));
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
        return AjaxResult.success(this.sensortypeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sensortype 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    @ApiIgnore
    public AjaxResult insert(@RequestBody Sensortype sensortype) {
        return AjaxResult.success(this.sensortypeService.save(sensortype));
    }

    /**
     * 修改数据
     *
     * @param sensortype 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    @ApiIgnore
    public AjaxResult update(@RequestBody Sensortype sensortype) {
        return AjaxResult.success(this.sensortypeService.updateById(sensortype));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping("delete")
    @ApiIgnore
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.sensortypeService.removeByIds(idList));
    }
    /**
     * 获取传感器数据类型
     * @return
     */
    @GetMapping("getSensorTypeData")
    @ApiOperation("查询传感器类型数据")
    public AjaxResult getSensorTypeData() {
    	return AjaxResult.success(this.sensortypeService.list() );
    }
}

