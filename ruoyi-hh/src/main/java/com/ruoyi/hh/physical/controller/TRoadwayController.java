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
import com.ruoyi.hh.physical.domain.TBkActBoreholeInfo;
import com.ruoyi.hh.physical.domain.TNavigationPoint;
import com.ruoyi.hh.physical.domain.TRoadway;
import com.ruoyi.hh.physical.domain.TRoadwayZone;
import com.ruoyi.hh.physical.service.ITBkActBoreholeInfoService;
import com.ruoyi.hh.physical.service.ITNavigationPointService;
import com.ruoyi.hh.physical.service.ITRoadwayService;
import com.ruoyi.hh.physical.service.ITRoadwayZoneService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * (TRoadway)表控制层
 *
 * @author Eleven
 * @since 2022-07-09 15:10:39
 */
@RestController
@RequestMapping("tRoadway")
@Api(value = "巷道Controller", tags = {"DZ巷道信息接口"})
public class TRoadwayController extends BaseController {

    @Resource
    private ITRoadwayService tRoadwayService;
    @Resource
    private ITRoadwayZoneService tRoadwayZoneService;
    @Resource
    private ITNavigationPointService tNavigationPointService;
    @Resource
    private ITBkActBoreholeInfoService actBoreholeInfoService;
    @Resource
    protected Validator validator;


    /**
     * 获取列表
     *
     * @param tRoadway 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @ApiOperation("按条件获取巷道列表接口")
    @Log(title = "巷道管理", businessType = BusinessType.QUERY)
    //  @PreAuthorize("@ss.hasPermi('tRoadway:list')")
    public TableDataInfo selectAll(@RequestBody TRoadway tRoadway) {
        startPage();
        List<TRoadway> list = this.tRoadwayService.selectTRoadwayList(tRoadway);
        return getDataTable(list);
    }

    @GetMapping("byWorkingFace/{workingFaceId}")
    @ApiOperation("获取工作面内绑定的巷道")
    public AjaxResult selectRoadwayListByWorkFace(@PathVariable Integer workingFaceId) {
        return AjaxResult.success(tRoadwayService.selectRoadwayListByWorkFace(workingFaceId));
    }

    /**
     * 分页获取列表
     *
     * @return 结果
     */
    @PostMapping("getPageList")
    @Log(title = "巷道管理", businessType = BusinessType.QUERY)
    public TableDataInfo getPageList() {
        startPage();
        List<TRoadway> list = new ArrayList<>();
        list = this.tRoadwayService.getPageList();
        for (TRoadway t : list) {
            List<TRoadwayZone> tRoadwayZoneList = tRoadwayZoneService.getByRoadwayId(t.getId());
            List<TNavigationPoint> tNavigationPointList = tNavigationPointService.getByRoadwayId(t.getId());
            t.setTroadwayZoneList(tRoadwayZoneList);
            t.setTNavigationPointList(tNavigationPointList);
            // 设置超前探数据
            TBkActBoreholeInfo boreholeInfo = new TBkActBoreholeInfo();
            boreholeInfo.setCategoryCode(9);//code:9 代表超前探钻孔
            boreholeInfo.setRoadwayId(t.getId());
            t.setAdvanceExplorationDrillList(actBoreholeInfoService.selectTBkActBoreholeInfoList(boreholeInfo));
        }
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tRoadway 单挑数据
     */
    @PostMapping("insert")
    @ApiOperation("新增巷道数据接口")
    @Log(title = "巷道管理", businessType = BusinessType.INSERT)
    public AjaxResult insertTRoadway(@RequestBody TRoadway tRoadway) {
        return this.tRoadwayService.insertTRoadway(tRoadway);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @ApiOperation("批量新增巷道信息接口")
    @Log(title = "巷道管理", businessType = BusinessType.INSERT)
    //  @PreAuthorize("@ss.hasPermi('tRoadway:insert')")
    public AjaxResult insert(@RequestBody List<TRoadway> list) {
        Integer i = this.tRoadwayService.insertTRoadwayList(list);
        if (i > 0) {
            return AjaxResult.success("成功添加" + i + "条巷道");
        } else {
            return AjaxResult.error("巷道添加失败");
        }
    }

    /**
     * 修改巷道
     *
     * @param tRoadway 要修改的巷道信息
     * @return 结果
     */
    @PutMapping("updateTRoadway")
    @ApiOperation("修改巷道信息接口")
    @Log(title = "巷道管理", businessType = BusinessType.UPDATE)
    public AjaxResult updateTRoadway(@RequestBody TRoadway tRoadway) {
        return AjaxResult.success(this.tRoadwayService.updateTRoadway(tRoadway));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @ApiOperation("根据ID查询巷道信息")
    @Log(title = "巷道管理", businessType = BusinessType.QUERY)
    //  @PreAuthorize("@ss.hasPermi('tRoadway:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        TRoadway tRoadway = this.tRoadwayService.selectTRoadwayById(id);
        return AjaxResult.success(tRoadway);
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTRoadwayById")
    @ApiOperation("根据ID删除巷道信息")
    @Log(title = "巷道管理", businessType = BusinessType.DELETE)
    public AjaxResult deleteTRoadwayById(@RequestParam("id") int id) {
        int result = this.tRoadwayService.deleteTRoadwayById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除巷道的增删改查
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTRoadwayByIds")
    @ApiOperation("根据ID列表批量删除巷道信息")
    @Log(title = "巷道管理", businessType = BusinessType.DELETE)
    public AjaxResult deleteTRoadwayByIds(@RequestParam("ids") List<Integer> ids) {
        return AjaxResult.success(this.tRoadwayService.deleteTRoadwayByIds(ids));
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @Log(title = "巷道管理", businessType = BusinessType.EXPORT)
    @ApiOperation("导出巷道信息")
    public void export(HttpServletResponse response, @RequestBody TRoadway tRoadway) {
        List<TRoadway> list = this.tRoadwayService.selectTRoadwayList(tRoadway);
        ExcelUtil<TRoadway> util = new ExcelUtil<TRoadway>(TRoadway.class);
        util.exportExcel(response, list, "巷道信息数据");
    }

    /**
     * 导出excel模板
     *
     * @param response res
     */
    @PostMapping("exportTemplate")
    @ApiOperation("导出巷道文件模板")
    public void exportTemplate(HttpServletResponse response) {
        TRoadway tRoadway = new TRoadway();
        tRoadway.setRoadway("模板数据（巷道名称不能重复）");
        tRoadway.setRoadwayType("模板数据（已知巷道类型）");
        tRoadway.setRoadwayType("模板数据（已知断层类型）");
        tRoadway.setAzimuthAngle(88.8);
        tRoadway.setWide(66.8);
        tRoadway.setHeight(66.8);
        List<TRoadway> list = new ArrayList<>();
        list.add(tRoadway);
        ExcelUtil<TRoadway> util = new ExcelUtil<TRoadway>(TRoadway.class);
        util.exportExcel(response, list, "巷道信息数据");
    }

    /**
     * excel导入巷道信息
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    @Log(title = "巷道管理", businessType = BusinessType.IMPORT)
    @ApiOperation("导入巷道数据")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TRoadway> util = new ExcelUtil<TRoadway>(TRoadway.class);
        List<TRoadway> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TRoadway tRoadway : list) {
            BeanValidators.validateWithException(validator, tRoadway);
            AjaxResult r = this.tRoadwayService.insertTRoadway(tRoadway);
            if ((int) r.get("code") == 200) {
                successNum++;
            } else {
                failureNum++;
            }
        }
        return AjaxResult.success("添加成功" + successNum + "条," + failureNum + "条失败");
    }


}
