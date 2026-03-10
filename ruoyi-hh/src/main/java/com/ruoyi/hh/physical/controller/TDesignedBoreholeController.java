package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.annotation.LimitRequest;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import com.ruoyi.hh.hhhz.service.ITDesignParameterService;
import com.ruoyi.hh.physical.domain.TDesignedBorehole;
import com.ruoyi.hh.physical.service.ITDesDirectionalDrillingPointService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * (TDesignedBorehole)表控制层
 *
 * @author Eleven
 * @since 2022-07-13 10:55:16
 */
@RestController
@RequestMapping("tDesignedBorehole")
@Api(value = "DZZK设计普钻接口", tags = "DZZK设计普钻接口")
public class TDesignedBoreholeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDesignedBoreholeService tDesignedBoreholeService;
    @Resource
    protected Validator validator;
    @Resource
    private ITDesDirectionalDrillingPointService itDesDirectionalDrillingPointService;
    @Resource
    private ITDesignParameterService itDesignParameterService;

    /**
     * 获取列表
     *
     * @param tDesignedBorehole 要查询的信息
     * @return 返回结果
     */
    //@LimitRequest(type = "desBorehole")
    @PostMapping("list")
    @ApiOperation("按条件查询设计普钻钻孔列表接口")
    @Log(title = "钻孔设计", businessType = BusinessType.QUERY)
