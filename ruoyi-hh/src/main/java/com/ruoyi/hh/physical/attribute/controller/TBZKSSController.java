package com.ruoyi.hh.physical.attribute.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.hh.physical.attribute.domain.TBZKSS;
import com.ruoyi.hh.physical.attribute.service.ITBZKSSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 钻孔应力测点实时信息Controller
 * 
 * @author eleven
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/TBZKSS")
public class TBZKSSController extends BaseController
{
    @Resource
    private ITBZKSSService tBZKSSService;

    /**
     * 查询钻孔应力测点实时信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TBZKSS tBZKSS)
    {
        startPage();
        List<TBZKSS> list = tBZKSSService.selectTBZKSSList(tBZKSS);
        return getDataTable(list);
    }

    /**
     * 导出钻孔应力测点实时信息列表
     */
    @Log(title = "钻孔应力测点实时信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TBZKSS tBZKSS)
    {
        List<TBZKSS> list = tBZKSSService.selectTBZKSSList(tBZKSS);
        ExcelUtil<TBZKSS> util = new ExcelUtil<TBZKSS>(TBZKSS.class);
        util.exportExcel(response, list, "钻孔应力测点实时信息数据");
    }

    /**
     * 获取钻孔应力测点实时信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(tBZKSSService.selectTBZKSSById(id).toString());
    }

    /**
     * 新增钻孔应力测点实时信息
     */
    @Log(title = "钻孔应力测点实时信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TBZKSS tBZKSS)
    {
        return toAjax(tBZKSSService.insertTBZKSS(tBZKSS));
    }

    /**
     * 修改钻孔应力测点实时信息
     */
    @Log(title = "钻孔应力测点实时信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TBZKSS tBZKSS)
    {
        return toAjax(tBZKSSService.updateTBZKSS(tBZKSS));
    }

    /**
     * 删除钻孔应力测点实时信息
     */
    @Log(title = "钻孔应力测点实时信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(tBZKSSService.deleteTBZKSSByIds(ids));
    }
}
