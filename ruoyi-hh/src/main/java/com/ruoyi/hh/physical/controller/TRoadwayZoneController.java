package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TRoadwayZone;
import com.ruoyi.hh.physical.service.ITRoadwayZoneService;
import com.ruoyi.hh.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * (TRoadwayZone)表控制层
 *
 * @author Eleven
 * @since 2022-07-14 16:30:21
 */
@RestController
@RequestMapping("tRoadwayZone")
public class TRoadwayZoneController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITRoadwayZoneService tRoadwayZoneService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tRoadwayZone 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
//    @PreAuthorize("@ss.hasPermi('tRoadwayZone:list')")
    public TableDataInfo selectAll(@RequestBody TRoadwayZone tRoadwayZone) {
        startPage();
        List<TRoadwayZone> list = this.tRoadwayZoneService.selectTRoadwayZoneList(tRoadwayZone);
        return getDataTable(list);
    }

    @PostMapping("getPageList")
    public TableDataInfo list() {
        startPage();
        List<TRoadwayZone> list = new ArrayList<>();
        list=this.tRoadwayZoneService.list();
        return getDataTable(list);
    }


    /**
     * 插入单条数据
     *
     * @param tRoadwayZone 单挑数据
     */
    @PostMapping("insert")
//	@PreAuthorize("@ss.hasPermi('tRoadwayZone:insert')")
    public AjaxResult insertTRoadwayZone(@RequestBody TRoadwayZone tRoadwayZone) {
        Result result = this.tRoadwayZoneService.insertTRoadwayZone(tRoadwayZone);
        if (result.getStatus()== 0) {
            return AjaxResult.success(result.getMessage());
        } else {
            return AjaxResult.error(result.getMessage());
        }
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
//    @PreAuthorize("@ss.hasPermi('tRoadwayZone:insert')")
    public AjaxResult insert(@RequestBody List<TRoadwayZone> list) {
        int result = this.tRoadwayZoneService.insertTRoadwayZoneList(list);
        if (result > 0) {
            return AjaxResult.success("插入" + result + "条数据");
        } else {
            return AjaxResult.error("插入失败");
        }
    }


    /**
     * 修改
     *
     * @param tRoadwayZone 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTRoadwayZone")
//	@PreAuthorize("@ss.hasPermi('tRoadwayZone:updateTRoadwayZone')")
    public AjaxResult updateTRoadway(@RequestBody TRoadwayZone tRoadwayZone) {
        Result result=this.tRoadwayZoneService.updateTRoadwayZone(tRoadwayZone);
        if (result.getStatus()== 0) {
            return AjaxResult.success(result.getMessage());
        } else {
            return AjaxResult.error(result.getMessage());
        }
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
//    @PreAuthorize("@ss.hasPermi('tRoadwayZone:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tRoadwayZoneService.selectTRoadwayZoneById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTRoadwayZoneById")
//	@PreAuthorize("@ss.hasPermi('tRoadwayZone:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tRoadwayZoneService.deleteTRoadwayZoneById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTRoadwayZoneByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tRoadwayZoneService.deleteTRoadwayZoneByIds(ids));
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TRoadwayZone tRoadwayZone) {
        List<TRoadwayZone> list=this.tRoadwayZoneService.selectTRoadwayZoneList(tRoadwayZone);
        ExcelUtil<TRoadwayZone> util = new ExcelUtil<TRoadwayZone>(TRoadwayZone.class);
        util.exportExcel(response, list, "巷道区段信息数据");
    }

    @PostMapping("exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        TRoadwayZone tRoadwayZone=new TRoadwayZone();
        tRoadwayZone.setRoadway("模板数据（巷道名称不能重复）");
        tRoadwayZone.setZoneName("模板数据（同巷道下不能有相同区段）");
        tRoadwayZone.setX(88.8);
        tRoadwayZone.setY(88.8);
        tRoadwayZone.setZ(88.8);
        tRoadwayZone.setLength(88.8);
        tRoadwayZone.setZoneFinalElevation(88.8);
        tRoadwayZone.setAzimuthAngle(66.6);
        tRoadwayZone.setInclinationAngle(66.6);
        List<TRoadwayZone> list=new ArrayList<>();
        list.add(tRoadwayZone);
        ExcelUtil<TRoadwayZone> util = new ExcelUtil<TRoadwayZone>(TRoadwayZone.class);
        util.exportExcel(response, list, "巷道区段信息数据");
    }



    /**
     * excel导入巷道信息
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TRoadwayZone> util = new ExcelUtil<TRoadwayZone>(TRoadwayZone.class);
        List<TRoadwayZone> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TRoadwayZone tRoadwayZone : list) {
            BeanValidators.validateWithException(validator, tRoadwayZone);
            Result r = this.tRoadwayZoneService.insertTRoadwayZone(tRoadwayZone);
            if (r.getStatus() == 0) {
                successNum++;
            } else {
                failureNum++;
            }
        }
        return AjaxResult.success("添加成功" + successNum + "条," + failureNum + "条失败");
    }


}