//    //  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:list')")
    public TableDataInfo selectAll(@RequestBody TDesignedBorehole tDesignedBorehole) {
        startPage();
        List<TDesignedBorehole> list = this.tDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
//        for(TDesignedBorehole tdb:list){
//            TDesDirectionalDrillingPoint tDesDirectionalDrillingPoint=new TDesDirectionalDrillingPoint();
//            tDesDirectionalDrillingPoint.setDesBoreholeId(tdb.getId());
//            List<TDesDirectionalDrillingPoint> lists=itDesDirectionalDrillingPointService.selectTDesDirectionalDrillingPointList(tDesDirectionalDrillingPoint);
//            tdb.setTDesDirectionalDrillingPointList(lists);
//        }
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     * 插入数据：检查组号和孔号是否重复
     *
     * @param tDesignedBorehole 单挑数据
     */
    @PostMapping("insert")
    @ApiOperation("新增单条设计普钻钻孔信息接口")
    @Log(title = "钻孔设计", businessType = BusinessType.INSERT)
//	//  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:insert')")
    public AjaxResult insertTDesignedBorehole(@RequestBody TDesignedBorehole tDesignedBorehole) {
        tDesignedBorehole.setSign(2);
        Result r = this.tDesignedBoreholeService.insertTDesignedBorehole(tDesignedBorehole);
//        if(r.getStatus()==0){
//            TDesignedBorehole tb=this.tDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole).get(0);
//            List<TDesDirectionalDrillingPoint> list=tDesignedBorehole.getTDesDirectionalDrillingPointList();
//            for(TDesDirectionalDrillingPoint tdd:list){
//                tdd.setDesBoreholeId(tb.getId());
//                itDesDirectionalDrillingPointService.insertTDesDirectionalDrillingPoint(tdd);
//            }
//        }
        if (r.getStatus() == 0) {
            return AjaxResult.success(r.getMessage());
        } else {
            return AjaxResult.error(r.getMessage());
        }
    }

//    @PostMapping("insertWithParamHighLevelDrilling")
//    @ApiOperation("插入钻孔设计数据带有高位钻孔设计参数")
//    @Log(title = "钻孔设计带有高位钻孔设计参数", businessType = BusinessType.INSERT)
//    public AjaxResult insertWithParamHighLevelDrilling(@RequestBody TParamHighLevelDrilling tParamHighLevelDrilling){
//        int result=itParamHighLevelDrillingService.insertTParamHighLevelDrilling(tParamHighLevelDrilling);
//        int id;
//        if(result>0){
//            id=itParamHighLevelDrillingService.selectTParamHighLevelDrillingList(tParamHighLevelDrilling).get(0).getId();
//            List<TDesignedBorehole> tDesignedBoreholes=tParamHighLevelDrilling.gettDesignedBoreholes();
//            for(TDesignedBorehole tDesignedBorehole:tDesignedBoreholes){
//                tDesignedBorehole.setParamhighlevelid(id);
//            }
//            insert(tDesignedBoreholes);
//        }
//        List<TParamHighLevelDrilling> all=this.itParamHighLevelDrillingService.selectTParamHighLevelDrillingList(tParamHighLevelDrilling);
//        return AjaxResult.success(this.itParamHighLevelDrillingService.getLastOne());
//    }
//
//
//    @PostMapping("insertWithTParamLayerDrilling")
//    @ApiOperation("插入钻孔设计数据带有顺层钻孔设计参数")
//    @Log(title = "钻孔设计带有顺层钻孔设计参数", businessType = BusinessType.INSERT)
//    public AjaxResult insertWithTParamLayerDrilling(@RequestBody TParamLayerDrilling tParamLayerDrilling){
//        int result=itParamLayerDrillingService.insertTParamLayerDrilling(tParamLayerDrilling);
//        int id;
//        if(result>0){
//            id=itParamLayerDrillingService.selectTParamLayerDrillingList(tParamLayerDrilling).get(0).getId();
//            List<TDesignedBorehole> tDesignedBoreholes=tParamLayerDrilling.gettDesignedBoreholes();
//            for(TDesignedBorehole tDesignedBorehole:tDesignedBoreholes){
//                tDesignedBorehole.setParamlayerid(id);
//            }
//            insert(tDesignedBoreholes);
//        }
//        List<TParamLayerDrilling> all=this.itParamLayerDrillingService.selectTParamLayerDrillingList(new TParamLayerDrilling());
//        return AjaxResult.success(this.itParamLayerDrillingService.getLastOne());
//    }
//
//
//    @PostMapping("insertWithTParamBandDrilling")
//    @ApiOperation("插入钻孔设计数据带有条带钻孔设计参数")
//    @Log(title = "钻孔设计带有条带钻孔设计参数", businessType = BusinessType.INSERT)
//    public AjaxResult insertWithTParamBandDrilling(@RequestBody TParamBandDrilling tParamBandDrilling){
//        int result=itParamBandDrillingService.insertTParamBandDrilling(tParamBandDrilling);
//        int id;
//        if(result>0){
//            id=itParamBandDrillingService.selectTParamBandDrillingList(tParamBandDrilling).get(0).getId();
//            List<TDesignedBorehole> tDesignedBoreholes=tParamBandDrilling.gettDesignedBoreholes();
//            for(TDesignedBorehole tDesignedBorehole:tDesignedBoreholes){
//                tDesignedBorehole.setParambandid(id);
//            }
//            insert(tDesignedBoreholes);
//        }
//        return AjaxResult.success(this.itParamBandDrillingService.getLastOne());
//    }
//
//
//    @PostMapping("insertWithTParamDirectionalDrilling")
//    @ApiOperation("插入钻孔设计数据带有定向钻孔设计参数")
//    @Log(title = "钻孔设计带有定向钻孔设计参数", businessType = BusinessType.INSERT)
//    public AjaxResult insertWithTParamDirectionalDrilling(@RequestBody TParamDirectionalDrilling tParamDirectionalDrilling){
//        int result=itParamDirectionalDrillingService.insertTParamDirectionalDrilling(tParamDirectionalDrilling);
//        int id;
//        if(result>0){
//            id=itParamDirectionalDrillingService.selectTParamDirectionalDrillingList(tParamDirectionalDrilling).get(0).getId();
//            List<TDesignedBorehole> tDesignedBoreholes=tParamDirectionalDrilling.gettDesignedBoreholes();
//            for(TDesignedBorehole tDesignedBorehole:tDesignedBoreholes){
//                tDesignedBorehole.setParamdirectionalid(id);
//            }
//            insert(tDesignedBoreholes);
//        }
//        return AjaxResult.success(this.itParamDirectionalDrillingService.getLastOne());
//    }


    /**
     * 批量插入数据
     * 批量插入数据：检查组号和孔号是否重复
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    @ApiOperation("批量插入设计普钻钻孔列表接口")
    @Log(title = "钻孔设计", businessType = BusinessType.INSERT)
//    //  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:insertList')")
    public AjaxResult insert(@RequestBody List<TDesignedBorehole> list) {
//        this.tDesignedBoreholeService.insertTDesignedBoreholeList(list);
        int suc = 0;
        int err = 0;
        List<String> exList = new ArrayList<>();
        if (list.isEmpty()) {
            return AjaxResult.error("提交的列表为空");
        }
        for (TDesignedBorehole tDesignedBorehole : list) {
            tDesignedBorehole.setSign(2);
            Result r = this.tDesignedBoreholeService.insertTDesignedBorehole(tDesignedBorehole);
            if (r.getStatus() == 0) {
                suc++;
            } else if (r.getStatus() == 1) {
                exList.add("组号：" + tDesignedBorehole.getGroupNo() + "、孔号" + tDesignedBorehole.getBoreholeNo());
            } else {
                err++;
                exList.add("组号：" + tDesignedBorehole.getGroupNo() + "、孔号" + tDesignedBorehole.getBoreholeNo()+r.getMessage());
            }
        }
        if (!exList.isEmpty() ||err>0) {
            return AjaxResult.error(300, "插入成功" + suc + "条,插入失败" + (err + exList.size()) + "条,其中" + exList.toString() + "重复");
        } else {
            return AjaxResult.error("更新成功" + suc + "条");
        }
    }


    /**
     * 修改
     * 更新数据：先检查更新的组号和孔号和原有的数据是否有重复的
     * @param tDesignedBorehole 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDesignedBorehole")
    @ApiOperation("修改设计普钻钻孔接口")
    @Transactional
    @Log(title = "钻孔设计", businessType = BusinessType.UPDATE)
    //  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:updateTDesignedBorehole')")
    public AjaxResult updateTRoadway(@RequestBody TDesignedBorehole tDesignedBorehole) {
        Result r = this.tDesignedBoreholeService.updateTDesignedBorehole(tDesignedBorehole);
        if (r.getStatus() == 0) {
            return AjaxResult.success("更新成功");
        } else if (r.getStatus() < 0) {
            return AjaxResult.error("更新失败");
        }
        return AjaxResult.success("组号和孔号重复");
    }

    /**
     * 批量更新设计钻孔信息
     *
     * @param list 请求内容
     * @return 结果
     */
    @PutMapping("updateTRoadwayList")
    @ApiOperation("批量修改设计普钻钻孔接口")
    @Transactional
    @Log(title = "钻孔设计", businessType = BusinessType.UPDATE)
    public AjaxResult updateTRoadwayList(@RequestBody List<TDesignedBorehole> list) {
        int suc = 0;
        List<String> exList = new ArrayList<>();
        List<String> sucList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (TDesignedBorehole tDesignedBorehole : list) {
                Result r = this.tDesignedBoreholeService.updateTDesignedBorehole(tDesignedBorehole);
                if (r.getStatus() > 0) {
                    exList.add("组号：" + tDesignedBorehole.getGroupNo() + "，孔号" + tDesignedBorehole.getBoreholeNo());
                } else if (r.getStatus() == 0) {
                    sucList.add(tDesignedBorehole.getGroupNo() + ":" + tDesignedBorehole.getBoreholeNo());
                }
            }
            if (!exList.isEmpty()) {
                return AjaxResult.error(300, "更新成功" + sucList.size() + "条数据，更新失败" + exList.size() + "条，失败列表：" + exList.toString());
            } else {
                return AjaxResult.success("更新成功" + list.size() + "条数据");
            }
        }
        return AjaxResult.error("提交的信息为空");
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    @ApiOperation("根据ID获取设计普钻钻孔接口")
    @Log(title = "钻孔设计", businessType = BusinessType.QUERY)
//    //  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:selectOneById')")
    public AjaxResult selectOne(@RequestParam int id) {
        TDesignedBorehole tDesignedBorehole = this.tDesignedBoreholeService.selectTDesignedBoreholeById(id);
        return AjaxResult.success(tDesignedBorehole);
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDesignedBoreholeById")
    @ApiOperation("根据ID删除设计普钻钻孔接口")
    @Log(title = "钻孔设计", businessType = BusinessType.DELETE)
//	//  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:deleteTDesignedBoreholeById')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tDesignedBoreholeService.deleteTDesignedBoreholeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    @Log(title = "钻孔设计", businessType = BusinessType.QUERY)
    public TableDataInfo getPageList() {
        startPage();
        List<TDesignedBorehole> list = new ArrayList<>();
        list = this.tDesignedBoreholeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDesignedBoreholeByIds")
    @ApiOperation("根据ID删除设计普钻钻孔接口")
    @Log(title = "钻孔设计", businessType = BusinessType.DELETE)
//    //  @PreAuthorize("@ss.hasPermi('tDesignedBorehole:deleteTDesignedBoreholeByIds')")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tDesignedBoreholeService.deleteTDesignedBoreholeByIds(ids));
    }

    @GetMapping("getByRoadWayIdAndpurposeboreId")
    public AjaxResult getByRoadWayIdAndpurposeboreId(@RequestParam int roadwayId, @RequestParam int purposeboreholeId) {
        return AjaxResult.success(this.tDesignedBoreholeService.getByRoadWayIdAndpurposeboreId(roadwayId, purposeboreholeId));
    }

    /**
     * 测试：删除所有信息
     *
     * @return 结果
     */
    @DeleteMapping("delAll")
    @Log(title = "钻孔设计", businessType = BusinessType.CLEAN)
    public AjaxResult delAll() {
        return AjaxResult.success(this.tDesignedBoreholeService.delAll());
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    @Log(title = "钻孔设计", businessType = BusinessType.EXPORT)
    public void export(HttpServletResponse response, @RequestBody TDesignedBorehole tDesignedBorehole) {
        List<TDesignedBorehole> list = this.tDesignedBoreholeService.selectTDesignedBoreholeList(tDesignedBorehole);
        ExcelUtil<TDesignedBorehole> util = new ExcelUtil<TDesignedBorehole>(TDesignedBorehole.class);
        util.exportExcel(response, list, "设计钻孔数据");
    }


    @PostMapping("/importData")
    @Log(title = "钻孔设计", businessType = BusinessType.IMPORT)
    @Transactional
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TDesignedBorehole> util = new ExcelUtil<TDesignedBorehole>(TDesignedBorehole.class);
        List<TDesignedBorehole> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TDesignedBorehole tDesignedBorehole : list) {
            BeanValidators.validateWithException(validator, tDesignedBorehole);
            Result r = this.tDesignedBoreholeService.insertTDesignedBorehole(tDesignedBorehole);
            if (r.getStatus() == 0) {
                successNum++;
            } else if (r.getStatus() == 1) {
                failureNum++;
            }
        }
        return AjaxResult.success("添加" + successNum + "条," + failureNum + "条已存在,");
    }

    /**
     * 删除跟巷道有关的
     */
    @ApiOperation("删除单条巷道关联的钻孔")
    @DeleteMapping("delByRoadWayId")
    @Log(title = "钻孔设计", businessType = BusinessType.DELETE)
    public Integer delByRoadWayId(@RequestParam Integer roadwayId) {
        return this.tDesignedBoreholeService.delByRoadWayId(roadwayId);
    }

    /**
     * 删除跟巷道相关的钻孔
     */
    @ApiOperation("批量删除多条巷道关联的钻孔")
    @DeleteMapping("delByRoadWayIds")
    @Log(title = "钻孔设计", businessType = BusinessType.DELETE)
    public Integer delByRoadWayIds(@RequestParam List<Integer> roadwayIds) {
        return this.tDesignedBoreholeService.delByRoadWayIds(roadwayIds);
    }

    /**
     * 更改设计钻孔状态（确定设计方案）
     */
    @ApiOperation("更改设计钻孔状态（确定设计方案）")
    @PostMapping("makeSureDesProgramme")
    public void makeSureDesProgramme(@RequestBody List<DesignParameters> designParameters
                                     ){
        List<Integer> ids=new ArrayList<>();
        for(DesignParameters dep:designParameters){
            ids.add(dep.getId());
        }
        if(ids.size()>0){
            this.itDesignParameterService.updateSign(1,ids);
        }
//        List<Integer> boreholeIds=new ArrayList<>();
//        List<Integer> paramsLayerid=new ArrayList<>();
//        List<Integer> paramsHighLevelid=new ArrayList<>();
//        List<Integer> paramsBandid=new ArrayList<>();
//        List<Integer> paramsDirectionalid=new ArrayList<>();
//        for(TParamHighLevelDrilling tParamHighLevelDrilling:requestData.getHighLevelDrillings()){
//            boreholeIds.addAll(this.tDesignedBoreholeService.getBoreholeIdByParam(0,tParamHighLevelDrilling.getId(),0,0));
//            paramsHighLevelid.add(tParamHighLevelDrilling.getId());
//        }
//        for(TParamLayerDrilling tParamLayerDrilling:requestData.getLayerDrilling()){
//            boreholeIds.addAll(this.tDesignedBoreholeService.getBoreholeIdByParam(0,0,tParamLayerDrilling.getId(),0));
//            paramsLayerid.add(tParamLayerDrilling.getId());
//        }
//        for(TParamBandDrilling tParamBandDrilling:requestData.getBand()){
//            boreholeIds.addAll(this.tDesignedBoreholeService.getBoreholeIdByParam(tParamBandDrilling.getId(),0,0,0));
//            paramsBandid.add(tParamBandDrilling.getId());
//        }
//        for(TParamDirectionalDrilling tParamDirectionalDrilling:requestData.getDirectional()){
//            boreholeIds.addAll(this.tDesignedBoreholeService.getBoreholeIdByParam(0,0,0,tParamDirectionalDrilling.getId()));
//            paramsDirectionalid.add(tParamDirectionalDrilling.getId());
//        }
//        if(boreholeIds.size()>0){
//            this.tDesignedBoreholeService.makeSureDesProgramme(boreholeIds);
//        }

//        if(paramsLayerid.size()>0){
//            this.itParamLayerDrillingService.updateSign(paramsLayerid);
//        }
//        if(paramsBandid.size()>0){
//            this.itParamBandDrillingService.updateSign(paramsBandid);
//        }
//        if(paramsDirectionalid.size()>0){
//            this.itParamDirectionalDrillingService.updateSign(paramsDirectionalid);
//        }
//        if(paramsHighLevelid.size()>0){
//            this.itParamHighLevelDrillingService.updateSign(paramsHighLevelid);
//        }
    }

    /**
     * 根据工作面ID查询设计参数列表
     * @param id 工作面ID
     * @return 结果
     */
    @ApiOperation("根据工作面ID查询设计参数列表")
    @PostMapping("getParamByWorkFaceId_No")
    public AjaxResult getParamByWorkFaceId_No(Integer id){
//        TParamHighLevelDrilling tParamHighLevelDrilling=new TParamHighLevelDrilling();
//        tParamHighLevelDrilling.setWorkingFaceId(id);
//        List<TParamHighLevelDrilling> tParamHighLevelDrillings= this.itParamHighLevelDrillingService.selectTParamHighLevelDrillingList(tParamHighLevelDrilling);
//        TParamBandDrilling tParamBandDrilling=new TParamBandDrilling();
//        tParamBandDrilling.setWorkingFaceId(id);
//        List<TParamBandDrilling> tParamBandDrillings=this.itParamBandDrillingService.selectTParamBandDrillingList(tParamBandDrilling);
//        TParamDirectionalDrilling tParamDirectionalDrilling=new TParamDirectionalDrilling();
//        List<TParamDirectionalDrilling> tParamDirectionalDrillings=this.itParamDirectionalDrillingService.selectTParamDirectionalDrillingList(tParamDirectionalDrilling);
//        TParamLayerDrilling tParamLayerDrilling=new TParamLayerDrilling();
//        List<TParamLayerDrilling> tParamLayerDrillings=this.itParamLayerDrillingService.selectTParamLayerDrillingList(tParamLayerDrilling);
//        RequestData requestData=new RequestData();
//        requestData.setBand(tParamBandDrillings);
//        requestData.setDirectional(tParamDirectionalDrillings);
//        requestData.setHighLevelDrillings(tParamHighLevelDrillings);
//        requestData.setLayerDrilling(tParamLayerDrillings);
//        return AjaxResult.success(requestData);
        DesignParameters designParameters=new DesignParameters();
        designParameters.setWorkfaceId(id);
        return AjaxResult.success(this.itDesignParameterService.selectTDesignParametersList(designParameters));
    }

    /**
     * 根据工作面ID查询设计参数列表
     * @param id 工作面ID
     * @return 结果
     */
    @PostMapping("getParamByWorkFaceId_Sure")
    public AjaxResult getParamByWorkFaceId_Sure(Integer id){
//        TParamHighLevelDrilling tParamHighLevelDrilling=new TParamHighLevelDrilling();
//        tParamHighLevelDrilling.setWorkingFaceId(id);
//        List<TParamHighLevelDrilling> tParamHighLevelDrillings= this.itParamHighLevelDrillingService.selectTParamHighLevelDrillingList(tParamHighLevelDrilling);
//        tParamHighLevelDrillings.removeIf(tParamHighLevelDrilling1 -> !tParamHighLevelDrilling1.getSign().equals("1"));
//        TParamBandDrilling tParamBandDrilling=new TParamBandDrilling();
//        tParamBandDrilling.setWorkingFaceId(id);
//        List<TParamBandDrilling> tParamBandDrillings=this.itParamBandDrillingService.selectTParamBandDrillingList(tParamBandDrilling);
//        tParamBandDrillings.removeIf(tParamBandDrilling1 -> !tParamBandDrilling1.getSign().equals("1"));
//        TParamDirectionalDrilling tParamDirectionalDrilling=new TParamDirectionalDrilling();
//        List<TParamDirectionalDrilling> tParamDirectionalDrillings=this.itParamDirectionalDrillingService.selectTParamDirectionalDrillingList(tParamDirectionalDrilling);
//        tParamDirectionalDrillings.removeIf(tParamDirectionalDrilling1 -> !tParamDirectionalDrilling1.getSign().equals("1"));
//        TParamLayerDrilling tParamLayerDrilling=new TParamLayerDrilling();
//        List<TParamLayerDrilling> tParamLayerDrillings=this.itParamLayerDrillingService.selectTParamLayerDrillingList(tParamLayerDrilling);
//        tParamLayerDrillings.removeIf(tParamLayerDrilling1 -> !tParamLayerDrilling1.getSign().equals("1"));
//        RequestData requestData=new RequestData();
//        requestData.setBand(tParamBandDrillings);
//        requestData.setDirectional(tParamDirectionalDrillings);
//        requestData.setHighLevelDrillings(tParamHighLevelDrillings);
//        requestData.setLayerDrilling(tParamLayerDrillings);
//        return AjaxResult.success(requestData);

        DesignParameters designParameters=new DesignParameters();
        designParameters.setWorkfaceId(id);
        List<DesignParameters> designParametersList=this.itDesignParameterService.selectTDesignParametersList(designParameters);
        designParametersList.removeIf(dp ->!dp.getSign().equals("1"));
        return AjaxResult.success(designParametersList);
    }



}

