package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Gascontentanalyze;
import com.ruoyi.hh.demo.service.GascontentanalyzeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Gascontentanalyze)表控制层
 *
 * @author Eleven
 * @since 2022-06-10 16:36:23
 */
@RestController
@RequestMapping("gascontentanalyze")
@Api(value="瓦斯量分布Controller",tags={"（防突信息）瓦斯量分布信息相关接口 2022-06-09"})
public class GascontentanalyzeController{
    /**
     * 服务对象
     */
    @Resource
    private GascontentanalyzeService gascontentanalyzeService;

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
        Page<Gascontentanalyze> page = new Page<Gascontentanalyze>(index, size);
        return AjaxResult.success(this.gascontentanalyzeService.page(page, new QueryWrapper<>()));
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
        return AjaxResult.success(this.gascontentanalyzeService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param gascontentanalyze 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    @ApiIgnore
    public AjaxResult insert(@RequestBody Gascontentanalyze gascontentanalyze) {
        return AjaxResult.success(this.gascontentanalyzeService.save(gascontentanalyze));
    }

    /**
     * 修改数据
     *
     * @param gascontentanalyze 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    @ApiIgnore
    public AjaxResult update(@RequestBody Gascontentanalyze gascontentanalyze) {
        return AjaxResult.success(this.gascontentanalyzeService.updateById(gascontentanalyze));
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
        return AjaxResult.success(this.gascontentanalyzeService.removeByIds(idList));
    }
    
    /**
     * 获取瓦斯含量分布
     * @return
     */
    @GetMapping("getGasContent")
    @ApiOperation("瓦斯含量饼状图")
    public AjaxResult getGasContent() {
    	return AjaxResult.success(this.gascontentanalyzeService.getGasContent());
    }
}

