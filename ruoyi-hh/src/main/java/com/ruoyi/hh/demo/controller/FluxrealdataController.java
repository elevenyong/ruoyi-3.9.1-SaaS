package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Fluxrealdata;
import com.ruoyi.hh.demo.service.FluxrealdataService;
import io.swagger.annotations.Api;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Fluxrealdata)表控制层
 *
 * @author Eleven
 * @since 2022-05-25 16:48:02
 */
@RestController
@RequestMapping("fluxrealdata")
@Api(value="月统计瓦斯抽采Controller",tags={"月统计瓦斯抽采相关接口"})
public class FluxrealdataController {
    /**
     * 服务对象
     */
    @Resource
    private FluxrealdataService fluxrealdataService;

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param fluxrealdata 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResult selectAll(Page<Fluxrealdata> page, Fluxrealdata fluxrealdata) {
        return AjaxResult.success(this.fluxrealdataService.page(page, new QueryWrapper<>(fluxrealdata)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.fluxrealdataService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param fluxrealdata 实体对象
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult insert(@RequestBody Fluxrealdata fluxrealdata) {
        return AjaxResult.success(this.fluxrealdataService.save(fluxrealdata));
    }

    /**
     * 修改数据
     *
     * @param fluxrealdata 实体对象
     * @return 修改结果
     */
    @PutMapping
    public AjaxResult update(@RequestBody Fluxrealdata fluxrealdata) {
        return AjaxResult.success(this.fluxrealdataService.updateById(fluxrealdata));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.fluxrealdataService.removeByIds(idList));
    }
}

