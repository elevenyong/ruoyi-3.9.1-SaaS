package com.ruoyi.hh.hhhz.controller.drilling;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.param.TParamBandDrilling;
import com.ruoyi.hh.hhhz.service.ITParamBandDrillingService;
import com.ruoyi.hh.physical.controller.TDesignedBoreholeController;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 钻孔设计历史记录Controller
 *
 * @author hhhz
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/drillingParam/band")
public class TParamBandDrillingController extends BaseController {
    @Resource
    private ITParamBandDrillingService tParamBandDrillingService;

    /**
     * 查询钻孔设计历史记录列表
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:list')")
    @PostMapping("list")
    public TableDataInfo list(@RequestBody TParamBandDrilling tParamBandDrilling) {
        startPage();
        List<TParamBandDrilling> list = tParamBandDrillingService.selectTParamBandDrillingList(tParamBandDrilling);
//        for(TParamBandDrilling tbd:list){
//            TDesignedBorehole tDesignedBorehole=new TDesignedBorehole();
//            tDesignedBorehole.setParambandid(tbd.getId());
//            List<TDesignedBorehole> tDesignedBoreholes=itDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
//            tbd.settDesignedBoreholes(tDesignedBoreholes);
//        }
        return getDataTable(list);
    }

    /**
     * 导出钻孔设计历史记录列表
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:export')")
    @Log(title = "钻孔设计历史记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TParamBandDrilling tParamBandDrilling) {
        List<TParamBandDrilling> list = tParamBandDrillingService.selectTParamBandDrillingList(tParamBandDrilling);
        ExcelUtil<TParamBandDrilling> util = new ExcelUtil<TParamBandDrilling>(TParamBandDrilling.class);
        util.exportExcel(response, list, "钻孔设计历史记录数据");
    }

    /**
     * 获取钻孔设计历史记录详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tParamBandDrillingService.selectTParamBandDrillingById(id));
    }

    /**
     * 新增钻孔设计历史记录
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:add')")
    @Log(title = "钻孔设计历史记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TParamBandDrilling tParamBandDrilling) {
        return toAjax(tParamBandDrillingService.insertTParamBandDrilling(tParamBandDrilling));
    }

    /**
     * 修改钻孔设计历史记录
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:edit')")
    @Log(title = "钻孔设计历史记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TParamBandDrilling tParamBandDrilling) {
        return toAjax(tParamBandDrillingService.updateTParamBandDrilling(tParamBandDrilling));
    }

    /**
     * 删除钻孔设计历史记录
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:remove')")
    @Log(title = "钻孔设计历史记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tParamBandDrillingService.deleteTParamBandDrillingByIds(ids));
    }
}
