package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TSensorInfo;
import com.ruoyi.hh.business.service.ITSensorInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TSensorInfo)表控制层
 *
 * @author Eleven
 * @since 2022-07-26 16:40:40
 */
@RestController
@RequestMapping("tSensorInfo")
public class TSensorInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITSensorInfoService tSensorInfoService;

    /**
     * 获取列表
     *
     * @param tSensorInfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tSensorInfo:list')")
    public TableDataInfo selectAll(@RequestBody TSensorInfo tSensorInfo) {
        startPage();
        List<TSensorInfo> list = this.tSensorInfoService.selectTSensorInfoList(tSensorInfo);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tSensorInfo 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tSensorInfo:insert')")
    public void insertTSensorInfo(@RequestBody TSensorInfo tSensorInfo) {
        this.tSensorInfoService.insertTSensorInfo(tSensorInfo);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tSensorInfo:insert')")
    public void insert(@RequestBody List<TSensorInfo> list) {
        if (list.size() > 0) {
            for (TSensorInfo tSensorInfo : list) {
                this.tSensorInfoService.insertTSensorInfo(tSensorInfo);
            }
        }
        //this.tSensorInfoService.insertTSensorInfoList(list);
    }


    /**
     * 修改
     *
     * @param tSensorInfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTSensorInfo")
    //@PreAuthorize("@ss.hasPermi('tSensorInfo:updateTSensorInfo')")
    public AjaxResult updateTRoadway(@RequestBody TSensorInfo tSensorInfo) {
        return AjaxResult.success(this.tSensorInfoService.updateTSensorInfo(tSensorInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tSensorInfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tSensorInfoService.selectTSensorInfoById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTSensorInfoById")
    //@PreAuthorize("@ss.hasPermi('tSensorInfo:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tSensorInfoService.deleteTSensorInfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTSensorInfoByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tSensorInfoService.deleteTSensorInfoByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TSensorInfo> list = new ArrayList<>();
               list= this.tSensorInfoService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TSensorInfo tSensorInfo) {
        List<TSensorInfo> list=this.tSensorInfoService.selectTSensorInfoList(tSensorInfo);
        ExcelUtil<TSensorInfo> util = new ExcelUtil<TSensorInfo>(TSensorInfo.class);
        util.exportExcel(response, list, "工作面数据");
    }


}

