package com.ruoyi.hh.physical.attribute.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.hh.physical.attribute.domain.TBZKYL;
import com.ruoyi.hh.physical.attribute.service.ITBZKYLService;
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
 * 钻孔应力测点基本信息Controller
 * 
 * @author eleven
 * @date 2024-11-12
 */
@RestController
@RequestMapping("/TBZKYL")
public class TBZKYLController extends BaseController
{
    @Resource
    private ITBZKYLService tBZKYLService;

    /**
     * 查询钻孔应力测点基本信息列表
     */
    @GetMapping("/list")
    public TableDataInfo list(TBZKYL tBZKYL)
    {
        startPage();
        List<TBZKYL> list = tBZKYLService.selectTBZKYLList(tBZKYL);
        return getDataTable(list);
    }

    /**
     * 导出钻孔应力测点基本信息列表
     */
    @Log(title = "钻孔应力测点基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TBZKYL tBZKYL)
    {
        List<TBZKYL> list = tBZKYLService.selectTBZKYLList(tBZKYL);
        ExcelUtil<TBZKYL> util = new ExcelUtil<TBZKYL>(TBZKYL.class);
        util.exportExcel(response, list, "钻孔应力测点基本信息数据");
    }

    /**
     * 获取钻孔应力测点基本信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(tBZKYLService.selectTBZKYLById(id).toString());
    }

    /**
     * 新增钻孔应力测点基本信息
     */
    @Log(title = "钻孔应力测点基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TBZKYL tBZKYL)
    {
        return toAjax(tBZKYLService.insertTBZKYL(tBZKYL));
    }

    /**
     * 修改钻孔应力测点基本信息
     */
    @Log(title = "钻孔应力测点基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TBZKYL tBZKYL)
    {
        return toAjax(tBZKYLService.updateTBZKYL(tBZKYL));
    }

    /**
     * 删除钻孔应力测点基本信息
     */
    @Log(title = "钻孔应力测点基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(tBZKYLService.deleteTBZKYLByIds(ids));
    }
}
