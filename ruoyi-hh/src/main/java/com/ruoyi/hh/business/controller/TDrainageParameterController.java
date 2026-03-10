package com.ruoyi.hh.business.controller;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TDrainageParameter;
import com.ruoyi.hh.business.service.ITDrainageParameterService;
import com.ruoyi.hh.physical.service.ITBkActBoreholeInfoService;
import com.ruoyi.hh.physical.service.ITRoadwayService;
import com.ruoyi.hh.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * (TDrainageParameter)表控制层
 *
 * @author Eleven
 * @since 2022-07-30 17:52:13
 */
@RestController
@RequestMapping("tDrainageParameter")
public class TDrainageParameterController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDrainageParameterService tDrainageParameterService;
    @Resource
    private ITBkActBoreholeInfoService itBkActBoreholeInfoService;
    @Resource
    private Validator validator;
    @Resource
    private ITRoadwayService itRoadwayService;

    /**
     * 获取列表
     *
     * @param tDrainageParameter 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.QUERY)
    //@PreAuthorize("@ss.hasPermi('tDrainageParameter:list')")
    public TableDataInfo selectAll(@RequestBody TDrainageParameter tDrainageParameter) {
        startPage();
        List<TDrainageParameter> list = this.tDrainageParameterService.selectTDrainageParameterList(tDrainageParameter);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDrainageParameter 单条数据
     */
    @PostMapping("insert")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.INSERT)
    //@PreAuthorize("@ss.hasPermi('tDrainageParameter:insert')")
    public AjaxResult insertTDrainageParameter(@RequestBody TDrainageParameter tDrainageParameter) {
        return AjaxResult.success(this.tDrainageParameterService.insertTDrainageParameter(tDrainageParameter).getMessage());
    }




    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.INSERT)
    // @PreAuthorize("@ss.hasPermi('tDrainageParameter:insert')")
    public AjaxResult insert(@RequestBody List<TDrainageParameter> list) {
        int suc = 0;
        int err = 0;
        if (list.size() > 0) {
            for (TDrainageParameter tDrainageParameter : list) {
                Result re = this.tDrainageParameterService.insertTDrainageParameter(tDrainageParameter);
                if (re.getStatus() == 0) {
                    suc++;
                } else {
                    err++;
                }
            }
        }
        return AjaxResult.success("添加成功" + suc + "条，失败" + err + "条");
        //this.tDrainageParameterService.insertTDrainageParameterList(list);
    }


    /**
     * 修改
     *
     * @param tDrainageParameter 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDrainageParameter")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.UPDATE)
    //@PreAuthorize("@ss.hasPermi('tDrainageParameter:updateTDrainageParameter')")
    public AjaxResult updateTRoadway(@RequestBody TDrainageParameter tDrainageParameter) {
        return AjaxResult.success(this.tDrainageParameterService.updateTDrainageParameter(tDrainageParameter));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.QUERY)
    //@PreAuthorize("@ss.hasPermi('tDrainageParameter:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDrainageParameterService.selectTDrainageParameterById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDrainageParameterById")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.DELETE)
    //@PreAuthorize("@ss.hasPermi('tDrainageParameter:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tDrainageParameterService.deleteTDrainageParameterById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDrainageParameterByIds")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.DELETE)
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tDrainageParameterService.deleteTDrainageParameterByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.QUERY)
    public TableDataInfo getPageList() {
        startPage();
        List<TDrainageParameter> list = new ArrayList<>();
        list = this.tDrainageParameterService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestBody TDrainageParameter tDrainageParameter) {
        List<TDrainageParameter> list = this.tDrainageParameterService.selectTDrainageParameterList(tDrainageParameter);
        ExcelUtil<TDrainageParameter> util = new ExcelUtil<TDrainageParameter>(TDrainageParameter.class);
        util.exportExcel(response, list, "钻孔周期计量参数台帐");
    }

    /**
     * 根据巷道ID，测量日期查询钻孔抽采参数
     *
     * @param groupNo   巷道ID
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 列表
     */
    @PostMapping("getTDrainageParameter")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.QUERY)
    public TableDataInfo getTDrainageParameter(@RequestParam(value = "roadwayId", required = false) Integer roadwayId,
                                               @RequestParam(value = "groupNo", required = false) List<String> groupNo,
                                               @RequestParam(value = "purposeboreholeId", required = false) List<Integer> purposeboreholeId,
                                               @RequestParam(value = "boreholeNo", required = false) List<Integer> boreholeNo,
                                               @RequestParam(value = "startDate", required = false) Date startDate,
                                               @RequestParam(value = "endDate", required = false) Date endDate) throws ParseException {
        startPage();
        List<TDrainageParameter> list = new ArrayList<>();
        if (startDate == null && endDate == null) {
            list = this.tDrainageParameterService.getTDrainageParameter(roadwayId, groupNo, purposeboreholeId, boreholeNo, null, null);
        } else if (startDate != null && endDate == null) {
            list = this.tDrainageParameterService.getTDrainageParameter(roadwayId, groupNo, purposeboreholeId, boreholeNo, startDate, new Date());
        } else if (startDate != null && endDate != null) {
            list = this.tDrainageParameterService.getTDrainageParameter(roadwayId, groupNo, purposeboreholeId, boreholeNo, startDate, endDate);
        }
        return getDataTable(list);
    }

    /**
     * 导入数据
     *
     * @param file 路径
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("/importData")
    @Log(title = "钻孔抽采参数测量信息", businessType = BusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        int reNum = 0;
        ExcelUtil<TDrainageParameter> util = new ExcelUtil<TDrainageParameter>(TDrainageParameter.class);
        List<TDrainageParameter> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TDrainageParameter tDrainageParameter : list) {
            Integer boreholeid=this.itBkActBoreholeInfoService.getIdByRoadWayAndGroupIdBoreholeno(tDrainageParameter.getRoadway(),
                    tDrainageParameter.getGroupNo(),tDrainageParameter.getBoreholeNo());
            BeanValidators.validateWithException(validator, tDrainageParameter);
            tDrainageParameter.setBoreholeId(boreholeid);
//            tDrainageParameter.setBoreholeid(this.tActualBoreholeMapper.getIdByRoadWayAndGroupIdBoreholeno(tDrainageParameter.getRoadway(), tDrainageParameter.getGroupid(), tDrainageParameter.getBoreholeno()));
            Result r = this.tDrainageParameterService.insertTDrainageParameter(tDrainageParameter);
            if (r.getStatus() == 0) {
                successNum++;
            } else if (r.getStatus() == 1) {
                reNum++;
            } else {
                logger.debug(r.getMessage());
                failureNum++;
            }
        }
        return AjaxResult.success("操作成功" + successNum + "条," + reNum + "条已存在," + failureNum + "添加失败");
    }

    /***
     * 根据巷道ID，钻孔用途，组号，查询给定阶段的浓度
     * @param roadwayId 巷道ID
     * @param purposeboreholeId 钻孔用途ID
     * @param groupNo 组号ID
     * @param boreholeNo 孔号
     * @param low 小值
     * @param height 大值
     * @return 列表
     */
    @PostMapping("getTGasExtractionAnalyze")
    public TableDataInfo getTGasExtractionAnalyze(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                                  @RequestParam(required = false, value = "purposeboreholeId") List<Integer> purposeboreholeId,
                                                  @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                                  @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                                  @RequestParam(value = "low") Integer low,
                                                  @RequestParam(value = "height") Integer height) {
        startPage();
        return getDataTable(this.tDrainageParameterService.getTGasExtractionAnalyze(roadwayId, purposeboreholeId, groupNo, boreholeNo, low, height));

    }

    /**
     * 查询钻孔始抽效果分析
     *
     * @param roadwayId         巷道ID
     * @param purposeboreholeId 钻孔用途ID
     * @param groupNo           组号ID
     * @param boreholeNo        孔号
     * @param low               小值
     * @param height            大值
     * @return 列表
     */
    @PostMapping("getGasAnalyzeBegin")
    @Log(title = "钻孔始抽效果分析", businessType = BusinessType.QUERY)
    public TableDataInfo getGasAnalyzeBegin(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                            @RequestParam(required = false, value = "purposeboreholeId") List<Integer> purposeboreholeId,
                                            @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                            @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                            @RequestParam(value = "low") Integer low,
                                            @RequestParam(value = "height") Integer height) {
        startPage();
        return getDataTable(this.tDrainageParameterService.getGasAnalyzeBegin(roadwayId, purposeboreholeId, groupNo, boreholeNo, low, height));

    }

    /**
     * 查询未录入钻孔抽采参数测量的钻孔
     *
     * @return 列表
     */
    @PostMapping("getBoreholeInfoNotRecord")
    public TableDataInfo getBoreholeInfoNotRecord() {
        startPage();
        return getDataTable(this.tDrainageParameterService.getBoreholeInfoNotRecord());
    }

    /**
     * 导出excel模版
     *
     * @param response res
     */
    @PostMapping("exportTemplate")
    public void exportTemplate(HttpServletResponse response) throws Exception {
        TDrainageParameter tDrainageParameter = new TDrainageParameter();
        List<String> values=new ArrayList<>();
        values.add("是");
        values.add("否");
        setBeginningcon("beginningcon",values);
        tDrainageParameter.setRoadway("已有巷道名称");
        tDrainageParameter.setGroupNo("8");
        tDrainageParameter.setBoreholeNo("88");
        tDrainageParameter.setBoreholeCode("8-88");
        tDrainageParameter.setDrillingLocation("钻孔位置");
        tDrainageParameter.setMeasureDate(new Date());
        tDrainageParameter.setGasConcentration(88.8);
        tDrainageParameter.setNegativePressure(66.6);
//        tDrainageParameter.setBeginningcon("1");
        tDrainageParameter.setMeasurer("测量人");
        tDrainageParameter.setTabulator("制表人");
        TDrainageParameter tDrainageParameter1 = new TDrainageParameter();
        tDrainageParameter1.setRoadway("已有巷道名称");
        tDrainageParameter1.setGroupNo("8");
        tDrainageParameter1.setBoreholeNo("88");
        tDrainageParameter1.setBoreholeCode("8-88");
        tDrainageParameter1.setDrillingLocation("钻孔位置");
        tDrainageParameter1.setMeasureDate(new Date());
        tDrainageParameter1.setGasConcentration(88.6);
        tDrainageParameter1.setNegativePressure(66.0);
        tDrainageParameter1.setMeasurer("测量人");
        tDrainageParameter1.setTabulator("制表人");
        List<TDrainageParameter> list = new ArrayList<>();
        list.add(tDrainageParameter);
        list.add(tDrainageParameter1);
        ExcelUtil<TDrainageParameter> util = new ExcelUtil<TDrainageParameter>(TDrainageParameter.class);
        util.exportExcel(response, list, "钻孔抽采参数测量模版");
    }

    private void setBeginningcon(String param,List<String> list)throws Exception{
        // 通过反射 获取目标实体类的属性成员
        Field file= TDrainageParameter.class.getDeclaredField(param);
        // 获取该字段的上叫Excel的注解
        Excel annotation=file.getAnnotation(Excel.class);
        InvocationHandler h= Proxy.getInvocationHandler(annotation);
        Field hField=h.getClass().getDeclaredField("memberValues");
        // 设置私有可访问
        hField.setAccessible(true);
        Map memberValues=(Map)hField.get(h);
        // 集合转数组
        String[]generalizedList=list.toArray(new String[list.size()]);
        // 修改属性值
        memberValues.put("combo",generalizedList);
    }
}

