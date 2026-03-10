package com.ruoyi.hh.physical.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.Punchingledger;
import com.ruoyi.hh.physical.domain.TActualFlushing;
import com.ruoyi.hh.physical.service.ITActualFlushingService;
import com.ruoyi.hh.simulate.domain.FlushingStandingBook;
import com.ruoyi.hh.simulate.domain.FlushingStatistics;
import com.ruoyi.hh.simulate.domain.StatisticsBook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TActualFlushing)表控制层
 *
 * @author Eleven
 * @since 2022-07-19 09:47:08
 */
@RestController
@RequestMapping("tActualFlushing")
public class TActualFlushingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITActualFlushingService tActualFlushingService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tActualFlushing 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @Log(title = "水力冲孔", businessType = BusinessType.QUERY)
    //@PreAuthorize("@ss.hasPermi('tActualFlushing:list')")
    public TableDataInfo selectAll(@RequestBody TActualFlushing tActualFlushing) {
        startPage();
        List<TActualFlushing> list=this.tActualFlushingService.selectTActualFlushingList(tActualFlushing);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tActualFlushing 单挑数据
     */
    @PostMapping("insert")
    @Log(title = "水力冲孔", businessType = BusinessType.INSERT)
    //@PreAuthorize("@ss.hasPermi('tActualFlushing:insert')")
    public AjaxResult insertTActualFlushing(@RequestBody TActualFlushing tActualFlushing) {
        int res = tActualFlushingService.insertTActualFlushing(tActualFlushing);
//        if (res > 0) {
//            return AjaxResult.success("添加成功");
//        } else {
//            return AjaxResult.error("孔号重复");
//        }
        if(res>0){
            return AjaxResult.success("添加成功");
        }else{
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @Log(title = "水力冲孔", businessType = BusinessType.INSERT)
    // @PreAuthorize("@ss.hasPermi('tActualFlushing:insert')")
    public AjaxResult insert(@RequestBody List<TActualFlushing> list) {
        int sucnum = 0;
        int errnum = 0;
        if (list.size() > 0) {
            for (TActualFlushing tActualFlushing : list) {
                int res = this.tActualFlushingService.insertTActualFlushing(tActualFlushing);
                if (res > 0) {
                    sucnum++;
                } else {
                    errnum++;
                }
            }
            if (errnum == 0) {
                return AjaxResult.success("成功添加" + sucnum + "条数据");
            } else {
                return AjaxResult.error("添加失败" + errnum + "条，成功" + sucnum + "条");
            }
        }
        return AjaxResult.error("不能添加空数据");
        //this.tActualFlushingService.insertTActualFlushingList(list);
    }


    /**
     * 修改
     *
     * @param tActualFlushing 要修改的实体信息
     *                        更新信息时不更新对应的钻孔ID
     * @return 结果
     */
    @PutMapping("updateTActualFlushing")
    @Log(title = "水力冲孔", businessType = BusinessType.UPDATE)
    //@PreAuthorize("@ss.hasPermi('tActualFlushing:updateTActualFlushing')")
    public AjaxResult updateTRoadway(@RequestBody TActualFlushing tActualFlushing) {
        int result = this.tActualFlushingService.updateTActualFlushing(tActualFlushing);
        if(result>0){
            return AjaxResult.success("修改成功");
        }else{
            return AjaxResult.error("修改失败");
        }
        /* 修改孔号不能重复 int result = this.tActualFlushingService.updateTActualFlushing(tActualFlushing);
        if(result==2){
            return AjaxResult.error("已有数据格式不正确，更改的孔号重复");
        }else if(result==-2){
            return AjaxResult.error("孔号重复");
        }return AjaxResult.success("更新成功");*/
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @Log(title = "水力冲孔", businessType = BusinessType.QUERY)
    //@PreAuthorize("@ss.hasPermi('tActualFlushing:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tActualFlushingService.selectTActualFlushingById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTActualFlushingById")
    @Log(title = "水力冲孔", businessType = BusinessType.DELETE)
    //@PreAuthorize("@ss.hasPermi('tActualFlushing:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tActualFlushingService.deleteTActualFlushingById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTActualFlushingByIds")
    @Log(title = "水力冲孔", businessType = BusinessType.DELETE)
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tActualFlushingService.deleteTActualFlushingByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    @Log(title = "水力冲孔", businessType = BusinessType.QUERY)
    public TableDataInfo getPageList() {
        startPage();
        List<TActualFlushing> list =this.tActualFlushingService.getAndBroeholeInfo();
        return getDataTable(list);
    }

    /**
     * 按照冲孔时间和巷道信息查询水力冲孔台账信息
     *
     * @param roadwayId 巷道ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 列表
     */
    @PostMapping("getFlushingStandingBook")
    @Log(title = "水力冲孔台账", businessType = BusinessType.QUERY)
    public TableDataInfo getFlushingStandingBook(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                                 @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                                 @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                                 @RequestParam(required = false, value = "startDate") Date startDate,
                                                 @RequestParam(required = false, value = "endDate") Date endDate) throws ParseException {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<FlushingStandingBook> list=this.tActualFlushingService.getFlushingStandingBook(roadwayId,groupNo,boreholeNo, startDate, endDate);
        return getDataTable(list);
    }

    /**
     * 导出excel 水力冲孔台账信息
     * @param response res
     */
    @PostMapping("exportFlushingStandingBook")
    @Log(title = "水力冲孔", businessType = BusinessType.EXPORT)
    public void exportFlushingStandingBook(HttpServletResponse response, @RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                           @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                           @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                           @RequestParam(required = false, value = "startDate") Date startDate,
                                           @RequestParam(required = false, value = "endDate") Date endDate) {
        if (endDate == null && startDate != null) {
            endDate = new Date();
        }
        List<FlushingStandingBook> list=this.tActualFlushingService.getFlushingStandingBook(roadwayId,groupNo,boreholeNo, startDate, endDate);
        ExcelUtil<FlushingStandingBook> util = new ExcelUtil<>(FlushingStandingBook.class);
        util.exportExcel(response, list, "水力冲孔台账信息");
    }

    /**
     * 根据钻孔时间和巷道ID统计冲煤量
     *
     * @param roadwayId 巷道ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 列表
     * @throws ParseException 异常信息
     */
    @PostMapping("getFlushingStatistics")
    @Log(title = "冲孔煤量统计", businessType = BusinessType.QUERY)
    public TableDataInfo getFlushingStatistics(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                               @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                               @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                               @RequestParam(required = false, value = "startDate") Date startDate,
                                               @RequestParam(required = false, value = "endDate") Date endDate) throws ParseException {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<FlushingStatistics> list = this.tActualFlushingService.getFlushingStatistics(roadwayId,groupNo,boreholeNo, startDate, endDate);
        return getDataTable(list);
    }

    /**
     * 导出excel 冲孔煤量完成情况
     * @param response res
     */
    @PostMapping("exportFlushingStatistics")
    @Log(title = "冲孔煤量统计", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                       @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                       @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                       @RequestParam(required = false, value = "startDate") Date startDate,
                       @RequestParam(required = false, value = "endDate") Date endDate) {
        if (endDate == null && startDate != null) {
            endDate = new Date();
        }
        List<FlushingStatistics> list=this.tActualFlushingService.getFlushingStatistics(roadwayId,groupNo,boreholeNo, startDate, endDate);
        ExcelUtil<FlushingStatistics> util = new ExcelUtil<FlushingStatistics>(FlushingStatistics.class);
        util.exportExcel(response, list, "冲孔煤量完成情况");
    }

    /**
     * 根据冲孔时间和巷道ID查询列表
     *
     * @param roadwayId 巷道ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 列表
     * @throws ParseException 异常
     */
    @PostMapping("getFlushingInfoAndOther")
    @Log(title = "水力冲孔", businessType = BusinessType.QUERY)
    public TableDataInfo getFlushingInfoAndOther(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                                 @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                                 @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                                 @RequestParam(required = false, value = "startDate") Date startDate,
                                                 @RequestParam(required = false, value = "endDate") Date endDate) throws ParseException {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<TActualFlushing> list = this.tActualFlushingService.getFlushingInfoAndOther(roadwayId,groupNo,boreholeNo, startDate, endDate);
        return getDataTable(list);
    }


    /**
     * 根据孔号和验收时间查询列表（冲孔验收单）
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号ID
     * @param boreholeNo 孔号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return 实体
     * @throws ParseException 异常信息
     */
    @PostMapping("getAcceptBills")
    @Log(title = "水力冲孔验收单", businessType = BusinessType.QUERY)
    public TableDataInfo getAcceptBills(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                        @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                        @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                        @RequestParam(required = false, value = "startDate") Date startDate,
                                        @RequestParam(required = false, value = "endDate") Date endDate) throws ParseException {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<TActualFlushing> list=this.tActualFlushingService.getAcceptBills(roadwayId, groupNo, boreholeNo, startDate, endDate);
        return getDataTable(list);
    }

    /**
     *  水力冲孔统计台账
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号ID
     * @param boreholeNo 孔号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return 实体
     * @throws ParseException 异常信息
     */
    @PostMapping("getStatisticsBook")
    @Log(title = "水力冲孔统计台账", businessType = BusinessType.QUERY)
    public TableDataInfo getStatisticsBook(@RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                           @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                           @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                           @RequestParam(required = false, value = "startDate") Date startDate,
                                           @RequestParam(required = false, value = "endDate") Date endDate) throws ParseException {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<StatisticsBook> list=this.tActualFlushingService.getStatisticsBook(roadwayId, groupNo, boreholeNo, startDate, endDate);
        return getDataTable(list);
    }

    /**
     * 导出excel 水力冲孔统计台账
     * @param response res
     */
    @PostMapping("exportStatisticsBook")
    @Log(title = "水力冲孔统计台账", businessType = BusinessType.EXPORT)
    public void exportStatisticsBook(HttpServletResponse response, @RequestParam(required = false, value = "roadwayId") List<Integer> roadwayId,
                                     @RequestParam(required = false, value = "groupNo") List<String> groupNo,
                                     @RequestParam(required = false, value = "boreholeNo") List<Integer> boreholeNo,
                                     @RequestParam(required = false, value = "startDate") Date startDate,
                                     @RequestParam(required = false, value = "endDate") Date endDate) {
        if (endDate == null && startDate != null) {
            endDate = new Date();
        }
        List<StatisticsBook> list=this.tActualFlushingService.getStatisticsBook(roadwayId,groupNo,boreholeNo, startDate, endDate);
        ExcelUtil<StatisticsBook> util = new ExcelUtil<StatisticsBook>(StatisticsBook.class);
        util.exportExcel(response, list, "冲孔煤量完成情况");
    }




    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @Log(title = "水力冲孔", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestBody TActualFlushing tActualFlushing) {
        List<TActualFlushing> list=this.tActualFlushingService.selectTActualFlushingList(tActualFlushing);
        ExcelUtil<TActualFlushing> util = new ExcelUtil<>(TActualFlushing.class);
        util.exportExcel(response, list, "水力冲孔数据");
    }

    @PostMapping("PunchingledgerForExcel")
    public void PunchingledgerForExcel(HttpServletResponse response){
        List<Punchingledger> list=this.tActualFlushingService.PunchingledgerForExcel();
        ExcelUtil<Punchingledger> util = new ExcelUtil<>(Punchingledger.class);
        util.exportExcel(response, list, "水力冲孔台帐","瓦斯抽采水力冲孔施工台帐");
    }

    @PostMapping("exportTemplate")
    public void ex(HttpServletResponse response) {
        List<TActualFlushing> list = new ArrayList<>();
        TActualFlushing tActualFlushing=new TActualFlushing();
        tActualFlushing.setRoadway("模板数据");
        tActualFlushing.setGroupNo("201");
        tActualFlushing.setBoreholeNo(8);
        tActualFlushing.setShiftinfo("早班");
        tActualFlushing.setDateFlushing(new Date());
        tActualFlushing.setTimeBeginFlushing(new Date());
        tActualFlushing.setTimeEndFlushing(new Date());
        tActualFlushing.setTotalTimeFlushing(120.0);
        tActualFlushing.setWaterPressureDuringFlushing(23.1);
        tActualFlushing.setGasCONCBeforeFlushing(23.0);
        tActualFlushing.setGasCONCAfterFlushing(20.1);
        tActualFlushing.setPunchingParagraph1(16.6);
        tActualFlushing.setPunchingParagraph2(18.8);
        tActualFlushing.setSeeCoalCase("见煤情况");
        tActualFlushing.setSeeRockCase("见岩情况");
        tActualFlushing.setActualCoalAmountFlushed(3.3);
        tActualFlushing.setCoalDensity(1.5);
        tActualFlushing.setCompletionRatioFlushedCoal(90.1);
        tActualFlushing.setPersonConstruction("施工人员");
        tActualFlushing.setChargehand("冲孔负责人");
        tActualFlushing.setReceptionTime(new Date());
        tActualFlushing.setGasChangeDuring("2%~1.6%");
        tActualFlushing.setExceptionPhenomena("冲孔过程中异常标注");
        tActualFlushing.setPersonChecking("验收人员");
        tActualFlushing.setOtherPerson("其他队干");
        list.add(tActualFlushing);
        ExcelUtil<TActualFlushing> util = new ExcelUtil<>(TActualFlushing.class);
        util.exportExcel(response, list, "水力冲孔模板数据");
    }


    @PostMapping("/importData")
    @Log(title = "水力冲孔", businessType = BusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TActualFlushing> util = new ExcelUtil<TActualFlushing>(TActualFlushing.class);
        List<TActualFlushing> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TActualFlushing tActualFlushing : list) {
            BeanValidators.validateWithException(validator, tActualFlushing);
            int res = this.tActualFlushingService.insertTActualFlushing(tActualFlushing);
            if (res > 0) {
                successNum++;
            }
        }
        return AjaxResult.success("添加" + successNum + "条");
    }

}

