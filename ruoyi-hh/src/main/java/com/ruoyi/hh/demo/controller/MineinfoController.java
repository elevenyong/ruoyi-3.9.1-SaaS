package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Mineinfo;
import com.ruoyi.hh.demo.service.MineinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Mineinfo)表控制层
 *
 * @author Eleven
 * @since 2022-05-25 16:48:03
 */
@RestController
@RequestMapping("mineinfo")
@Api(value="矿井信息Controller",tags={"矿井信息相关接口"})
public class MineinfoController{
    /**
     * 服务对象
     */
    @Resource
    private MineinfoService mineinfoService;

    /**
     * 分页查询所有数据
     *
     * @param page     分页对象
     * @param mineinfo 查询实体
     * @return 所有数据
     */
    @GetMapping
    
    public AjaxResult selectAll(Page<Mineinfo> page, Mineinfo mineinfo) {
        return AjaxResult.success (this.mineinfoService.page(page, new QueryWrapper<>(mineinfo)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success (this.mineinfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param mineinfo 实体对象
     * @return 新增结果
     */
    @PostMapping
    
    public AjaxResult insert(@RequestBody Mineinfo mineinfo) {
        return AjaxResult.success (this.mineinfoService.save(mineinfo));
    }

    /**
     * 修改数据
     *
     * @param mineinfo 实体对象
     * @return 修改结果
     */
    @PutMapping
    
    public AjaxResult update(@RequestBody Mineinfo mineinfo) {
        return AjaxResult.success (this.mineinfoService.updateById(mineinfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success (this.mineinfoService.removeByIds(idList));
    }
    /**
     * 获取矿井基本信息
     * @param index
     * @param size
     * @return
     */
    @GetMapping("getMineinfoList")
    @ApiOperation("获取所有矿井基本信息，参数index为页面下标，size为单个页面的个数")
    @ApiImplicitParams({
    	@ApiImplicitParam(name="index",value="分页页面下标，从0开始"),
    	@ApiImplicitParam(name="size",value="单个页面的个数")
    })
    public AjaxResult getMineinfoList(int index,int size) {
    	Page<Mineinfo> page=new Page<>(index,size);
    	return AjaxResult.success(this.mineinfoService.page(page, null));
    }
    
    
   
}

