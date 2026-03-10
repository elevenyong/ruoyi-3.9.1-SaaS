package com.ruoyi.hh.business.controller;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TGasContentPressure;
import com.ruoyi.hh.business.service.ITGasContentPressureService;
import com.ruoyi.hh.physical.service.ITRoadwayService;
import com.ruoyi.hh.util.service.ITPurposeBoreholeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (TGasContentPressure)表控制层
 *
 * @author Eleven
 * @since 2022-07-27 10:41:19
 */
@RestController
@RequestMapping("tGasContentPressure")
public class TGasContentPressureController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITGasContentPressureService tGasContentPressureService;
    @Resource
    private ITRoadwayService itRoadwayService;
    @Resource
    private ITPurposeBoreholeService itPurposeBoreholeService;

    /**
     * 获取列表
     *
     * @param tGasContentPressure 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tGasContentPressure:list')")
    public TableDataInfo selectAll(@RequestBody TGasContentPressure tGasContentPressure) {
        startPage();
        List<TGasContentPressure> list = this.tGasContentPressureService.selectTGasContentPressureList(tGasContentPressure);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tGasContentPressure 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tGasContentPressure:insert')")
    public AjaxResult insertTGasContentPressure(@RequestBody TGasContentPressure tGasContentPressure) {
        int res=this.tGasContentPressureService.insertTGasContentPressure(tGasContentPressure);
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
    // @PreAuthorize("@ss.hasPermi('tGasContentPressure:insert')")
    public AjaxResult insert(@RequestBody List<TGasContentPressure> list) {
        int suc=0;
        int err=0;
        if (list.size() > 0) {
            for (TGasContentPressure tGasContentPressure : list) {
                int res=this.tGasContentPressureService.insertTGasContentPressure(tGasContentPressure);
                if(res>0){
                    suc++;
                }else {
                    err++;
                }
            }
        }
        return AjaxResult.success("添加成功"+suc+"条，失败"+err+"条");
    }


    /**
     * 修改
     *
     * @param tGasContentPressure 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTGasContentPressure")
    //@PreAuthorize("@ss.hasPermi('tGasContentPressure:updateTGasContentPressure')")
    public AjaxResult updateTRoadway(@RequestBody TGasContentPressure tGasContentPressure) {
        return AjaxResult.success(this.tGasContentPressureService.updateTGasContentPressure(tGasContentPressure));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tGasContentPressure:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tGasContentPressureService.selectTGasContentPressureById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTGasContentPressureById")
    //@PreAuthorize("@ss.hasPermi('tGasContentPressure:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tGasContentPressureService.deleteTGasContentPressureById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTGasContentPressureByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tGasContentPressureService.deleteTGasContentPressureByIds(ids));
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("exportTemplate")
    public void exportTemplate(HttpServletResponse response) throws Exception {
        List<TGasContentPressure> list=new ArrayList<>();
        TGasContentPressure tcp=new TGasContentPressure();
        tcp.setRoadway("已有巷道名称");
        tcp.setSamplingProt("取样点");
        tcp.setInclinationAngle(66.6);
        tcp.setAngle(66.6);
        tcp.setSamplingDepth(12.6);
        tcp.setGasContent(63.3);
        tcp.setGasPressure(-12.1);
        tcp.setMeasureDate(new Date());
        tcp.setMeasurer("测量人");
        List<String> purposeBoreholeList=this.itPurposeBoreholeService.getPurposeBoreholeAll();
        setPurposeBorehole("purposeBorehole",purposeBoreholeList);
        list.add(tcp);
        ExcelUtil<TGasContentPressure> util = new ExcelUtil<TGasContentPressure>(TGasContentPressure.class);
        util.exportExcel(response, list, "瓦斯含量压力台账");
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response,
                       @RequestParam(value="roadwayId",required =false) Integer roadwayId,
                       @RequestParam(value="purposeboreholeId",required =false)List<Integer> purposeboreholeId,
                       @RequestParam(value="startDate",required =false) Date startDate,
                       @RequestParam(value="endDate",required =false) Date endDate
                       ) {
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<TGasContentPressure> list=this.tGasContentPressureService.getData(roadwayId, purposeboreholeId,
                startDate,
                endDate);
        ExcelUtil<TGasContentPressure> util = new ExcelUtil<TGasContentPressure>(TGasContentPressure.class);
        util.exportExcel(response, list, "瓦斯含量压力台账");
    }


    /**
     * 根据巷道ID，组号，孔号，测定时间获取列表（瓦斯含量/压力台账）
     *
     * @param roadwayId  巷道ID
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return 列表
     */
    @PostMapping("getData")
    public TableDataInfo getData(@RequestParam(value="roadwayId",required =false) Integer roadwayId,
                              @RequestParam(value="purposeboreholeId",required =false)List<Integer> purposeboreholeId,
                              @RequestParam(value="startDate",required =false) Date startDate,
                              @RequestParam(value="endDate",required =false) Date endDate) {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<TGasContentPressure> list=this.tGasContentPressureService.getData(roadwayId, purposeboreholeId,
                startDate,
                endDate);
        return getDataTable(list);
    }

    @PostMapping("/importData")
    @Log(title = "瓦斯含量压力台帐", businessType = BusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TGasContentPressure> util = new ExcelUtil<TGasContentPressure>(TGasContentPressure.class);
        List<TGasContentPressure> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        for(TGasContentPressure tcp:list){
            Integer roadwayid=this.itRoadwayService.getIdByRoadWayName(tcp.getRoadway());
            Integer purposeboreholeId=this.itPurposeBoreholeService.getIdByPurposeBorehole(tcp.getPurposeBorehole());
            tcp.setRoadwayId(roadwayid);
            tcp.setPurposeBoreholeId(purposeboreholeId);
            Integer r=this.tGasContentPressureService.insertTGasContentPressure(tcp);
            if(r>0){
                successNum++;
            }else{
                failureNum++;
            }
        }
        return AjaxResult.success("成功导入"+successNum+"，失败"+failureNum);
    }

    private void setPurposeBorehole(String param,List<String> list)throws Exception{
        // 通过反射 获取目标实体类的属性成员
        Field file= TGasContentPressure.class.getDeclaredField(param);
        // 获取该字段的上叫Excel的注解
        Excel annotation=file.getAnnotation(Excel.class);
        InvocationHandler h= Proxy.getInvocationHandler(annotation);
        Field hField=h.getClass().getDeclaredField("memberValues");
        // 设置私有可访问
        hField.setAccessible(true);
        Map memberValues=(Map)hField.get(h);
        // 集合转数组
        String[]purposeboreholeList=list.toArray(new String[list.size()]);
        // 修改属性值
        memberValues.put("combo",purposeboreholeList);
    }

}

