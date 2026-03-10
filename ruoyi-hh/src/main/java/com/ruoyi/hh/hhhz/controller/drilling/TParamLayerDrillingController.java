package com.ruoyi.hh.hhhz.controller.drilling;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.param.TParamLayerDrilling;
import com.ruoyi.hh.hhhz.service.ITParamLayerDrillingService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 顺层钻孔参数Controller
 *
 * @author hhhz
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/drillingParam/layer")
public class TParamLayerDrillingController extends BaseController {
    @Resource
    private ITParamLayerDrillingService tParamLayerDrillingService;
    @Resource
    private ITDesignedBoreholeService itDesignedBoreholeService;

    /**
     * 查询顺层钻孔参数列表
     */
    //@PreAuthorize("@ss.hasPermi('layer:drilling:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody TParamLayerDrilling tParamLayerDrilling) {
        startPage();
        List<TParamLayerDrilling> list = tParamLayerDrillingService.selectTParamLayerDrillingList(tParamLayerDrilling);
//        for(TParamLayerDrilling tpld:list){
//            TDesignedBorehole tDesignedBorehole=new TDesignedBorehole();
//            tDesignedBorehole.setParamlayerid(tpld.getId());
//            List<TDesignedBorehole> tDesignedBoreholes=itDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
//            tpld.settDesignedBoreholes(tDesignedBoreholes);
//        }
        return getDataTable(list);
    }

    /**
     * 导出顺层钻孔参数列表
     */
    //@PreAuthorize("@ss.hasPermi('layer:drilling:export')")
    @Log(title = "顺层钻孔参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TParamLayerDrilling tParamLayerDrilling) {
        List<TParamLayerDrilling> list = tParamLayerDrillingService.selectTParamLayerDrillingList(tParamLayerDrilling);
        ExcelUtil<TParamLayerDrilling> util = new ExcelUtil<TParamLayerDrilling>(TParamLayerDrilling.class);
        util.exportExcel(response, list, "顺层钻孔参数数据");
    }

    /**
     * 获取顺层钻孔参数详细信息
     */
    //@PreAuthorize("@ss.hasPermi('layer:drilling:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tParamLayerDrillingService.selectTParamLayerDrillingById(id));
    }

    /**
     * 新增顺层钻孔参数
     */
    //@PreAuthorize("@ss.hasPermi('layer:drilling:add')")
    @Log(title = "顺层钻孔参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TParamLayerDrilling tParamLayerDrilling) {
        return toAjax(tParamLayerDrillingService.insertTParamLayerDrilling(tParamLayerDrilling));
    }

    /**
     * 修改顺层钻孔参数
     */
    //@PreAuthorize("@ss.hasPermi('layer:drilling:edit')")
    @Log(title = "顺层钻孔参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TParamLayerDrilling tParamLayerDrilling) {
        return toAjax(tParamLayerDrillingService.updateTParamLayerDrilling(tParamLayerDrilling));
    }

    /**
     * 删除顺层钻孔参数
     */
    //@PreAuthorize("@ss.hasPermi('layer:drilling:remove')")
    @Log(title = "顺层钻孔参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tParamLayerDrillingService.deleteTParamLayerDrillingByIds(ids));
    }
}
