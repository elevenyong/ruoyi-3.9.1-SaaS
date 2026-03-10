package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TDesSchemeInfo;
import com.ruoyi.hh.business.service.ITDesSchemeInfoService;
import com.ruoyi.hh.hhhz.service.ITDesignParameterService;
import com.ruoyi.hh.physical.controller.TDesignedBoreholeController;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计方案信息(TDesSchemeInfo)表控制层
 *
 * @author eleven
 * @since 2023-06-30 14:31:02
 */
@RestController
@RequestMapping("tDesSchemeInfo")
public class TDesSchemeInfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDesSchemeInfoService tDesSchemeInfoService;
//    @Resource
//    private ITParamBandDrillingService itParamBandDrillingService;
//    @Resource
//    private ITParamDirectionalDrillingService itParamDirectionalDrillingService;
//    @Resource
//    private ITParamHighLevelDrillingService itParamHighLevelDrillingService;
//    @Resource
//    private ITParamLayerDrillingService itParamLayerDrillingService;
    @Resource
    private ITDesignParameterService itDesignParameterService;
    @Resource
    private TDesignedBoreholeController tDesignedBoreholeController;
    @Resource
    private ITDesignedBoreholeService itDesignedBoreholeService;

    /**
     * 获取列表
     *
     * @param tDesSchemeInfo  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDesSchemeInfo:list')")
    public TableDataInfo selectAll(@RequestBody TDesSchemeInfo tDesSchemeInfo) {
        startPage();
        List<TDesSchemeInfo> list =this.tDesSchemeInfoService.selectTDesSchemeInfoList(tDesSchemeInfo);
        for(TDesSchemeInfo tds:list){
//            //遍历所有的设计参数，找到设计参数中的SchemeId，看是否含有该方案的ID，有的话，保存并返回
//            List<TParamHighLevelDrilling> tParamHighLevelDrillings=this.itParamHighLevelDrillingService.selectTParamHighLevelDrillingList(new TParamHighLevelDrilling());
//            tParamHighLevelDrillings.removeIf(tParamHighLevelDrilling -> !isIn(tParamHighLevelDrilling.getSchemeId().split(","), tds.getId().toString()));
//            List<TParamBandDrilling> tParamBandDrillings=this.itParamBandDrillingService.selectTParamBandDrillingList(new TParamBandDrilling());
//            tParamBandDrillings.removeIf(tParamBandDrilling->!isIn(tParamBandDrilling.getSchemeId().split(","),tds.getId().toString()));
//            List<TParamDirectionalDrilling> tParamDirectionalDrillings=this.itParamDirectionalDrillingService.selectTParamDirectionalDrillingList(new TParamDirectionalDrilling());
//            tParamDirectionalDrillings.removeIf(tParamDirectionalDrilling->!isIn(tParamDirectionalDrilling.getSchemeId().split(","),tds.getId().toString()));
//            List<TParamLayerDrilling> tParamLayerDrillings=this.itParamLayerDrillingService.selectTParamLayerDrillingList(new TParamLayerDrilling());
//            tParamLayerDrillings.removeIf(tParamLayerDrilling->!isIn(tParamLayerDrilling.getSchemeId().split(","),tds.getId().toString()));
//            tds.setBandDrillings(tParamBandDrillings);
//            tds.setDirectionalDrillings(tParamDirectionalDrillings);
//            tds.setHighLevelDrillings(tParamHighLevelDrillings);
//            tds.setLayerDrillings(tParamLayerDrillings);



//            List<DesignParameters> designParameters=tDesSchemeInfoService.getByDesSchemeId(tds.getId());
//            tds.setDesignParameters(designParameters);
        }
        return getDataTable(list);

    }

    /**
     * 确定方案
     * @param id 方案ID
     */
    @PostMapping("masureDesScheme")
    public void masureDesScheme(Integer id){
//        TDesSchemeInfo tDesSchemeInfo=this.tDesSchemeInfoService.selectTDesSchemeInfoById(id);
//        tDesSchemeInfo.setSign("1");
//        itDesignedBoreholeService.makeSureDesProgramme(tDesSchemeInfoService.getByDesSchemeId(id));
//        this.tDesSchemeInfoService.updateTDesSchemeInfo(tDesSchemeInfo);
        this.tDesSchemeInfoService.masureDesScheme(id);
    }
    
    /**
     * 插入单条数据
     *
     * @param tDesSchemeInfo 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDesSchemeInfo:insert')")
    public AjaxResult insertTDesSchemeInfo (@RequestBody TDesSchemeInfo tDesSchemeInfo) {

//        Integer id=this.tDesSchemeInfoService.selectTDesSchemeInfoList(tDesSchemeInfo).get(0).getId();
//        List<DesignParameters> list=tDesSchemeInfo.getDesignParameters();
//        for(DesignParameters dp:list){
//            DesignParameters d=this.itDesignParameterService.selectDesignParametersById(dp.getId());
//            this.itDesignParameterService.updateDesignParameters(appendSchemeId(d,id));
//        }
        return AjaxResult.success(this.tDesSchemeInfoService.insertTDesSchemeInfo(tDesSchemeInfo));
//        List<TParamHighLevelDrilling> tParamHighLevelDrillings= tDesSchemeInfo.getHighLevelDrillings();
//        for(TParamHighLevelDrilling tphd:tParamHighLevelDrillings){
//            TParamHighLevelDrilling td=this.itParamHighLevelDrillingService.selectTParamHighLevelDrillingById(tphd.getId());
//            this.itParamHighLevelDrillingService.updateTParamHighLevelDrilling(highLevelappendSchemeId(td,id));
//        }
//        List<TParamBandDrilling> tParamBandDrillings=tDesSchemeInfo.getBandDrillings();
//        for(TParamBandDrilling tpbd:tParamBandDrillings){
//            TParamBandDrilling td=this.itParamBandDrillingService.selectTParamBandDrillingById(tpbd.getId());
//            this.itParamBandDrillingService.updateTParamBandDrilling(bandappendSchemeId(td,id));
//        }
//        List<TParamDirectionalDrilling> tParamDirectionalDrillings=tDesSchemeInfo.getDirectionalDrillings();
//        for(TParamDirectionalDrilling tpdd:tParamDirectionalDrillings){
//            TParamDirectionalDrilling tdd=this.itParamDirectionalDrillingService.selectTParamDirectionalDrillingById(tpdd.getId());
//            this.itParamDirectionalDrillingService.updateTParamDirectionalDrilling(directionalSchemeId(tdd,id));
//        }
//        List<TParamLayerDrilling> tParamLayerDrillings=tDesSchemeInfo.getLayerDrillings();
//        for(TParamLayerDrilling tpld:tParamLayerDrillings){
//            TParamLayerDrilling td=this.itParamLayerDrillingService.selectTParamLayerDrillingById(tpld.getId());
//            this.itParamLayerDrillingService.updateTParamLayerDrilling(layerSchemeId(td,id));
//        }
//        return AjaxResult.success("保存成功");
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDesSchemeInfo:insert')")
    public void insert(@RequestBody List<TDesSchemeInfo> list){
        if(list.size()>0){
            for(TDesSchemeInfo td:list){
                this.tDesSchemeInfoService.insertTDesSchemeInfo(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDesSchemeInfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDesSchemeInfo")
    // @PreAuthorize("@ss.hasPermi('tDesSchemeInfo:updateTDesSchemeInfo')")
    public AjaxResult updateTDesSchemeInfo(@RequestBody TDesSchemeInfo tDesSchemeInfo) {
        return AjaxResult.success(this.tDesSchemeInfoService.updateTDesSchemeInfo(tDesSchemeInfo));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDesSchemeInfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDesSchemeInfoService.selectTDesSchemeInfoById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDesSchemeInfoById")
    // @PreAuthorize("@ss.hasPermi('tDesSchemeInfo:id')")
    public AjaxResult deleteTDesSchemeInfoById(@RequestParam int id) {
        int result = this.tDesSchemeInfoService.deleteTDesSchemeInfoById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDesSchemeInfoByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDesSchemeInfoService.deleteTDesSchemeInfoByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDesSchemeInfo tDesSchemeInfo) {
        List<TDesSchemeInfo> list=this.tDesSchemeInfoService.selectTDesSchemeInfoList(tDesSchemeInfo);
        ExcelUtil<TDesSchemeInfo> util = new ExcelUtil<TDesSchemeInfo>(TDesSchemeInfo.class);
        util.exportExcel(response, list, "抽采单元数据");
    }



//    /**
//     * 高位钻孔追加方案ID
//     * @param tParamHighLevelDrilling
//     * @param schemeId
//     * @return
//     */
//    public TParamHighLevelDrilling highLevelappendSchemeId(TParamHighLevelDrilling tParamHighLevelDrilling,Integer schemeId){
//        if(!"".equals(tParamHighLevelDrilling.getSchemeId())){
//            tParamHighLevelDrilling.setSchemeId(tParamHighLevelDrilling.getSchemeId() +","+ schemeId);
//        }else{
//            tParamHighLevelDrilling.setSchemeId(schemeId.toString());
//        }
//        return tParamHighLevelDrilling;
//    }
//
//    /**
//     * 顺层钻孔追加方案ID
//     * @param tParamBandDrilling
//     * @param schemeId
//     * @return
//     */
//    public TParamBandDrilling bandappendSchemeId(TParamBandDrilling tParamBandDrilling,Integer schemeId){
//        if(!"".equals(tParamBandDrilling.getSchemeId())){
//            tParamBandDrilling.setSchemeId(tParamBandDrilling.getSchemeId() +","+ schemeId);
//        }else{
//            tParamBandDrilling.setSchemeId(schemeId.toString());
//        }
//        return tParamBandDrilling;
//    }
//
//    /**
//     * 顺层钻孔追加方案ID
//     * @param tParamDirectionalDrilling
//     * @param schemeId
//     * @return
//     */
//    public TParamDirectionalDrilling directionalSchemeId(TParamDirectionalDrilling tParamDirectionalDrilling,Integer schemeId){
//        if(!"".equals(tParamDirectionalDrilling.getSchemeId())){
//            tParamDirectionalDrilling.setSchemeId(tParamDirectionalDrilling.getSchemeId() +","+ schemeId);
//        }else{
//            tParamDirectionalDrilling.setSchemeId(schemeId.toString());
//        }
//        return tParamDirectionalDrilling;
//    }
//
//    /**
//     * 顺层钻孔追加方案ID
//     * @param tParamLayerDrilling
//     * @param schemeId
//     * @return
//     */
//    public TParamLayerDrilling layerSchemeId(TParamLayerDrilling tParamLayerDrilling,Integer schemeId){
//        if(!"".equals(tParamLayerDrilling.getSchemeId())){
//            tParamLayerDrilling.setSchemeId(tParamLayerDrilling.getSchemeId() +","+ schemeId);
//        }else{
//            tParamLayerDrilling.setSchemeId(schemeId.toString());
//        }
//        return tParamLayerDrilling;
//    }


