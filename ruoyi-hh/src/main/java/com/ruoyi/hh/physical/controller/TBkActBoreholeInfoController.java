package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.service.*;
import com.ruoyi.hh.util.Result;
import com.ruoyi.hh.util.service.ITBoreholeCategoryService;
import com.ruoyi.hh.util.service.ITPurposeBoreholeService;
import com.ruoyi.hh.util.service.ITShiftinfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.arrow.flatbuf.Int;
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
 * 钻孔原始验收单信息(TBkActBoreholeInfo)表控制层
 *
 * @author makejava
 * @since 2023-05-27 11:56:33
 */
@Api(value = "DZZK实钻普钻接口", tags = "DZZK实钻普钻接口")
@RestController
@RequestMapping("tBkActBoreholeInfo")
public class TBkActBoreholeInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITBkActBoreholeInfoService tBkActBoreholeInfoService;
    @Resource
    private ITActDirectionalDrillingPointService itActDirectionalDrillingPointService;
    @Resource
    protected Validator validator;
    @Resource
    private ITRoadwayService itRoadwayService;
    @Resource
    private ITRoadwayZoneService itRoadwayZoneService;
    @Resource
    private ITBoreholeCategoryService itBoreholeCategoryService;
    @Resource
    private ITPurposeBoreholeService itPurposeBoreholeService;
    @Resource
    private ITShiftinfoService itShiftinfoService;
    @Resource
    private ITNavigationPointService itNavigationPointService;
    @Resource
    private ITDesignedBoreholeService itDesignedBoreholeService;
    @Resource
    private TBKActFlushingService tbkActFlushingService;

    /**
     * 获取列表
     *
     * @param tBkActBoreholeInfo 要查询的信息
     * @return 返回结果
     */
    @ApiOperation("按条件获取实钻钻孔列表")
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tBkActBoreholeInfo:list')")
    public TableDataInfo selectAll(@RequestBody TBkActBoreholeInfo tBkActBoreholeInfo) {
        startPage();
        List<TBkActBoreholeInfo> list = this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoList(tBkActBoreholeInfo);
//        for(TBkActBoreholeInfo tbab:list){
//            TActDirectionalDrillingPoint tActDirectionalDrillingPoint=new TActDirectionalDrillingPoint();
//            tActDirectionalDrillingPoint.setActBoreholeId(tbab.getId());
//            List<TActDirectionalDrillingPoint> list1=itActDirectionalDrillingPointService.selectTActDirectionalDrillingPointList(tActDirectionalDrillingPoint);
//            tbab.setTActDirectionalDrillingPoints(list1);
//        }
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tBkActBoreholeInfo 单条数据
     */
    @ApiOperation("新增实钻钻孔")
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tBkActBoreholeInfo:insert')")
    public AjaxResult insertTBkActBoreholeInfo(@RequestBody TBkActBoreholeInfo tBkActBoreholeInfo) {
        Result result = this.tBkActBoreholeInfoService.insertTBkActBoreholeInfo(tBkActBoreholeInfo);
//        Integer id=this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoList(tBkActBoreholeInfo).get(0).getId();
//        List<TActDirectionalDrillingPoint> list=tBkActBoreholeInfo.getTActDirectionalDrillingPoints();
//        if(list!=null){
//            for(TActDirectionalDrillingPoint tActDirectionalDrillingPoint:list){
//                tActDirectionalDrillingPoint.setActBoreholeId(id);
//                return AjaxResult.success(itActDirectionalDrillingPointService.insertTActDirectionalDrillingPoint(tActDirectionalDrillingPoint));
//            }
//        }
        if (result.getStatus() == 0) {
            return AjaxResult.success("添加成功");
        }
        return AjaxResult.error("添加失败");
    }

    /**
     * 导入，更新超前探数据列表
     *
     * @param roadwayId 巷道ID
     * @param list      超前探钻孔数据
     * @return 结果
     */
    @ApiOperation("导入，更新超前探数据列表")
    @PostMapping("insertAdvExpDrillList/{roadwayId}")
    public AjaxResult insertAdvanceBorehole(@PathVariable("roadwayId") Integer roadwayId, @RequestBody List<TBkActBoreholeInfo> list) {
        for (TBkActBoreholeInfo tBkActBoreholeInfo : list) {
            tBkActBoreholeInfo.setIsUsedCreateCoal(1);
        }
        return this.tBkActBoreholeInfoService.insertAdvanceExpDrillList(roadwayId, list);
    }


    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @ApiOperation("批量插入实钻钻孔数据")
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tBkActBoreholeInfo:insert')")
    public AjaxResult insert(@RequestBody List<TBkActBoreholeInfo> list) {
        if (list.size() > 0) {
            return this.tBkActBoreholeInfoService.insertBatchBkActBorehole(list);
            /*for(TBkActBoreholeInfo td:list){
                insertTBkActBoreholeInfo(td);
            }*/
        }
        return AjaxResult.error("导入数据为空");
    }


    /**
     * 修改
     *
     * @param tBkActBoreholeInfo 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("修改实钻钻孔数据")
    @PutMapping("updateTBkActBoreholeInfo")
    // @PreAuthorize("@ss.hasPermi('tBkActBoreholeInfo:updateTBkActBoreholeInfo')")
    public AjaxResult updateTBkActBoreholeInfo(@RequestBody TBkActBoreholeInfo tBkActBoreholeInfo) {
        int i = this.tBkActBoreholeInfoService.updateTBkActBoreholeInfo(tBkActBoreholeInfo);
        if (i>0){
            return AjaxResult.success(this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoById(tBkActBoreholeInfo.getId()));
        }else {
            return AjaxResult.error("更新失败，请检查后重试");
        }
        //return AjaxResult.success();
    }

    /**
     * 批量修改
     *
     * @param tBkActBoreholeInfoList 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("批量修改实钻钻孔数据")
    @PutMapping("updateTBkActBoreholeInfoList")
    public AjaxResult updateTBkActBoreholeInfo(@RequestBody List<TBkActBoreholeInfo> tBkActBoreholeInfoList) {
        int i = 0;
        ArrayList<Integer> ids = new ArrayList<>();
        for (TBkActBoreholeInfo tBkActBoreholeInfo : tBkActBoreholeInfoList) {
            ids.add(tBkActBoreholeInfo.getId());
            i += this.tBkActBoreholeInfoService.updateTBkActBoreholeInfo(tBkActBoreholeInfo);
        }

        ArrayList<TBkActBoreholeInfo> updatedHoles = new ArrayList<>();
        for (Integer id : ids) {
            TBkActBoreholeInfo boreholeInfo = this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoById(id);
            updatedHoles.add(boreholeInfo);
        }
        return AjaxResult.success(updatedHoles);
        //return AjaxResult.success("成功更新" + i + "条实钻数据");
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过实钻ID查询钻孔")
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tBkActBoreholeInfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoById(id));
    }


    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @ApiOperation("根据id删除单条实钻数据")
    @DeleteMapping("deleteTBkActBoreholeInfoById")
    // @PreAuthorize("@ss.hasPermi('tBkActBoreholeInfo:id')")
    public AjaxResult deleteTBkActBoreholeInfoById(@RequestParam int id) {
        int result = this.tBkActBoreholeInfoService.deleteTBkActBoreholeInfoById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @ApiOperation("批量删除实钻数据")
    @DeleteMapping("deleteTBkActBoreholeInfoByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tBkActBoreholeInfoService.deleteTBkActBoreholeInfoByIds(ids));
    }

    /**
     * 水力冲孔台帐
     *
     * @param roadwayId                巷道ID
     * @param groupNo                  组号ID
     * @param boreholeNo               孔号
     * @param actualcoalamountflushed1 冲孔煤量1
     * @param actualcoalamountflushed2 冲孔煤量2
     * @param terminalholedate1        终孔时间1
     * @param terminalholedate2        终孔时间2
     * @return 列表
     */
    @PostMapping("getTBKFlushingStandingBook")
    public TableDataInfo getTBKFlushingStandingBook(@RequestParam(value = "roadwayId", required = false) List<Integer> roadwayId,
                                                    @RequestParam(value = "groupNo", required = false) String groupNo,
                                                    @RequestParam(value = "boreholeNo", required = false) List<Integer> boreholeNo,
                                                    @RequestParam(value = "drillinglocation", required = false) List<String> drillinglocation,
                                                    @RequestParam(value = "actualcoalamountflushed1", required = false) Double actualcoalamountflushed1,
                                                    @RequestParam(value = "actualcoalamountflushed2", required = false) Double actualcoalamountflushed2,
                                                    @RequestParam(value = "terminalholedate1", required = false) Date terminalholedate1,
                                                    @RequestParam(value = "terminalholedate2", required = false) Date terminalholedate2) {
        startPage();

        List<TBKFlushingStandingBook> list = this.tBkActBoreholeInfoService.getTBKFlushingStandingBook(roadwayId, groupNo, drillinglocation, boreholeNo, actualcoalamountflushed1, actualcoalamountflushed2, terminalholedate1, terminalholedate2);
        return getDataTable(list);
    }

    /**
     * 导出水力冲孔excel
     *
     * @param response
     * @param roadwayId
     * @param groupNo
     * @param boreholeNo
     * @param actualcoalamountflushed1
     * @param actualcoalamountflushed2
     * @param terminalholedate1
     * @param terminalholedate2
     */
    @PostMapping("getTBKFlushingStandingBookExport")
    /*public void getTBKFlushingStandingBookExport(HttpServletResponse response, @RequestBody List<TBKFlushingStandingBook> list) {
        ExcelUtil<TBKFlushingStandingBook> util = new ExcelUtil<TBKFlushingStandingBook>(TBKFlushingStandingBook.class);
        util.exportExcel(response, list, "水力冲孔台帐");
    }*/
    public void getTBKFlushingStandingBookExport(HttpServletResponse response, @RequestParam(value = "roadwayId", required = false) List<Integer> roadwayId,
                                                 @RequestParam(value = "groupNo", required = false) String groupNo,
                                                 @RequestParam(value = "drillinglocation", required = false) List<String> drillinglocation,
                                                 @RequestParam(value = "boreholeNo", required = false) List<Integer> boreholeNo,
                                                 @RequestParam(value = "actualcoalamountflushed1", required = false) Double actualcoalamountflushed1,
                                                 @RequestParam(value = "actualcoalamountflushed2", required = false) Double actualcoalamountflushed2,
                                                 @RequestParam(value = "terminalholedate1", required = false) Date terminalholedate1,
                                                 @RequestParam(value = "terminalholedate2", required = false) Date terminalholedate2) {
        List<TBKFlushingStandingBook> list = this.tBkActBoreholeInfoService.getTBKFlushingStandingBook(roadwayId, groupNo, drillinglocation, boreholeNo, actualcoalamountflushed1, actualcoalamountflushed2, terminalholedate1, terminalholedate2);
        ExcelUtil<TBKFlushingStandingBook> util = new ExcelUtil<TBKFlushingStandingBook>(TBKFlushingStandingBook.class);
        util.exportExcel(response, list, "水力冲孔台帐", "瓦斯抽采水力冲孔施工台帐");
    }


    /**
     * @param response
     * @param tBkActBoreholeInfo
     * @CreateTime 2023/7/3 17:58
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TBkActBoreholeInfo tBkActBoreholeInfo) throws Exception {
        List<TBkActBoreholeInfo> list = this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoList(tBkActBoreholeInfo);
        List<String> roadways = this.itRoadwayService.getRoadwayAll();
        List<String> zonenames = this.itRoadwayZoneService.getZoneNameAll();
        List<String> boreholecategorys = this.itBoreholeCategoryService.getBoreholeCategoryAll();
        List<String> purposeboreholes = this.itPurposeBoreholeService.getPurposeBoreholeAll();
        List<String> shiftinfos = this.itShiftinfoService.getShiftInfoAll();
        List<String> navigationpoint = this.itNavigationPointService.getNavigationPointAll();
        setCombo("roadway", roadways);
        setCombo("zonename", zonenames);
        setCombo("boreholeCategory", boreholecategorys);
        setCombo("purposeBorehole", purposeboreholes);
        setCombo("shiftinfo", shiftinfos);
        setCombo("navigationPointName", navigationpoint);
        ExcelUtil<TBkActBoreholeInfo> util = new ExcelUtil<TBkActBoreholeInfo>(TBkActBoreholeInfo.class);
        util.exportExcel(response, list, "钻孔信息");
    }

    /**
     * 导出模版（根据八矿现场的实钻台帐）
     *
     * @param response
     * @CreateTime: 2023/7/6 10:45
     */
    @ApiOperation("导出实钻模版（根据八矿现场的实钻台帐）")
    @PostMapping("exportSimpleTemplate")
    public void exportSimpleTemplate(HttpServletResponse response) throws Exception {
        TBKActBoreholeSimple tbKActBoreholeSimple = new TBKActBoreholeSimple();
        tbKActBoreholeSimple.setRoadway("1508中抽巷");  //巷道
        tbKActBoreholeSimple.setZonename("已有的所属区段"); //区段
        tbKActBoreholeSimple.setBoreholeCode("B1-1");
        tbKActBoreholeSimple.setActBoreholeLength(23.0); //实际孔深
        tbKActBoreholeSimple.setDepth1stRockSeamStart(0.0);
        tbKActBoreholeSimple.setDepth1stRockSeamEnd(19.8);
        tbKActBoreholeSimple.setDepth1stCoalSeamStart(19.8);
        tbKActBoreholeSimple.setDepth1stCoalSeamEnd(23.0);
        tbKActBoreholeSimple.setDepth2stRockSeamStart(23.0);
        tbKActBoreholeSimple.setDepth2stRockSeamEnd(24.0);
        tbKActBoreholeSimple.setDepth2stCoalSeamStart(24.0);
        tbKActBoreholeSimple.setDepth2stCoalSeamEnd(26.1);
        tbKActBoreholeSimple.setDepth3stRockSeamStart(25.0);
        tbKActBoreholeSimple.setDepth3stRockSeamEnd(26.5);
        tbKActBoreholeSimple.setActualCoalAmountFlushed(5.3); //实际冲煤量
        tbKActBoreholeSimple.setSprayHoleCondition("钻孔喷孔情况"); //钻孔喷孔情况
        tbKActBoreholeSimple.setExceptionConstruction("施工异常情况"); //施工异常情况
        tbKActBoreholeSimple.setAcceptanceStatus("验收情况");  //验收情况
        tbKActBoreholeSimple.setActAperture(22.1);  //孔径（mm）
        tbKActBoreholeSimple.setActAzimuthAngle("186.0");  //水平角
        tbKActBoreholeSimple.setActAngle(66.1);  //实际角度
        tbKActBoreholeSimple.setDrillingLocation("钻孔位置");   //钻孔位置
        tbKActBoreholeSimple.setExceptionDuringDrilling("打钻过程中异常现象");
        tbKActBoreholeSimple.setTerminalHoleDate(new Date());
        tbKActBoreholeSimple.setChargehand("钻孔负责人");
        tbKActBoreholeSimple.setPersonCheckingVideo("视频验收人员");
        tbKActBoreholeSimple.setPersonCheckingSite("现场验收人员");
        tbKActBoreholeSimple.setTotalTimeFlushing(120.2);  //累计冲煤时间
        tbKActBoreholeSimple.setSeeRockCoalCase("见岩见煤情况");  //见岩见煤情况
        tbKActBoreholeSimple.setExceptionPhenomena("冲孔过程中异常标注");

        tbKActBoreholeSimple.setSealingHoleDiameter(22.1); //封孔直径
        tbKActBoreholeSimple.setSolidPipelineLength(23.1); // 封孔实管长度
        tbKActBoreholeSimple.setSievePipelineLength(3.1); //封孔花管长度
        tbKActBoreholeSimple.setSealingMaterial("两堵一注"); //封孔方式
        tbKActBoreholeSimple.setHolesealDepth(6.8); // 封孔管长度
        tbKActBoreholeSimple.setHolesealDate(new Date()); //封孔时间
        tbKActBoreholeSimple.setHolesealChargehand("封孔负责人"); //封孔负责人

        tbKActBoreholeSimple.setGroutingDate(new Date()); //注浆日期
        tbKActBoreholeSimple.setGroutingStartTime(new Date());  //注浆开始时间
        tbKActBoreholeSimple.setGroutingEndTime(new Date());  //注浆结束时间
        tbKActBoreholeSimple.setGroutingCements("6.6袋");  //注浆水泥量
        tbKActBoreholeSimple.setGroutingWaters("6.6袋"); //注浆水量
        tbKActBoreholeSimple.setGroutingChargehand("注浆负责人");  //注浆负责人
        tbKActBoreholeSimple.setPersonCheckingSiteGrouting("注浆现场验收人员");  //注浆现场验收人员
        tbKActBoreholeSimple.setPersonCheckingVideoGrouting("注浆视频验收人员"); //注浆视频验收人员


        List<TBKActBoreholeSimple> list = new ArrayList<>();
        list.add(tbKActBoreholeSimple);
        List<String> roadways = this.itRoadwayService.getRoadwayAll();
        List<String> zonenames = this.itRoadwayZoneService.getZoneNameAll();
        List<String> boreholecategorys = this.itBoreholeCategoryService.getBoreholeCategoryAll();
        List<String> purposeboreholes = this.itPurposeBoreholeService.getPurposeBoreholeAll();
        List<String> shiftinfos = this.itShiftinfoService.getShiftInfoAll();
        List<String> navigationpoint = this.itNavigationPointService.getNavigationPointAll();
        setSimple("roadway", roadways);
        setSimple("zonename", zonenames);
//        setSimple("boreholeCategory",boreholecategorys);
//        setSimple("purposeBorehole",purposeboreholes);
//        setSimple("navigationPointName",navigationpoint);
        ExcelUtil<TBKActBoreholeSimple> util = new ExcelUtil<>(TBKActBoreholeSimple.class);
        util.exportExcel(response, list, "钻孔台帐模版");
    }


    /**
     * 导出模版（实钻应有信息的模版）
     *
     * @param response
     * @CreateTime: 2023/7/6 10:45
     */
    @PostMapping("exportGeneralizedTemplate")
    public void exportGeneralizedTemplate(HttpServletResponse response) throws Exception {
        TBKActBoreholeGeneralized tbkActBoreholeGeneralized = new TBKActBoreholeGeneralized();
        tbkActBoreholeGeneralized.setBoreholeCode("B1-1");
        tbkActBoreholeGeneralized.setBoreholeCategory("钻孔类型");
        tbkActBoreholeGeneralized.setPurposeBorehole("钻孔用途");
        tbkActBoreholeGeneralized.setDrillingLocation("钻孔位置");
        tbkActBoreholeGeneralized.setNavigationPointName("导线点");
        tbkActBoreholeGeneralized.setDistanceToNavigationPoint(2.6);  //距导线点距离
        tbkActBoreholeGeneralized.setDirectionToNavigation("导线点方向");
        tbkActBoreholeGeneralized.setDrillingDate(new Date());
        tbkActBoreholeGeneralized.setActAperture(22.3);
        tbkActBoreholeGeneralized.setHeightArchBaseline(2.8);
        tbkActBoreholeGeneralized.setActAzimuthAngle("168.6");
        tbkActBoreholeGeneralized.setActInclinationAngle(65.1);
        tbkActBoreholeGeneralized.setActAngle(63.0);
        tbkActBoreholeGeneralized.setActLineSpace(3.0);
        tbkActBoreholeGeneralized.setActHoleSpace(1.5);
        tbkActBoreholeGeneralized.setActBoreholeLength(23.6);
        tbkActBoreholeGeneralized.setDepth1stRockSeamStart(0.0);
        tbkActBoreholeGeneralized.setDepth1stRockSeamEnd(19.8);
        tbkActBoreholeGeneralized.setDepth1stCoalSeamStart(19.8);
        tbkActBoreholeGeneralized.setDepth1stCoalSeamEnd(23.0);
        tbkActBoreholeGeneralized.setDepth2stRockSeamStart(23.0);
        tbkActBoreholeGeneralized.setDepth2stRockSeamEnd(24.0);
        tbkActBoreholeGeneralized.setDepth2stCoalSeamStart(24.0);
        tbkActBoreholeGeneralized.setDepth2stCoalSeamEnd(26.1);
        tbkActBoreholeGeneralized.setDepth3stRockSeamStart(25.0);
        tbkActBoreholeGeneralized.setDepth3stRockSeamEnd(26.5);
        tbkActBoreholeGeneralized.setExtractionRadius(5.0);
        tbkActBoreholeGeneralized.setDistanceToCentrallineTunnel(2.3);
        tbkActBoreholeGeneralized.setSprayHoleCondition("钻孔喷空情况");
        tbkActBoreholeGeneralized.setExceptionDuringDrilling("打钻过程中异常现场");
        tbkActBoreholeGeneralized.setAcceptanceStatus("验收情况");
        tbkActBoreholeGeneralized.setDownTubeTimeStart(new Date());
        tbkActBoreholeGeneralized.setDownTubeTimeStart(new Date());
        tbkActBoreholeGeneralized.setTubeLength(16.0);
        tbkActBoreholeGeneralized.setDownTubeChargehand("下管负责人");
        tbkActBoreholeGeneralized.setSolidPipelineLength(5.6);
        tbkActBoreholeGeneralized.setSievePipelineLength(3.3);
        tbkActBoreholeGeneralized.setHolesealDepth(2.2);
        tbkActBoreholeGeneralized.setSealingHoleDiameter(16.2);
        tbkActBoreholeGeneralized.setSealingMaterial("两堵一注");
        tbkActBoreholeGeneralized.setSieveTubeLength(4.1);
        tbkActBoreholeGeneralized.setHolesealDate(new Date());
        tbkActBoreholeGeneralized.setHolesealChargehand("封孔负责人");
        tbkActBoreholeGeneralized.setChargehand("钻孔负责人");
        tbkActBoreholeGeneralized.setPersonCheckingVideo("视频验收人员");
        tbkActBoreholeGeneralized.setPersonCheckingSite("现场验收人员");
        tbkActBoreholeGeneralized.setCheckingDate(new Date());
        tbkActBoreholeGeneralized.setTimeBeginFlushing(new Date());
        tbkActBoreholeGeneralized.setTimeEndFlushing(new Date());
        tbkActBoreholeGeneralized.setTotalTimeFlushing(120.1);
        tbkActBoreholeGeneralized.setSeeRockCoalCase("见岩见煤情况");
        tbkActBoreholeGeneralized.setActualCoalAmountFlushed(5.5);
        tbkActBoreholeGeneralized.setExceptionPhenomena("冲孔过程中异常标注");
        tbkActBoreholeGeneralized.setChargehandFlushing("冲孔负责人");
        tbkActBoreholeGeneralized.setTerminalHoleDate(new Date());
        tbkActBoreholeGeneralized.setRemarkFlushing("冲孔备注");
        tbkActBoreholeGeneralized.setGroutingDate(new Date());
        tbkActBoreholeGeneralized.setGroutingStartTime(new Date());
        tbkActBoreholeGeneralized.setGroutingEndTime(new Date());
        tbkActBoreholeGeneralized.setGroutingCements("8.8袋");
        tbkActBoreholeGeneralized.setGroutingWaters("8.8袋");
        tbkActBoreholeGeneralized.setGroutingChargehand("注浆负责人");
        tbkActBoreholeGeneralized.setPersonCheckingVideoGrouting("注浆视频验收人员");
        tbkActBoreholeGeneralized.setPersonCheckingSiteGrouting("注浆现场验收人员");

        List<TBKActBoreholeGeneralized> list = new ArrayList<>();
        List<String> roadways = this.itRoadwayService.getRoadwayAll();
        List<String> zonenames = this.itRoadwayZoneService.getZoneNameAll();
        List<String> boreholecategorys = this.itBoreholeCategoryService.getBoreholeCategoryAll();
        List<String> purposeboreholes = this.itPurposeBoreholeService.getPurposeBoreholeAll();
        List<String> shiftinfos = this.itShiftinfoService.getShiftInfoAll();
        List<String> navigationpoint = this.itNavigationPointService.getNavigationPointAll();
        setGeneralized("roadway", roadways);
        setGeneralized("boreholeCategory", boreholecategorys);
        setGeneralized("purposeBorehole", purposeboreholes);
        setGeneralized("navigationPointName", navigationpoint);
        list.add(tbkActBoreholeGeneralized);
        ExcelUtil<TBKActBoreholeGeneralized> util = new ExcelUtil<>(TBKActBoreholeGeneralized.class);
        util.exportExcel(response, list, "钻孔台帐模版");
    }

    /**
     * 导入Excel数据（八矿提供简单Excel模版）
     *
     * @param file
     * @return
     * @throws Exception
     * @CreateTime: 2023/7/5 11:18
     */
    @PostMapping("/importSimpleData")
    public AjaxResult importSimpleData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failNum = 0;
        StringBuffer sb = new StringBuffer();
        ExcelUtil<TBKActBoreholeSimple> util = new ExcelUtil<TBKActBoreholeSimple>(TBKActBoreholeSimple.class);
        List<TBKActBoreholeSimple> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TBKActBoreholeSimple tbkActBoreholeSimple : list) {
            TBkActBoreholeInfo tBkActBoreholeInfo = assignmentActBoreholeSimple(tbkActBoreholeSimple);
            BeanValidators.validateWithException(validator, tBkActBoreholeInfo);
            Result r = this.tBkActBoreholeInfoService.insertTBkActBoreholeInfo(tBkActBoreholeInfo);
            if (r.getStatus() == 0) {
                successNum++;
            } else {
                sb.append("组号" + tBkActBoreholeInfo.getGroupNo() + "孔号" + tBkActBoreholeInfo.getAdjacentBoreholeNo() + ",");
                failNum++;
            }
        }
        return AjaxResult.success("导入" + successNum + "条成功,失败" + failNum + "条:" + sb.toString());
    }

    /**
     * 导入Excel数据（实钻详细信息数据）
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importGeneralizedData")
    public AjaxResult importGeneralizedData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failNum = 0;
        StringBuffer sb = new StringBuffer();
        ExcelUtil<TBKActBoreholeGeneralized> util = new ExcelUtil<TBKActBoreholeGeneralized>(TBKActBoreholeGeneralized.class);
        List<TBKActBoreholeGeneralized> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TBKActBoreholeGeneralized tbkActBoreholeGeneralized : list) {
            TBkActBoreholeInfo tBkActBoreholeInfo = assignmentGeneralizedActBorehole(tbkActBoreholeGeneralized);
            BeanValidators.validateWithException(validator, tBkActBoreholeInfo);
            Result r = this.tBkActBoreholeInfoService.insertTBkActBoreholeInfo(tBkActBoreholeInfo);
            if (r.getStatus() == 0) {
                successNum++;
            } else {
                sb.append("组号" + tBkActBoreholeInfo.getGroupNo() + "孔号" + tBkActBoreholeInfo.getAdjacentBoreholeNo() + ",");
                failNum++;
            }
        }
        return AjaxResult.success("导入" + successNum + "条成功,失败" + failNum + "条:" + sb.toString());
    }

    /**
     * Excel导入数据（实钻全数据）
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failNum = 0;
        StringBuffer sb = new StringBuffer();
        ExcelUtil<TBkActBoreholeInfo> util = new ExcelUtil<TBkActBoreholeInfo>(TBkActBoreholeInfo.class);
        List<TBkActBoreholeInfo> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TBkActBoreholeInfo tBkActBoreholeInfo : list) {
            BeanValidators.validateWithException(validator, tBkActBoreholeInfo);
            Result r = this.tBkActBoreholeInfoService.insertTBkActBoreholeInfo(tBkActBoreholeInfo);
            if (r.getStatus() == 0) {
                successNum++;
            } else {
                sb.append("组号" + tBkActBoreholeInfo.getGroupNo() + "孔号" + tBkActBoreholeInfo.getAdjacentBoreholeNo() + ",");
                failNum++;
            }
        }
        return AjaxResult.success("导入" + successNum + "条成功,失败" + failNum + "条:" + sb.toString());
    }


    /**
     * 八矿实钻台帐
     *
     * @return
     */
    @PostMapping("getActBoreholeBook")
    public void getActBoreholeBook(HttpServletResponse response, TBkActBoreholeInfo tBkActBoreholeInfo) throws Exception {
        List<TBkActBoreholeInfo> tBkActBoreholeInfos = this.tBkActBoreholeInfoService.selectTBkActBoreholeInfoList(tBkActBoreholeInfo);
        List<TBKActBoreholeSimple> list = new ArrayList<>();
        for (TBkActBoreholeInfo tbi : tBkActBoreholeInfos) {
            list.add(assignmentActToSimple(tbi));
        }
        ExcelUtil<TBKActBoreholeSimple> util = new ExcelUtil<>(TBKActBoreholeSimple.class);
        util.exportExcel(response, list, "实际钻孔台帐");
    }

    /**
     * 将实钻信息赋值给导出钻孔台帐
     *
     * @param tBkActBoreholeInfo
     * @return
     */
    public TBKActBoreholeSimple assignmentActToSimple(TBkActBoreholeInfo tBkActBoreholeInfo) {
        TBKActBoreholeSimple tbKActBoreholeSimple = new TBKActBoreholeSimple();
        tbKActBoreholeSimple.setBoreholeCode(tBkActBoreholeInfo.getBoreholeCode());
        tbKActBoreholeSimple.setRoadway(tBkActBoreholeInfo.getRoadway());
        tbKActBoreholeSimple.setZonename(tBkActBoreholeInfo.getZoneName());
        tbKActBoreholeSimple.setActBoreholeLength(tBkActBoreholeInfo.getBoreholeLength());
        TDesignedBorehole tDesignedBorehole = this.itDesignedBoreholeService.findDesBoreholeInfo(tBkActBoreholeInfo.getRoadwayId(), tBkActBoreholeInfo.getGroupNo(),
                tBkActBoreholeInfo.getBoreholeNo());
        if (tDesignedBorehole != null) {
            tbKActBoreholeSimple.setDesBoreholeLength(tDesignedBorehole.getBoreholeLength());
        }
        tbKActBoreholeSimple.setDepth1stRockSeamStart(tBkActBoreholeInfo.getDepth1stRockSeamStart());
        tbKActBoreholeSimple.setDepth1stRockSeamEnd(tBkActBoreholeInfo.getDepth1stRockSeamEnd());
        tbKActBoreholeSimple.setDepth1stCoalSeamStart(tBkActBoreholeInfo.getDepth1stCoalSeamStart());
        tbKActBoreholeSimple.setDepth1stCoalSeamEnd(tBkActBoreholeInfo.getDepth1stCoalSeamEnd());
        tbKActBoreholeSimple.setDepth2stRockSeamStart(tBkActBoreholeInfo.getDepth2stRockSeamStart());
        tbKActBoreholeSimple.setDepth2stRockSeamEnd(tBkActBoreholeInfo.getDepth2stRockSeamEnd());
        tbKActBoreholeSimple.setDepth2stCoalSeamStart(tBkActBoreholeInfo.getDepth2stCoalSeamStart());
        tbKActBoreholeSimple.setDepth2stCoalSeamEnd(tBkActBoreholeInfo.getDepth2stCoalSeamEnd());
        tbKActBoreholeSimple.setDepth3stRockSeamStart(tBkActBoreholeInfo.getDepth3stRockSeamStart());
        tbKActBoreholeSimple.setDepth3stRockSeamEnd(tBkActBoreholeInfo.getDepth3stRockSeamEnd());
        tbKActBoreholeSimple.setActualCoalAmountFlushed(tBkActBoreholeInfo.getActualCoalAmountFlushed());
        tbKActBoreholeSimple.setSprayHoleCondition(tBkActBoreholeInfo.getSprayHoleCondition());
        tbKActBoreholeSimple.setExceptionConstruction(tBkActBoreholeInfo.getExceptionConstruction());
        tbKActBoreholeSimple.setAcceptanceStatus(tBkActBoreholeInfo.getAcceptanceStatus());
        tbKActBoreholeSimple.setActAperture(tBkActBoreholeInfo.getAperture());
        tbKActBoreholeSimple.setActAzimuthAngle(tBkActBoreholeInfo.getAzimuthAngle());
        tbKActBoreholeSimple.setActAngle(tBkActBoreholeInfo.getAngle());
        tbKActBoreholeSimple.setDrillingLocation(tBkActBoreholeInfo.getDrillingLocation());
        tbKActBoreholeSimple.setExceptionDuringDrilling(tBkActBoreholeInfo.getExceptionDuringDrilling());
        tbKActBoreholeSimple.setTerminalHoleDate(tBkActBoreholeInfo.getTerminalHoleDate());
        tbKActBoreholeSimple.setChargehand(tBkActBoreholeInfo.getChargehand());
        tbKActBoreholeSimple.setPersonCheckingVideo(tBkActBoreholeInfo.getPersonCheckingVideo());
        tbKActBoreholeSimple.setPersonCheckingSite(tBkActBoreholeInfo.getPersonCheckingSite());
        tbKActBoreholeSimple.setTotalTimeFlushing(tBkActBoreholeInfo.getTotalTimeFlushing());
        tbKActBoreholeSimple.setSeeRockCoalCase(tBkActBoreholeInfo.getSeeRockCoalCase());
        tbKActBoreholeSimple.setExceptionPhenomena(tBkActBoreholeInfo.getExceptionPhenomena());
        tbKActBoreholeSimple.setHoleSealingSpecification(tBkActBoreholeInfo.getHoleSealingSpecification());
        tbKActBoreholeSimple.setSealingHoleDiameter(tBkActBoreholeInfo.getSealingHoleDiameter());
        tbKActBoreholeSimple.setSolidPipelineLength(tBkActBoreholeInfo.getSolidPipelineLength());
        tbKActBoreholeSimple.setSievePipelineLength(tBkActBoreholeInfo.getSievePipelineLength());
        tbKActBoreholeSimple.setSealingMaterial(tBkActBoreholeInfo.getSealingMaterial());
        tbKActBoreholeSimple.setHolesealDepth(tBkActBoreholeInfo.getHolesealDepth());
        tbKActBoreholeSimple.setHolesealDate(tBkActBoreholeInfo.getHolesealDate());
        tbKActBoreholeSimple.setHolesealChargehand(tBkActBoreholeInfo.getHolesealChargehand());
        tbKActBoreholeSimple.setGroutingDate(tBkActBoreholeInfo.getGroutingDate());
        tbKActBoreholeSimple.setGroutingStartTime(tBkActBoreholeInfo.getGroutingStartTime());
        tbKActBoreholeSimple.setGroutingEndTime(tBkActBoreholeInfo.getGroutingEndTime());
        tbKActBoreholeSimple.setGroutingCements(tBkActBoreholeInfo.getGroutingCements());
        tbKActBoreholeSimple.setGroutingWaters(tBkActBoreholeInfo.getGroutingWaters());
        tbKActBoreholeSimple.setGroutingChargehand(tBkActBoreholeInfo.getGroutingChargehand());
        tbKActBoreholeSimple.setPersonCheckingSiteGrouting(tBkActBoreholeInfo.getPersonCheckingSiteGrouting());
        tbKActBoreholeSimple.setPersonCheckingVideoGrouting(tBkActBoreholeInfo.getPersonCheckingVideoGrouting());
        tbKActBoreholeSimple.setRemark(tBkActBoreholeInfo.getRemark());
        return tbKActBoreholeSimple;
    }


    /**
     * 赋值
     *
     * @param tbKActBoreholeSimple
     * @return
     */
    public TBkActBoreholeInfo assignmentActBoreholeSimple(TBKActBoreholeSimple tbKActBoreholeSimple) {
        TBkActBoreholeInfo tBkActBoreholeInfo = new TBkActBoreholeInfo();
        if (splitForData(tbKActBoreholeSimple.getBoreholeCode()) != null) {
            tBkActBoreholeInfo.setGroupNo(splitForData(tbKActBoreholeSimple.getBoreholeCode())[0]);
            tBkActBoreholeInfo.setBoreholeNo(splitForData(tbKActBoreholeSimple.getBoreholeCode())[1]);
        }
        tBkActBoreholeInfo.setRoadway(tbKActBoreholeSimple.getRoadway());
        tBkActBoreholeInfo.setBoreholeCode(tbKActBoreholeSimple.getBoreholeCode());
        tBkActBoreholeInfo.setDrillingLocation(tbKActBoreholeSimple.getDrillingLocation());
        tBkActBoreholeInfo.setAperture(tbKActBoreholeSimple.getActAperture());
        tBkActBoreholeInfo.setAzimuthAngle(tbKActBoreholeSimple.getActAzimuthAngle());
        tBkActBoreholeInfo.setAngle(tbKActBoreholeSimple.getActAngle());
        tBkActBoreholeInfo.setBoreholeLength(tbKActBoreholeSimple.getActBoreholeLength());
        tBkActBoreholeInfo.setDepth1stRockSeamStart(tbKActBoreholeSimple.getDepth1stRockSeamEnd());
        tBkActBoreholeInfo.setDepth1stRockSeamEnd(tbKActBoreholeSimple.getDepth1stRockSeamEnd());
        tBkActBoreholeInfo.setDepth1stCoalSeamStart(tbKActBoreholeSimple.getDepth1stCoalSeamStart());
        tBkActBoreholeInfo.setDepth1stCoalSeamEnd(tbKActBoreholeSimple.getDepth1stCoalSeamEnd());
        tBkActBoreholeInfo.setDepth2stRockSeamStart(tbKActBoreholeSimple.getDepth2stRockSeamStart());
        tBkActBoreholeInfo.setDepth2stRockSeamEnd(tbKActBoreholeSimple.getDepth2stRockSeamEnd());
        tBkActBoreholeInfo.setDepth2stCoalSeamStart(tbKActBoreholeSimple.getDepth2stCoalSeamStart());
        tBkActBoreholeInfo.setDepth2stCoalSeamEnd(tbKActBoreholeSimple.getDepth2stCoalSeamEnd());
        tBkActBoreholeInfo.setDepth3stRockSeamStart(tbKActBoreholeSimple.getDepth3stRockSeamStart());
        tBkActBoreholeInfo.setDepth3stRockSeamEnd(tbKActBoreholeSimple.getDepth3stRockSeamEnd());
        tBkActBoreholeInfo.setActualCoalAmountFlushed(tbKActBoreholeSimple.getActualCoalAmountFlushed());
        tBkActBoreholeInfo.setSprayHoleCondition(tbKActBoreholeSimple.getSprayHoleCondition());
        tBkActBoreholeInfo.setExceptionDuringDrilling(tbKActBoreholeSimple.getExceptionDuringDrilling());
        tBkActBoreholeInfo.setAcceptanceStatus(tbKActBoreholeSimple.getAcceptanceStatus());
        tBkActBoreholeInfo.setAperture(tbKActBoreholeSimple.getActAperture());
        tBkActBoreholeInfo.setAzimuthAngle(tbKActBoreholeSimple.getActAzimuthAngle());
        tBkActBoreholeInfo.setAngle(tbKActBoreholeSimple.getActAngle());
        tBkActBoreholeInfo.setDrillingLocation(tbKActBoreholeSimple.getDrillingLocation());
        tBkActBoreholeInfo.setExceptionDuringDrilling(tbKActBoreholeSimple.getExceptionDuringDrilling());
        tBkActBoreholeInfo.setTerminalHoleDate(tbKActBoreholeSimple.getTerminalHoleDate());
        tBkActBoreholeInfo.setChargehand(tbKActBoreholeSimple.getChargehand());
        tBkActBoreholeInfo.setPersonCheckingSite(tbKActBoreholeSimple.getPersonCheckingSite());
        tBkActBoreholeInfo.setTotalTimeFlushing(tbKActBoreholeSimple.getTotalTimeFlushing());  //累计冲孔时间
        tBkActBoreholeInfo.setSeeRockCoalCase(tbKActBoreholeSimple.getSeeRockCoalCase());
        tBkActBoreholeInfo.setExceptionPhenomena(tbKActBoreholeSimple.getExceptionPhenomena());
        tBkActBoreholeInfo.setSealingHoleDiameter(tbKActBoreholeSimple.getSealingHoleDiameter());
        tBkActBoreholeInfo.setSolidPipelineLength(tbKActBoreholeSimple.getSolidPipelineLength());
        tBkActBoreholeInfo.setSievePipelineLength(tbKActBoreholeSimple.getSievePipelineLength());
        tBkActBoreholeInfo.setSealingMaterial(tbKActBoreholeSimple.getSealingMaterial());
        tBkActBoreholeInfo.setHolesealDepth(tbKActBoreholeSimple.getHolesealDepth());
        tBkActBoreholeInfo.setHolesealDate(tbKActBoreholeSimple.getHolesealDate());
        tBkActBoreholeInfo.setHolesealChargehand(tbKActBoreholeSimple.getHolesealChargehand());
        tBkActBoreholeInfo.setGroutingDate(tbKActBoreholeSimple.getGroutingDate());
        tBkActBoreholeInfo.setGroutingStartTime(tbKActBoreholeSimple.getGroutingStartTime());
        tBkActBoreholeInfo.setGroutingEndTime(tbKActBoreholeSimple.getGroutingEndTime());
        tBkActBoreholeInfo.setGroutingCements(tbKActBoreholeSimple.getGroutingCements());
        tBkActBoreholeInfo.setGroutingWaters(tbKActBoreholeSimple.getGroutingWaters());
        tBkActBoreholeInfo.setGroutingChargehand(tbKActBoreholeSimple.getGroutingChargehand());
        tBkActBoreholeInfo.setPersonCheckingSite(tbKActBoreholeSimple.getPersonCheckingSite());
        tBkActBoreholeInfo.setPersonCheckingVideo(tbKActBoreholeSimple.getPersonCheckingVideo());
        return tBkActBoreholeInfo;
    }

    /**
     * 赋值（详细信息赋值）
     *
     * @param tbkActBoreholeGeneralized
     * @return
     */
    public TBkActBoreholeInfo assignmentGeneralizedActBorehole(TBKActBoreholeGeneralized tbkActBoreholeGeneralized) {
        TBkActBoreholeInfo tBkActBoreholeInfo = new TBkActBoreholeInfo();
        if (splitForData(tbkActBoreholeGeneralized.getBoreholeCode()) != null) {
            tBkActBoreholeInfo.setGroupNo(splitForData(tbkActBoreholeGeneralized.getBoreholeCode())[0]);
            tBkActBoreholeInfo.setBoreholeNo(splitForData(tbkActBoreholeGeneralized.getBoreholeCode())[1]);
        }
        tBkActBoreholeInfo.setBoreholeCode(tbkActBoreholeGeneralized.getBoreholeCode());
        tBkActBoreholeInfo.setRoadway(tbkActBoreholeGeneralized.getRoadway());
        tBkActBoreholeInfo.setBoreholeCategory(tbkActBoreholeGeneralized.getBoreholeCategory());
        tBkActBoreholeInfo.setPurposeBorehole(tbkActBoreholeGeneralized.getPurposeBorehole());
        tBkActBoreholeInfo.setDrillingLocation(tbkActBoreholeGeneralized.getDrillingLocation());
        tBkActBoreholeInfo.setNavigationPointName(tbkActBoreholeGeneralized.getNavigationPointName());
        tBkActBoreholeInfo.setDistanceToNavigationPoint(tbkActBoreholeGeneralized.getDistanceToNavigationPoint());  //距导线点距离
        tBkActBoreholeInfo.setDirectionToNavigation(tbkActBoreholeGeneralized.getDirectionToNavigation());
        tBkActBoreholeInfo.setDrillingDate(tbkActBoreholeGeneralized.getDrillingDate());
        tBkActBoreholeInfo.setAperture(tbkActBoreholeGeneralized.getActAperture());
        tBkActBoreholeInfo.setAzimuthAngle(tbkActBoreholeGeneralized.getActAzimuthAngle());
        tBkActBoreholeInfo.setInclinationAngle(tbkActBoreholeGeneralized.getActInclinationAngle());
        tBkActBoreholeInfo.setAngle(tbkActBoreholeGeneralized.getActAngle());
        tBkActBoreholeInfo.setLineSpace(tbkActBoreholeGeneralized.getActLineSpace());
        tBkActBoreholeInfo.setHoleSpace(tbkActBoreholeGeneralized.getActHoleSpace());
        tBkActBoreholeInfo.setBoreholeLength(tbkActBoreholeGeneralized.getActBoreholeLength());
        tBkActBoreholeInfo.setDepth1stRockSeamStart(tbkActBoreholeGeneralized.getDepth1stRockSeamStart());
        tBkActBoreholeInfo.setDepth1stRockSeamEnd(tbkActBoreholeGeneralized.getDepth1stRockSeamEnd());
        tBkActBoreholeInfo.setDepth1stCoalSeamStart(tbkActBoreholeGeneralized.getDepth1stCoalSeamStart());
        tBkActBoreholeInfo.setDepth1stCoalSeamEnd(tbkActBoreholeGeneralized.getDepth1stCoalSeamEnd());
        tBkActBoreholeInfo.setDepth2stRockSeamStart(tbkActBoreholeGeneralized.getDepth2stRockSeamStart());
        tBkActBoreholeInfo.setDepth2stRockSeamEnd(tbkActBoreholeGeneralized.getDepth2stRockSeamEnd());
        tBkActBoreholeInfo.setDepth2stCoalSeamStart(tbkActBoreholeGeneralized.getDepth2stCoalSeamStart());
        tBkActBoreholeInfo.setDepth2stCoalSeamEnd(tbkActBoreholeGeneralized.getDepth2stCoalSeamEnd());
        tBkActBoreholeInfo.setDepth3stRockSeamStart(tbkActBoreholeGeneralized.getDepth3stRockSeamStart());
        tBkActBoreholeInfo.setDepth3stRockSeamEnd(tbkActBoreholeGeneralized.getDepth3stRockSeamEnd());
        tBkActBoreholeInfo.setExtractionRadius(tbkActBoreholeGeneralized.getExtractionRadius());
        tBkActBoreholeInfo.setDistanceToCentrallineTunnel(tbkActBoreholeGeneralized.getDistanceToCentrallineTunnel());
        tBkActBoreholeInfo.setSprayHoleCondition(tbkActBoreholeGeneralized.getSprayHoleCondition());
        tBkActBoreholeInfo.setExceptionDuringDrilling(tbkActBoreholeGeneralized.getExceptionDuringDrilling());
        tBkActBoreholeInfo.setAcceptanceStatus(tbkActBoreholeGeneralized.getAcceptanceStatus());
        tBkActBoreholeInfo.setDownTubeTimeStart(tbkActBoreholeGeneralized.getDownTubeTimeStart());
        tBkActBoreholeInfo.setDownTubeTimeStart(tbkActBoreholeGeneralized.getDownTubeTimeStart());
        tBkActBoreholeInfo.setTubeLength(tbkActBoreholeGeneralized.getTubeLength());
        tBkActBoreholeInfo.setDownTubeChargehand(tbkActBoreholeGeneralized.getDownTubeChargehand());
        tBkActBoreholeInfo.setSolidPipelineLength(tbkActBoreholeGeneralized.getSolidPipelineLength());
        tBkActBoreholeInfo.setSievePipelineLength(tbkActBoreholeGeneralized.getSievePipelineLength());
        tBkActBoreholeInfo.setHolesealDepth(tbkActBoreholeGeneralized.getHolesealDepth());
        tBkActBoreholeInfo.setSealingHoleDiameter(tbkActBoreholeGeneralized.getSealingHoleDiameter());
        tBkActBoreholeInfo.setSealingMaterial(tbkActBoreholeGeneralized.getSealingMaterial());
        tBkActBoreholeInfo.setSieveTubeLength(tbkActBoreholeGeneralized.getSieveTubeLength());
        tBkActBoreholeInfo.setHolesealDate(tbkActBoreholeGeneralized.getHolesealDate());
        tBkActBoreholeInfo.setHolesealChargehand(tbkActBoreholeGeneralized.getHolesealChargehand());
        tBkActBoreholeInfo.setChargehand(tbkActBoreholeGeneralized.getChargehand());
        tBkActBoreholeInfo.setPersonCheckingVideo(tbkActBoreholeGeneralized.getPersonCheckingVideo());
        tBkActBoreholeInfo.setPersonCheckingSite(tbkActBoreholeGeneralized.getPersonCheckingSite());
        tBkActBoreholeInfo.setCheckingDate(tbkActBoreholeGeneralized.getCheckingDate());
        tBkActBoreholeInfo.setTimeBeginFlushing(tbkActBoreholeGeneralized.getTimeBeginFlushing());
        tBkActBoreholeInfo.setTimeEndFlushing(tbkActBoreholeGeneralized.getTimeEndFlushing());
        tBkActBoreholeInfo.setTotalTimeFlushing(tbkActBoreholeGeneralized.getTotalTimeFlushing());
        tBkActBoreholeInfo.setSeeRockCoalCase(tbkActBoreholeGeneralized.getSeeRockCoalCase());
        tBkActBoreholeInfo.setActualCoalAmountFlushed(tbkActBoreholeGeneralized.getActualCoalAmountFlushed());
        tBkActBoreholeInfo.setExceptionPhenomena(tbkActBoreholeGeneralized.getExceptionPhenomena());
        tBkActBoreholeInfo.setChargehandFlushing(tbkActBoreholeGeneralized.getChargehandFlushing());
        tBkActBoreholeInfo.setTerminalHoleDate(tbkActBoreholeGeneralized.getTerminalHoleDate());
        tBkActBoreholeInfo.setRemarkFlushing(tbkActBoreholeGeneralized.getRemarkFlushing());
        tBkActBoreholeInfo.setGroutingDate(tbkActBoreholeGeneralized.getGroutingDate());
        tBkActBoreholeInfo.setGroutingStartTime(tbkActBoreholeGeneralized.getGroutingStartTime());
        tBkActBoreholeInfo.setGroutingEndTime(tbkActBoreholeGeneralized.getGroutingEndTime());
        tBkActBoreholeInfo.setGroutingCements(tbkActBoreholeGeneralized.getGroutingCements());
        tBkActBoreholeInfo.setGroutingWaters(tbkActBoreholeGeneralized.getGroutingWaters());
        tBkActBoreholeInfo.setGroutingChargehand(tbkActBoreholeGeneralized.getGroutingChargehand());
        tBkActBoreholeInfo.setPersonCheckingVideoGrouting(tbkActBoreholeGeneralized.getPersonCheckingVideoGrouting());
        tBkActBoreholeInfo.setPersonCheckingSiteGrouting(tbkActBoreholeGeneralized.getPersonCheckingSiteGrouting());
        return tBkActBoreholeInfo;
    }

    /**
     * 解析钻孔编号（分成组号和孔号）
     *
     * @param boreholeCode
     * @return
     * @CreateTime: 2023/7/7 11:10
     */
    public String[] splitForData(String boreholeCode) {
        if (boreholeCode.contains("-")) {
            return boreholeCode.split("-");
        }
        return null;
    }


    /**
     * 给实钻赋值
     *
     * @param param
     * @param list
     * @throws Exception
     */
    // 给combo赋值
    private void setCombo(String param, List<String> list) throws Exception {
        // 通过反射 获取目标实体类的属性成员
        Field file = TBkActBoreholeInfo.class.getDeclaredField(param);
        // 获取该字段的上叫Excel的注解
        Excel annotation = file.getAnnotation(Excel.class);
        InvocationHandler h = Proxy.getInvocationHandler(annotation);
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 设置私有可访问
        hField.setAccessible(true);
        Map memberValues = (Map) hField.get(h);
        // 集合转数组
        String[] combo = list.toArray(new String[list.size()]);
        // 修改属性值
        memberValues.put("combo", combo);
    }

    private void setGeneralized(String param, List<String> list) throws Exception {
        // 通过反射 获取目标实体类的属性成员
        Field file = TBKActBoreholeGeneralized.class.getDeclaredField(param);
        // 获取该字段的上叫Excel的注解
        Excel annotation = file.getAnnotation(Excel.class);
        InvocationHandler h = Proxy.getInvocationHandler(annotation);
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 设置私有可访问
        hField.setAccessible(true);
        Map memberValues = (Map) hField.get(h);
        // 集合转数组
        String[] generalizedList = list.toArray(new String[list.size()]);
        // 修改属性值
        memberValues.put("combo", generalizedList);
    }

    private void setSimple(String param, List<String> list) throws Exception {
        // 通过反射 获取目标实体类的属性成员
        Field file = TBKActBoreholeSimple.class.getDeclaredField(param);
        // 获取该字段的上叫Excel的注解
        Excel annotation = file.getAnnotation(Excel.class);
        InvocationHandler h = Proxy.getInvocationHandler(annotation);
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 设置私有可访问
        hField.setAccessible(true);
        Map memberValues = (Map) hField.get(h);
        // 集合转数组
        String[] simpleList = list.toArray(new String[list.size()]);
        // 修改属性值
        memberValues.put("combo", simpleList);
    }

    /**
     * 获取钻孔基础信息
     *
     * @param roadwayId         巷道ID
     * @param groupNo           组号
     * @param purposeboreholeId 钻孔用途ID
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @return 列表
     */
    @PostMapping("getBoreholeInfo")
    public TableDataInfo getBoreholeInfo(@RequestParam(value = "roadwayId", required = false) Integer roadwayId,
                                         @RequestParam(value = "groupNo", required = false) List<String> groupNo,
                                         @RequestParam(value = "purposeboreholeId", required = false) List<Integer> purposeboreholeId,
                                         @RequestParam(value = "boreholeNo", required = false) List<Integer> boreholeNo,
                                         @RequestParam(required = false, value = "startDate") Date startDate,
                                         @RequestParam(required = false, value = "endDate") Date endDate
    ) throws ParseException {
        startPage();
        if (startDate != null && endDate == null) {
            endDate = new Date();
        }
        List<TGasDrillingStandingBook> list = this.tBkActBoreholeInfoService.getActBoreholeInfo(roadwayId, groupNo, purposeboreholeId, boreholeNo,
                startDate, endDate);
        return getDataTable(list);

    }

}

