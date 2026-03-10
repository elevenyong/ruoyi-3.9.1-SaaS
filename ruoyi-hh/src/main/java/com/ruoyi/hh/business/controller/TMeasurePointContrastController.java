package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TMeasurePointContrast;
import com.ruoyi.hh.business.service.ITMeasurePointContrastService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TMeasurePointContrast)表控制层
 *
 * @author Eleven
 * @since 2022-07-28 10:51:35
 */
@RestController
@RequestMapping("tMeasurePointContrast")
public class TMeasurePointContrastController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITMeasurePointContrastService tMeasurePointContrastService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tMeasurePointContrast 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tMeasurePointContrast:list')")
    public TableDataInfo selectAll(@RequestBody TMeasurePointContrast tMeasurePointContrast) {
        startPage();
        List<TMeasurePointContrast> list = this.tMeasurePointContrastService.selectTMeasurePointContrastList(tMeasurePointContrast);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tMeasurePointContrast 单条数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tMeasurePointContrast:insert')")
    public AjaxResult insertTMeasurePointContrast(@RequestBody TMeasurePointContrast tMeasurePointContrast) {
        int res=this.tMeasurePointContrastService.insertTMeasurePointContrast(tMeasurePointContrast);
        if(res>0){
            return AjaxResult.success("添加成功");
        }
        return AjaxResult.error("添加失败");
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tMeasurePointContrast:insert')")
    public AjaxResult insert(@RequestBody List<TMeasurePointContrast> list) {
        int suc=0;
        int err=0;
        if (list.size() > 0) {
            for (TMeasurePointContrast tMeasurePointContrast : list) {
                int res=this.tMeasurePointContrastService.insertTMeasurePointContrast(tMeasurePointContrast);
                if(res>0){
                    suc++;
                }else{
                    err++;
                }
            }
        }
        return AjaxResult.success("添加成功"+suc+"条，添加失败"+err+"条");
        //this.tMeasurePointContrastService.insertTMeasurePointContrastList(list);
    }


    /**
     * 修改
     *
     * @param tMeasurePointContrast 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTMeasurePointContrast")
    //@PreAuthorize("@ss.hasPermi('tMeasurePointContrast:updateTMeasurePointContrast')")
    public AjaxResult updateTRoadway(@RequestBody TMeasurePointContrast tMeasurePointContrast) {
        return AjaxResult.success(this.tMeasurePointContrastService.updateTMeasurePointContrast(tMeasurePointContrast));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tMeasurePointContrast:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tMeasurePointContrastService.selectTMeasurePointContrastById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTMeasurePointContrastById")
    //@PreAuthorize("@ss.hasPermi('tMeasurePointContrast:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tMeasurePointContrastService.deleteTMeasurePointContrastById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTMeasurePointContrastByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tMeasurePointContrastService.deleteTMeasurePointContrastByIds(ids));
    }

    /**
     * 通过测点ID查询人工测点数据
     * @param measurePointIds 测点列表
     * @return 结果
     */
    @PostMapping("getByMeasurePointId")
    public TableDataInfo getByMeasurePointId(@RequestParam(required =false,value="measurePointIds") List<Integer> measurePointIds){
        startPage();
        List<TMeasurePointContrast> list=this.tMeasurePointContrastService.getByMeasurePointId(measurePointIds);
        return getDataTable(list);
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TMeasurePointContrast> list = new ArrayList<>();
        list=this.tMeasurePointContrastService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TMeasurePointContrast tMeasurePointContrast) {
        List<TMeasurePointContrast> list=this.tMeasurePointContrastService.selectTMeasurePointContrastList(tMeasurePointContrast);
        ExcelUtil<TMeasurePointContrast> util = new ExcelUtil<TMeasurePointContrast>(TMeasurePointContrast.class);
        util.exportExcel(response, list, "人工测点对比数据");
    }

    /**
     * 导出模板
     *
     * @param response
     */
    @PostMapping("exportTemplate")
    public void exportTemplate(HttpServletResponse response) {
        TMeasurePointContrast tMeasurePointContrast =new TMeasurePointContrast();
        tMeasurePointContrast.setMeasurePointLocal("模板数据（已存在的测点名称）");
        tMeasurePointContrast.setMeasureTime(new Date());
        tMeasurePointContrast.setConcentration(88.8);
        tMeasurePointContrast.setConcentrationS(88.8);
        tMeasurePointContrast.setConcentrationRevise(88.8);
        tMeasurePointContrast.setVacuum(88.8);
        tMeasurePointContrast.setVacuumRevise(88.8);
        tMeasurePointContrast.setVacuumS(88.8);
        tMeasurePointContrast.setMixedFlow(88.8);
        tMeasurePointContrast.setMixedFlowRevise(88.8);
        tMeasurePointContrast.setMixedFlowS(88.8);
        tMeasurePointContrast.setPureFlow(88.8);
        tMeasurePointContrast.setPureFlowRevise(88.8);
        tMeasurePointContrast.setPureFlowS(88.8);
        tMeasurePointContrast.setTemperature(88.8);
        tMeasurePointContrast.setTemperatureRevise(88.8);
        tMeasurePointContrast.setTemperatureS(88.8);
        tMeasurePointContrast.setCOS(88.8);
        tMeasurePointContrast.setCORevise(88.8);
        tMeasurePointContrast.setCOPortable(88.8);
        tMeasurePointContrast.setBarometerValue(88.8);
        tMeasurePointContrast.setPressureRevise(88.8);
        tMeasurePointContrast.setPressureS(88.8);
        tMeasurePointContrast.setActualMeasurementPersion("王安全");
        tMeasurePointContrast.setTimingPersion("王平安");
        List<TMeasurePointContrast> list=new ArrayList<>();
        list.add(tMeasurePointContrast);
        ExcelUtil<TMeasurePointContrast> util = new ExcelUtil<TMeasurePointContrast>(TMeasurePointContrast.class);
        util.exportExcel(response, list, "人工测点对比数据");
    }

    /**
     * 导入数据
     *
     * @param file 路径
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TMeasurePointContrast> util = new ExcelUtil<>(TMeasurePointContrast.class);
        List<TMeasurePointContrast> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

//        for (TMeasurePointContrast tMeasurePointContrast : list) {
//            BeanValidators.validateWithException(validator, tMeasurePointContrast);
//            String name = tMeasurePointContrast.getMeasuringpointname().replaceAll("\\s*","");//去掉中间的制表符
//            if (!name.equals("")) {
//                Integer id = this.tMeasurePointContrastService.getIdByName(name);
//                if (id != null) {
//                    tMeasurePointContrast.setMeasurepointid(id);
//                    int res = this.tMeasurePointContrastService.insertTMeasurePointContrast(tMeasurePointContrast);
//                    if (res > 0) {
//                        successNum++;
//                    } else if (res == -1) {
//                        failureNum++;
//                    }
//                } else {
//                    return AjaxResult.error("导入的测点名称不正确");
//                }
//            }
//        }
        return AjaxResult.success("添加" + successNum + "条," + failureNum + "条已存在");
    }


}

