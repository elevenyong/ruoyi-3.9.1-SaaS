package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.service.ITSensorDataService;
import com.ruoyi.hh.physical.domain.TWorkingFace;
import com.ruoyi.hh.physical.service.ITDrainageUnitService;
import com.ruoyi.hh.physical.service.ITWorkingFaceService;
import com.ruoyi.hh.util.Result;
import com.ruoyi.hh.simulate.domain.DrainageUnitTotal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (TWorkingFace)表控制层
 *
 * @author Eleven
 * @since 2022-07-18 17:26:20
 */
@RestController
@RequestMapping("tWorkingFace")
public class TWorkingFaceController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITWorkingFaceService tWorkingFaceService;
    @Resource
    private ITSensorDataService tSensorDataService;
    @Resource
    private ITDrainageUnitService tDrainageUnitService;
    @Resource
    private RedisCache redisCache;

    /**
     * 获取列表
     *
     * @param tWorkingFace 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tWorkingFace:list')")
    public TableDataInfo selectAll(@RequestBody TWorkingFace tWorkingFace) {
        startPage();
        List<TWorkingFace> list = this.tWorkingFaceService.selectTWorkingFaceList(tWorkingFace);
        /*if(list.size()>0){
            for(TWorkingFace twf:list){
                if(twf.getWorkingfacename()!=null){
                    List<DrainageUnitTotal> duts=redisCache.getCacheList(twf.getWorkingfacename());
                    double mix=0.0;
                    double pure=0.0;
                    for(DrainageUnitTotal du:duts){
                        mix+=du.getTotalmixtureflow();
                        pure+=du.getTotalpureflow();
                    }
                    twf.setAccumulatedmix(mix);
                    twf.setAccumulatedpure(pure);
                    twf.setDrainageunittotallist(duts);
                    twf.setCountdrainageunit(duts.size());
                }
            }
        }*/
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tWorkingFace 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tWorkingFace:insert')")
    public AjaxResult insertTWorkingFace(@RequestBody TWorkingFace tWorkingFace) {
        Result result=this.tWorkingFaceService.insertTWorkingFace(tWorkingFace);
        if(result.getStatus()==0){
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
    //@PreAuthorize("@ss.hasPermi('tWorkingFace:insert')")
    public AjaxResult insert(@RequestBody List<TWorkingFace> list) {
        int suc=0;
        int err=0;
        if (list.size() > 0) {
            for (TWorkingFace tWorkingFace : list) {
                Result res= this.tWorkingFaceService.insertTWorkingFace(tWorkingFace);
                if(res.getStatus()==0){
                    suc++;
                }else{
                    err++;
                }
            }
            if(suc>0){
                return AjaxResult.success("添加成功"+suc+"条");
            }else{
                return AjaxResult.error("添加成功"+suc+"条，失败"+err+"条");
            }
        }
        return AjaxResult.error("数据为空");
//        this.tWorkingFaceService.insertTWorkingFaceList(list);
    }


    /**
     * 修改
     *
     * @param tWorkingFace 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTWorkingFace")
    //@PreAuthorize("@ss.hasPermi('tWorkingFace:updateTWorkingFace')")
    public AjaxResult updateTRoadway(@RequestBody TWorkingFace tWorkingFace) {
        return AjaxResult.success(this.tWorkingFaceService.updateTWorkingFace(tWorkingFace));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tWorkingFace:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tWorkingFaceService.selectTWorkingFaceById(id));
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTWorkingFaceByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tWorkingFaceService.deleteTWorkingFaceByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TWorkingFace> list = this.tWorkingFaceService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TWorkingFace tWorkingFace) {
        List<TWorkingFace> list=this.tWorkingFaceService.selectTWorkingFaceList(tWorkingFace);
        ExcelUtil<TWorkingFace> util = new ExcelUtil<TWorkingFace>(TWorkingFace.class);
        util.exportExcel(response, list, "工作面数据");
    }


//    /**
//     * 抽采动态
//     * @return 状态
//     */
//    @PostMapping("getWorkfaceDrainageOperation")
//    public AjaxResult getWorkfaceDrainageOperation(@RequestParam(value="startdate",required = false) Date startdate,
//                                                   @RequestParam(value="enddate",required = false) Date enddate){
//        List<TWorkingFace> list=this.tWorkingFaceService.getWorkfaceDrainageOperation();
//        if(list!=null){
//            for(TWorkingFace tWorkingFace:list){
//                tWorkingFace.setDrainageunittotallist(this.tSensorDataService.getDUFlowsInWorkFace(tWorkingFace.getId(),startdate,enddate));
//            }
//        }
//        return AjaxResult.success(list);
//    }

    @PostMapping("getDrainageUnitFlowByWorkFace")
    public AjaxResult getDrainageUnitFlowByWorkFace(Integer workfaceId
//            ,
//                                                    Date startdate,
//                                                    Date enddate
    ){
        TWorkingFace f=this.tWorkingFaceService.selectTWorkingFaceById(workfaceId);
        if(f!=null){
            String workfancename=this.tWorkingFaceService.selectTWorkingFaceById(workfaceId).getWorkfaceName();
            if(workfancename!=null){
                TWorkingFace workingFace=this.tWorkingFaceService.getInfoById(workfaceId);
                List<DrainageUnitTotal> list=redisCache.getCacheList(workfancename);
                double mix=0.0;
                double pure=0.0;
                for(DrainageUnitTotal du:list){
                    mix+=du.getTotalmixtureflow();
                    pure+=du.getTotalpureflow();
                }
                workingFace.setTotalDrainageQuantity(mix);
                workingFace.setAccumulatedMix(mix);
                workingFace.setAccumulatedPure(pure);
                workingFace.setDrainageUnitTotalList(list);
                workingFace.setCountDrainageUnit(list.size());
                return AjaxResult.success(workingFace);
            }
        }
        return AjaxResult.success("数据为空");
//        return  AjaxResult.success(this.tSensorDataService.getDUFlowsInWorkFace(workfaceId, startdate, enddate));
    }


}

