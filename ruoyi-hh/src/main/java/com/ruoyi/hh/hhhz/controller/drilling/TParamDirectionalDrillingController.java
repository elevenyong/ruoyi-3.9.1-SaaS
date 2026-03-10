package com.ruoyi.hh.hhhz.controller.drilling;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.param.TParamDirectionalDrilling;
import com.ruoyi.hh.hhhz.service.ITParamDirectionalDrillingService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 定向钻孔Controller
 *
 * @author hhhz
 * @date 2023-04-25
 */
@RestController
@RequestMapping("/drillingParam/directional")
public class TParamDirectionalDrillingController extends BaseController {
    @Resource
    private ITParamDirectionalDrillingService tParamDirectionalDrillingService;
    @Resource
    private ITDesignedBoreholeService itDesignedBoreholeService;
    /**
     * 查询定向钻孔列表
     */
    //@PreAuthorize("@ss.hasPermi('system:directional:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody TParamDirectionalDrilling tParamDirectionalDrilling) {
        startPage();
        List<TParamDirectionalDrilling> list = tParamDirectionalDrillingService.selectTParamDirectionalDrillingList(tParamDirectionalDrilling);
//        for(TParamDirectionalDrilling tpdd:list){
//            TDesignedBorehole tDesignedBorehole=new TDesignedBorehole();
//            tDesignedBorehole.setParamdirectionalid(tpdd.getId());
//            List<TDesignedBorehole> tDesignedBoreholes=itDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
//            tpdd.settDesignedBoreholes(tDesignedBoreholes);
//        }
        return getDataTable(list);
    }

    /**
     * 导出定向钻孔列表
     */
    //@PreAuthorize("@ss.hasPermi('system:directional:export')")
    @Log(title = "定向钻孔", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TParamDirectionalDrilling tParamDirectionalDrilling) {
        List<TParamDirectionalDrilling> list = tParamDirectionalDrillingService.selectTParamDirectionalDrillingList(tParamDirectionalDrilling);
        ExcelUtil<TParamDirectionalDrilling> util = new ExcelUtil<TParamDirectionalDrilling>(TParamDirectionalDrilling.class);
        util.exportExcel(response, list, "定向钻孔数据");
    }

    /**
     * 获取定向钻孔详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:directional:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tParamDirectionalDrillingService.selectTParamDirectionalDrillingById(id));
    }

    /**
     * 新增定向钻孔
     */
    //@PreAuthorize("@ss.hasPermi('system:directional:add')")
    @Log(title = "定向钻孔", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TParamDirectionalDrilling tParamDirectionalDrilling) {
        return toAjax(tParamDirectionalDrillingService.insertTParamDirectionalDrilling(tParamDirectionalDrilling));
    }

    /**
     * 修改定向钻孔
     */
    //@PreAuthorize("@ss.hasPermi('system:directional:edit')")
    @Log(title = "定向钻孔", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TParamDirectionalDrilling tParamDirectionalDrilling) {
        return toAjax(tParamDirectionalDrillingService.updateTParamDirectionalDrilling(tParamDirectionalDrilling));
    }

    /**
     * 删除定向钻孔
     */
    //@PreAuthorize("@ss.hasPermi('system:directional:remove')")
    @Log(title = "定向钻孔", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tParamDirectionalDrillingService.deleteTParamDirectionalDrillingByIds(ids));
    }
}
