package com.ruoyi.hh.hhhz.controller.drilling;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.param.TParamHighLevelDrilling;
import com.ruoyi.hh.hhhz.service.ITParamHighLevelDrillingService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 高位钻孔Controller
 *
 * @author hhhz
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/drillingParam/highPosition")
public class TParamHighLevelDrillingController extends BaseController {
    @Resource
    private ITParamHighLevelDrillingService tParamHighLevelDrillingService;
    @Resource
    private ITDesignedBoreholeService itDesignedBoreholeService;

    /**
     * 查询高位钻孔列表
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:list')")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody TParamHighLevelDrilling tParamHighLevelDrilling) {
        startPage();
        List<TParamHighLevelDrilling> list = tParamHighLevelDrillingService.selectTParamHighLevelDrillingList(tParamHighLevelDrilling);
//        for(TParamHighLevelDrilling tphld:list){
//            TDesignedBorehole tDesignedBorehole=new TDesignedBorehole();
//            tDesignedBorehole.setParamhighlevelid(tphld.getId());
//            List<TDesignedBorehole> tDesignedBoreholes=itDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
//            tphld.settDesignedBoreholes(tDesignedBoreholes);
//        }
        return getDataTable(list);
    }

    /**
     * 导出高位钻孔列表
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:export')")
    @Log(title = "高位钻孔", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TParamHighLevelDrilling tParamHighLevelDrilling) {
        List<TParamHighLevelDrilling> list = tParamHighLevelDrillingService.selectTParamHighLevelDrillingList(tParamHighLevelDrilling);
        ExcelUtil<TParamHighLevelDrilling> util = new ExcelUtil<TParamHighLevelDrilling>(TParamHighLevelDrilling.class);
        util.exportExcel(response, list, "高位钻孔数据");
    }

    /**
     * 获取高位钻孔详细信息
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tParamHighLevelDrillingService.selectTParamHighLevelDrillingById(id));
    }

    /**
     * 新增高位钻孔
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:add')")
    @Log(title = "高位钻孔", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TParamHighLevelDrilling tParamHighLevelDrilling) {
        return toAjax(tParamHighLevelDrillingService.insertTParamHighLevelDrilling(tParamHighLevelDrilling));
    }

    /**
     * 修改高位钻孔
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:edit')")
    @Log(title = "高位钻孔", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TParamHighLevelDrilling tParamHighLevelDrilling) {
        return toAjax(tParamHighLevelDrillingService.updateTParamHighLevelDrilling(tParamHighLevelDrilling));
    }

    /**
     * 删除高位钻孔
     */
    //@PreAuthorize("@ss.hasPermi('system:drilling:remove')")
    @Log(title = "高位钻孔", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tParamHighLevelDrillingService.deleteTParamHighLevelDrillingByIds(ids));
    }
}