//    /**
//     * 根据方案ID查询设计参数列表
//     * @param id 方案ID
//     * @return
//     */
//    public RequestData getByDesSchemeId(Integer id){
//        RequestData requestData=new RequestData();
//        //遍历所有的设计参数，找到设计参数中的SchemeId，看是否含有该方案的ID，有的话，保存并返回
//        List<TParamHighLevelDrilling> tParamHighLevelDrillings=this.itParamHighLevelDrillingService.selectTParamHighLevelDrillingList(new TParamHighLevelDrilling());
//        tParamHighLevelDrillings.removeIf(tParamHighLevelDrilling -> !isIn(tParamHighLevelDrilling.getSchemeId().split(","), id.toString()));
//        List<TParamBandDrilling> tParamBandDrillings=this.itParamBandDrillingService.selectTParamBandDrillingList(new TParamBandDrilling());
//        tParamBandDrillings.removeIf(tParamBandDrilling->!isIn(tParamBandDrilling.getSchemeId().split(","),id.toString()));
//        List<TParamDirectionalDrilling> tParamDirectionalDrillings=this.itParamDirectionalDrillingService.selectTParamDirectionalDrillingList(new TParamDirectionalDrilling());
//        tParamDirectionalDrillings.removeIf(tParamDirectionalDrilling->!isIn(tParamDirectionalDrilling.getSchemeId().split(","),id.toString()));
//        List<TParamLayerDrilling> tParamLayerDrillings=this.itParamLayerDrillingService.selectTParamLayerDrillingList(new TParamLayerDrilling());
//        tParamLayerDrillings.removeIf(tParamLayerDrilling->!isIn(tParamLayerDrilling.getSchemeId().split(","),id.toString()));
//        requestData.setLayerDrilling(tParamLayerDrillings);
//        requestData.setHighLevelDrillings(tParamHighLevelDrillings);
//        requestData.setBand(tParamBandDrillings);
//        requestData.setDirectional(tParamDirectionalDrillings);
//        return requestData;
//    }

}

