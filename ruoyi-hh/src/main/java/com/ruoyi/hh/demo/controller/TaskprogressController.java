package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Taskprogress;
import com.ruoyi.hh.demo.service.TaskprogressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Taskprogress)表控制层
 *
 * @author Eleven
 * @since 2022-06-07 17:08:33
 */
@RestController
@RequestMapping("taskprogress")
public class TaskprogressController {
    /**
     * 服务对象
     */
    @Resource
    private TaskprogressService taskprogressService;

    /**
     * 分页查询所有数据
     *
     * @param page         分页对象
     * @param taskprogress 查询实体
     * @return 所有数据
     */
    @GetMapping
    public AjaxResult selectAll(Page<Taskprogress> page, Taskprogress taskprogress) {
        return AjaxResult.success(this.taskprogressService.page(page, new QueryWrapper<>(taskprogress)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.taskprogressService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param taskprogress 实体对象
     * @return 新增结果
     */
    @PostMapping
    public AjaxResult insert(@RequestBody Taskprogress taskprogress) {
        return AjaxResult.success(this.taskprogressService.save(taskprogress));
    }

    /**
     * 修改数据
     *
     * @param taskprogress 实体对象
     * @return 修改结果
     */
    @PutMapping
    public AjaxResult update(@RequestBody Taskprogress taskprogress) {
        return AjaxResult.success(this.taskprogressService.updateById(taskprogress));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.taskprogressService.removeByIds(idList));
    }
}

