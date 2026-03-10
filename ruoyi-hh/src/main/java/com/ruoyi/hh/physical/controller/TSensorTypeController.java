package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TSensorType;
import com.ruoyi.hh.physical.service.ITSensorTypeService;
import com.ruoyi.hh.util.Enum.SensorTypeEnum;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TSensorType)表控制层
 *
 * @author Eleven
 * @since 2022-07-26 16:40:40
 */
@RestController
@RequestMapping("tSensorType")
public class TSensorTypeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITSensorTypeService tSensorTypeService;

    /**
     * 获取列表
     *
     * @param tSensorType 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tSensorType:list')")
    public TableDataInfo selectAll(@RequestBody TSensorType tSensorType) {
        startPage();
        List<TSensorType> list = this.tSensorTypeService.selectTSensorTypeList(tSensorType);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tSensorType 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tSensorType:insert')")
    public void insertTSensorType(@RequestBody TSensorType tSensorType) {
        this.tSensorTypeService.insertTSensorType(tSensorType);
    }


    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tSensorType:insert')")
    public void insert(@RequestBody List<TSensorType> list) {
        if (list.size() > 0) {
            for (TSensorType tSensorType : list) {
                this.tSensorTypeService.insertTSensorType(tSensorType);
            }
        }
        //this.tSensorTypeService.insertTSensorTypeList(list);
    }




    /**
     * 修改
     *
     * @param tSensorType 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTSensorType")
    //@PreAuthorize("@ss.hasPermi('tSensorType:updateTSensorType')")
    public AjaxResult updateTRoadway(@RequestBody TSensorType tSensorType) {
        return AjaxResult.success(this.tSensorTypeService.updateTSensorType(tSensorType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tSensorType:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tSensorTypeService.selectTSensorTypeById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTSensorTypeById")
    //@PreAuthorize("@ss.hasPermi('tSensorType:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tSensorTypeService.deleteTSensorTypeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTSensorTypeByIds")
    public AjaxResult delete(@RequestParam  Integer[] ids) {
        return AjaxResult.success(this.tSensorTypeService.deleteTSensorTypeByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TSensorType tSensorType) {
        List<TSensorType> list=this.tSensorTypeService.selectTSensorTypeList(tSensorType);
        ExcelUtil<TSensorType> util = new ExcelUtil<TSensorType>(TSensorType.class);
        util.exportExcel(response, list, "传感器类型");
    }

    @GetMapping("insertvalue")
    public void insertvalue(){
            for(SensorTypeEnum sensorTypeEnum:SensorTypeEnum.values()){
                TSensorType tSensorType=new TSensorType();
                tSensorType.setCode(sensorTypeEnum.code);
                tSensorType.setDescribe(sensorTypeEnum.value);
                tSensorTypeService.insertTSensorType(tSensorType);
                System.out.println(sensorTypeEnum.code+":"+sensorTypeEnum.value);
            }

    }



}

