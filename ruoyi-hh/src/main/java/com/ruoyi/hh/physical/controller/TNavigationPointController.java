package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TNavigationPoint;
import com.ruoyi.hh.physical.service.ITNavigationPointService;
import com.ruoyi.hh.util.Result;
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
 * (TNavigationPoint)表控制层
 *
 * @author Eleven
 * @since 2022-07-13 14:44:57
 */
@RestController
@RequestMapping("tNavigationPoint")
@Api(value = "DZ导线点数据接口", tags = "DZ导线点数据接口")
public class TNavigationPointController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITNavigationPointService tNavigationPointService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tNavigationPoint 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //  @PreAuthorize("@ss.hasPermi('tNavigationPoint:list')")
    @ApiOperation("按条件查询导线点数据")
    public TableDataInfo selectAll(@RequestBody TNavigationPoint tNavigationPoint) {
        startPage();
        List<TNavigationPoint> list = this.tNavigationPointService.selectTNavigationPointList(tNavigationPoint);
        return getDataTable(list);
    }


    /**
     * 插入单条数据
     *
     * @param tNavigationPoint 单挑数据
     */
    @PostMapping("insert")
    //  @PreAuthorize("@ss.hasPermi('tNavigationPoint:insert')")
    @ApiOperation("新增导线点")
    public AjaxResult insertTNavigationPoint(@RequestBody TNavigationPoint tNavigationPoint) {
        Result result = this.tNavigationPointService.insertTNavigationPoint(tNavigationPoint);
        if (result.getStatus() == 0) {
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
    //  @PreAuthorize("@ss.hasPermi('tNavigationPoint:insert')")
    @ApiOperation("批量新增导线点")
    public void insert(@RequestBody List<TNavigationPoint> list) {
        this.tNavigationPointService.insertTNavigationPointList(list);
    }


    /**
     * 修改
     *
     * @param tNavigationPoint 要修改的实体信息u
     * @return 结果
     */
    @PutMapping("updateTNavigationPoint")
    //  @PreAuthorize("@ss.hasPermi('tNavigationPoint:updateTNavigationPoint')")
    @ApiOperation("新增导线点")
    public AjaxResult updateTRoadway(@RequestBody TNavigationPoint tNavigationPoint) {
        Result result = this.tNavigationPointService.updateTNavigationPoint(tNavigationPoint);
        if (result.getStatus() == 0) {
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
    //  @PreAuthorize("@ss.hasPermi('tNavigationPoint:id')")
    @ApiOperation("查询单条导线点数据")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tNavigationPointService.selectTNavigationPointById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTNavigationPointById")
    //  @PreAuthorize("@ss.hasPermi('tNavigationPoint:id')")
    @ApiOperation("删除单条导线点数据")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tNavigationPointService.deleteTNavigationPointById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTNavigationPointByIds")
    @ApiOperation("批量删除导线点数据")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tNavigationPointService.deleteTNavigationPointByIds(ids));
    }

    /**
     * 分页查询导线点列表
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TNavigationPoint> list = new ArrayList<>();
        list = this.tNavigationPointService.list();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @ApiOperation("导出导线点数据Excel")
    public void export(HttpServletResponse response, @RequestBody TNavigationPoint tNavigationPoint) {
        List<TNavigationPoint> list = this.tNavigationPointService.selectTNavigationPointList(tNavigationPoint);
        ExcelUtil<TNavigationPoint> util = new ExcelUtil<TNavigationPoint>(TNavigationPoint.class);
        util.exportExcel(response, list, "导线点数据");
    }

    /**
     * 导出excel模板
     *
     * @param response res
     */
    @PostMapping("exportTemplate")
    @ApiOperation("下载导线点Excel模板")
    public void exportTemplate(HttpServletResponse response) {
        TNavigationPoint tNavigationPoint = new TNavigationPoint();
        tNavigationPoint.setNavigationPointName("模板数据（同巷道下导线点不重复）");
        tNavigationPoint.setRoadway("模板数据（已知巷道名称）");
        tNavigationPoint.setX(88.8);
        tNavigationPoint.setY(88.8);
        tNavigationPoint.setZ(88.8);
        List<TNavigationPoint> list = new ArrayList<>();
        ExcelUtil<TNavigationPoint> util = new ExcelUtil<TNavigationPoint>(TNavigationPoint.class);
        util.exportExcel(response, list, "导线点数据");
    }

    /**
     * excel导入巷道信息
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/importData")
    @ApiOperation("导入导线点数据")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TNavigationPoint> util = new ExcelUtil<TNavigationPoint>(TNavigationPoint.class);
        List<TNavigationPoint> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TNavigationPoint tNavigationPoint : list) {
            BeanValidators.validateWithException(validator, tNavigationPoint);
            Result r = this.tNavigationPointService.insertTNavigationPoint(tNavigationPoint);
            if (r.getStatus() == 0) {
                successNum++;
            } else {
                failureNum++;
            }
        }
        return AjaxResult.success("添加成功" + successNum + "条," + failureNum + "条失败");
    }
}

