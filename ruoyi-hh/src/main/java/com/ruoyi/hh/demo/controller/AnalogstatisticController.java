package com.ruoyi.hh.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Analogstatistic;
import com.ruoyi.hh.demo.mapper.AnalogstatisticDao;
import com.ruoyi.hh.demo.service.impl.AnalogstatisticServiceImpl;
import com.ruoyi.hh.demo.utils.ResultMSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Validated
@RestController
@RequestMapping("Analogstatistic")
public class AnalogstatisticController {
    @Autowired
    public AnalogstatisticServiceImpl analogstatisticService;
    @Resource
    public AnalogstatisticDao analogstatisticMapper;
    @Autowired
    private ResultMSG resultMSG;

    @GetMapping("getAllAnalogstatistic")
    @ResponseBody
    public List<Analogstatistic> getAllAnalogstatistic() {
        return analogstatisticService.list();
    }

    @GetMapping("selectPage")
    @ResponseBody
    public List<Analogstatistic> selectPage(int index, int size) {
        IPage<Analogstatistic> page = new Page<>(index, size);
        analogstatisticMapper.selectPage(page, null);
        return page.getRecords();
    }

    @PutMapping("update")
    public ResultMSG update(@RequestBody Analogstatistic analogstatistic) {
        int r = analogstatisticMapper.updateById(analogstatistic);
        if (r > 0) {
            resultMSG.setDate(analogstatistic);
            resultMSG.setMSG("更新成功");
            resultMSG.setStatus(0);
            return resultMSG;
        } else {
            resultMSG.setDate("");
            resultMSG.setMSG("更新失败");
            resultMSG.setStatus(-1);
            return resultMSG;
        }
    }

    @PostMapping("add")
    public AjaxResult add(@RequestBody Analogstatistic analogstatistic) {
        int r = analogstatisticMapper.insert(analogstatistic);
        if (r > 0) {
            return AjaxResult.success(analogstatistic);
        } else {
            return AjaxResult.error("添加失败");
        }
    }

    @DeleteMapping("delById")
    public AjaxResult delById(@RequestParam int id) {
        int r = analogstatisticMapper.deleteById(id);
        if (r > 0) {
            return AjaxResult.success("删除成功");
        } else {
            return AjaxResult.error("删除失败");
        }
    }


}
