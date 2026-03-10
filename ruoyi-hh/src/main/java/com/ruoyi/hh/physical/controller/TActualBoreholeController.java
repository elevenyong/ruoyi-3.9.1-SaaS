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
import com.ruoyi.hh.business.domain.TSealingGrouting;
import com.ruoyi.hh.physical.domain.TActualBorehole;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.service.ITActualBoreholeService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * (TActualBorehole)表控制层
 * 废弃，实钻使用TBkActBoreholeInfo
 * @author Eleven
 * @since 2022-07-13 15:07:42
 */
@RestController
//@RequestMapping("tActualBorehole")
//@Api(value = "实钻数据Controller", tags = {"实钻相关接口"})
@Deprecated
public class TActualBoreholeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITActualBoreholeService tActualBoreholeService;
    @Resource
    private ITDesignedBoreholeService tDesignedBoreholeService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tActualBorehole 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    @ApiOperation("查询实钻列表")
    @Log(title = "实钻数据", businessType = BusinessType.QUERY)
    //  @PreAuthorize("@ss.hasPermi('tActualBorehole:list')")
    public TableDataInfo selectAll(@RequestBody TActualBorehole tActualBorehole) {
        startPage();
        List<TActualBorehole> list = this.tActualBoreholeService.selectTActualBoreholeList(tActualBorehole);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tActualBorehole 单挑数据
     */
    @PostMapping("insert")
    @ApiOperation("插入单条数据")
    @Log(title = "实钻数据", businessType = BusinessType.INSERT)
    //  @PreAuthorize("@ss.hasPermi('tActualBorehole:insert')")
    public AjaxResult insertTActualBorehole(@RequestBody TActualBorehole tActualBorehole) {
        Result result = this.tActualBoreholeService.insertTActualBorehole(tActualBorehole);
        if (result.getStatus() != 0) {
            return AjaxResult.error(result.getMessage());
        } else {
            return AjaxResult.success(result.getMessage());
        }

    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @ApiOperation("批量插入数据")
    @Log(title = "实钻数据", businessType = BusinessType.INSERT)
    //  @PreAuthorize("@ss.hasPermi('tActualBorehole:insert')")
    public AjaxResult insert(@RequestBody List<TActualBorehole> list) {
        int s = 0;
        int e = 0;
        int r = 0;
        if (list.size() <= 0) {
            return AjaxResult.error("内容为空,添加失败");
        }
//        for (TActualBorehole t : this.tActualBoreholeService.getPageList()) {
//            TDesignedBorehole tDesignedBorehole = new TDesignedBorehole();
//            tDesignedBorehole.setGroupid(t.getGroupid());
//            tDesignedBorehole.setBoreholeno(t.getBoreholeno());
//            List<TDesignedBorehole> dlist = this.tDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
//            TDesignedBorehole tD;
//            if (dlist.size() > 0) {
//                tD = dlist.get(0);
//                t.setAdjacentboreholeno(tD.getAdjacentboreholeno());
//                this.tActualBoreholeService.updateTActualBorehole(t);
//            }
//        }
        for (TActualBorehole t : list) {
            Result res = this.tActualBoreholeService.insertTActualBorehole(t);
            if (res.getStatus() == 0) {
                TDesignedBorehole tDesignedBorehole = new TDesignedBorehole();
                tDesignedBorehole.setGroupNo(t.getGroupNo());
                tDesignedBorehole.setBoreholeNo(t.getBoreholeNo());
                List<TDesignedBorehole> dlist = this.tDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
                TDesignedBorehole tD;
                if (dlist.size() > 0) {
                    tD = dlist.get(0);
                    t.setAdjacentBoreholeNo(tD.getAdjacentBoreholeNo());
                }
                //this.insertTActualBorehole(t);
                s++;
            } else if (res.getStatus() == 1) {
                r++;
            } else {
                e++;
            }

        }
        return AjaxResult.success("添加成功" + s + "条，重复" + r + "条，失败" + e);
    }


    /**
     * 修改
     *
     * @param tActualBorehole 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTActualBorehole")
    @ApiOperation("更新数据")
    @Log(title = "实钻数据", businessType = BusinessType.UPDATE)
    //  @PreAuthorize("@ss.hasPermi('tActualBorehole:updateTActualBorehole')")
    public AjaxResult updateTRoadway(@RequestBody TActualBorehole tActualBorehole) {
        return AjaxResult.success(this.tActualBoreholeService.updateTActualBorehole(tActualBorehole));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @ApiOperation("根据ID查询实钻数据")
    //  @PreAuthorize("@ss.hasPermi('tActualBorehole:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tActualBoreholeService.selectTActualBoreholeById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTActualBoreholeById")
    @ApiOperation("根据ID删除实钻信息")
    @Log(title = "实钻数据", businessType = BusinessType.DELETE)
    //  @PreAuthorize("@ss.hasPermi('tActualBorehole:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tActualBoreholeService.deleteTActualBoreholeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTActualBoreholeByIds")
    @ApiOperation("根据ID列表删除实钻信息")
    @Log(title = "实钻数据", businessType = BusinessType.DELETE)
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tActualBoreholeService.deleteTActualBoreholeByIds(ids));
    }

    /**
     * 分页查询列表
     *
     * @return 列表
     */

    @PostMapping("getPageList")
    @ApiOperation("分页查询实钻列表")
    public TableDataInfo getPageList() {
        startPage();
        List<TActualBorehole> list = this.tActualBoreholeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 查询实钻原始验收单
     *
     * @param roadwayId  巷道ID
     * @param groupNo    组号
     * @param boreholeNo 孔号
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @return 列表
     * @throws ParseException
     */
    @PostMapping("getAcceptanceCertificate")
    @ApiOperation("查询实钻原始验收单")
    public TableDataInfo getAcceptanceCertificate(@RequestParam(value = "roadwayId", required = false) Integer roadwayId,
                                                  @RequestParam(value = "groupNo", required = false) String groupNo,
                                                  @RequestParam(value = "boreholeNo", required = false) List<Integer> boreholeNo,
                                                  @RequestParam(value = "startDate", required = false) Date startDate,
                                                  @RequestParam(value = "endDate", required = false) Date endDate
    ) throws ParseException {
        startPage();
        if (startDate != null && endDate == null) {
            endDate = new Date();
        }
        List<TActualBorehole> list = this.tActualBoreholeService.getAcceptanceCertificate(roadwayId, groupNo, boreholeNo,
                startDate, endDate);
        return getDataTable(list);
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @Log(title = "实钻数据", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestBody TActualBorehole tActualBorehole) {
        List<TActualBorehole> list = this.tActualBoreholeService.selectTActualBoreholeList(tActualBorehole);
        ExcelUtil<TActualBorehole> util = new ExcelUtil<TActualBorehole>(TActualBorehole.class);
        util.exportExcel(response, list, "实钻数据");
    }

    /**
     * 导入excel
     *
     * @param response
     */
    @PostMapping("exportTemplate")
    public void ex(HttpServletResponse response) {
        List<TActualBorehole> list = new ArrayList<TActualBorehole>();
        ExcelUtil<TActualBorehole> util = new ExcelUtil<TActualBorehole>(TActualBorehole.class);
        util.exportExcel(response, list, "实钻数据");
    }


    /**
     * 导入数据
     *
     * @param file 路径
     * @return 结果
     * @throws Exception 异常
     */
    @PostMapping("/importData")
    @Log(title = "实钻数据", businessType = BusinessType.IMPORT)
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        int reNum = 0;
        ExcelUtil<TActualBorehole> util = new ExcelUtil<TActualBorehole>(TActualBorehole.class);
        List<TActualBorehole> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TActualBorehole tActualBorehole : list) {
            System.err.println(tActualBorehole.getAdjacentBoreholeNo());
            BeanValidators.validateWithException(validator, tActualBorehole);
            Result r = this.tActualBoreholeService.insertTActualBorehole(tActualBorehole);
            if (r.getStatus() == 0) {
                successNum++;
            } else if (r.getStatus() == 1) {
                reNum++;
            } else {
                failureNum++;
            }
        }
        return AjaxResult.success("添加" + successNum + "条," + reNum + "条已存在," + failureNum + "添加失败");
    }

    @DeleteMapping("delByRoadWayId")
    public AjaxResult delByBoreholeId(int roadwayId) {
        return AjaxResult.success(this.tActualBoreholeService.delByRoadWayId(roadwayId));
    }

    @DeleteMapping("delByRoadWayIds")
    public AjaxResult delByBoreholeIds(@RequestParam("roadwayIds") List<Integer> roadwayIds) {
        return AjaxResult.success(this.tActualBoreholeService.delByRoadWayIds(roadwayIds));
    }

//    /**
//     * 获取钻孔信息
//     *
//     * @param roadWayId         巷道ID
//     * @param groupNo           组号
//     * @param purposeBoreholeId 钻孔用途ID
//     * @param startdate         开始时间
//     * @param enddate           结束时间
//     * @return 列表
//     */
//    @PostMapping("getBoreholeInfo")
//    public TableDataInfo getBoreholeInfo(@RequestParam(value = "roadWayId", required = false) Integer roadWayId,
//                                         @RequestParam(value = "groupNo", required = false) List<String> groupNo,
//                                         @RequestParam(value = "purposeBoreholeId", required = false) List<Integer> purposeBoreholeId,
//                                         @RequestParam(value = "boreholeno", required = false) List<Integer> boreholeno,
//                                         @RequestParam(required = false, value = "startdate") Date startdate,
//                                         @RequestParam(required = false, value = "enddate") Date enddate
//    ) throws ParseException {
//        startPage();
//        if (startdate != null && enddate == null) {
//            enddate = new Date();
//        }
//        List<TGasDrillingStandingBook> list = this.tActualBoreholeService.getBoreholeInfo(roadWayId, groupid, purposeBoreholeId, boreholeno,
//                startdate, enddate);
//        return getDataTable(list);
//
//    }

    /**
     * 根据巷道Id查询组号列表
     *
     * @param roadwayId 巷道ID
     * @return 列表
     */
    @PostMapping("getGroupIdsByRoadwayId")
    public AjaxResult getGroupIdsByRoadwayId(@RequestParam("roadwayId") Integer roadwayId) {
        return AjaxResult.success(this.tActualBoreholeService.getGroupNosByRoadwayId(roadwayId));
    }

    /**
     * 根据巷道ID和组号查询孔号
     *
     * @param roadwayId 巷道ID
     * @param groupNo   组号
     * @return 孔号列表
     */
    @PostMapping("getBoreholenoByRoadwayIdAndGroupId")
    public List<Integer> getBoreholenoByRoadwayIdAndGroupId(@RequestParam(value = "roadwayId") Integer roadwayId,
                                                            @RequestParam(value = "groupNo") String groupNo) {
        return this.tActualBoreholeService.getBoreholenoByRoadwayIdAndGroupNo(roadwayId, groupNo);
    }

    /**
     * 根据巷道ID和组号查询孔号及对应的自增ID
     *
     * @param roadwayId 巷道ID
     * @param groupNo   组号
     * @return 列表
     */
    @PostMapping("getIdAndBoreholeNo")
    public List<TSealingGrouting> getIdAndBoreholeNo(@RequestParam(value = "roadwayId") Integer roadwayId,
                                                     @RequestParam(value = "groupNo") String groupNo) {
        return this.tActualBoreholeService.getIdAndBoreholeNo(roadwayId, groupNo);
    }

    @GetMapping("getByWorkfaceId")
    public List<TActualBorehole> getByWorkfaceId(Integer workfaceId){
        return this.tActualBoreholeService.getByWorkfaceId(workfaceId);
    }
}

