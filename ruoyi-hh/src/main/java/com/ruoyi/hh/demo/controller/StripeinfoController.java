package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Stripeinfo;
import com.ruoyi.hh.demo.service.StripeinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Stripeinfo)表控制层
 *
 * @author Eleven
 * @since 2022-06-13 17:07:01
 */
@RestController
@RequestMapping("stripeinfo")
public class StripeinfoController {
    /**
     * 服务对象
     */
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
        Page<Stripeinfo> page = new Page<Stripeinfo>(index, size);
        return AjaxResult.success(this.stripeinfoService.page(page, new QueryWrapper<>()));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.stripeinfoService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param stripeinfo 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody Stripeinfo stripeinfo) {
        return AjaxResult.success(this.stripeinfoService.save(stripeinfo));
    }

    /**
     * 修改数据
     *
     * @param stripeinfo 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    public AjaxResult update(@RequestBody Stripeinfo stripeinfo) {
        return AjaxResult.success(this.stripeinfoService.updateById(stripeinfo));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.stripeinfoService.removeByIds(idList));
    }
    
}

