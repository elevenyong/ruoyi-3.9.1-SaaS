package com.ruoyi.hh.simulate.controller;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.mapper.TWorkingFaceMapper;
import com.ruoyi.hh.simulate.domain.DrainageUnitTotal;
import com.ruoyi.hh.simulate.domain.Workfacerealtime;
import com.ruoyi.hh.simulate.service.IWorkfacerealtimeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (Workfacerealtime)表控制层
 *
 * @author Eleven
 * @since 2022-10-31 16:39:51
 */
@RestController
@RequestMapping("workfacerealtime")
public class WorkfacerealtimeController{
    /**
     * 服务对象
     */
    @Resource
    private IWorkfacerealtimeService workfacerealtimeService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private TWorkingFaceMapper tWorkingFaceMapper;
    @Resource
    private com.ruoyi.hh.util.data.simulationRealTimeData simulationRealTimeData;

	/**
     * 获取列表
     * @param workfacerealtime 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('workfacerealtime:list')")
    public AjaxResult selectAll(@RequestBody Workfacerealtime workfacerealtime) {
        return AjaxResult.success(this.workfacerealtimeService.selectWorkfacerealtimeList(workfacerealtime));
    }
	
	 /**
     * 插入单条数据
     * @param workfacerealtime 单条数据
     */
    @PostMapping("insert")
	//@PreAuthorize("@ss.hasPermi('workfacerealtime:insert')")
    public void insertWorkfacerealtime (@RequestBody Workfacerealtime workfacerealtime){
        this.workfacerealtimeService.insertWorkfacerealtime(workfacerealtime);
    }
	
	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
   // @PreAuthorize("@ss.hasPermi('workfacerealtime:insert')")
    public void insert(@RequestBody List<Workfacerealtime> list){
        if(list.size()>0){
            for(Workfacerealtime workfacerealtime:list){
                this.workfacerealtimeService.insertWorkfacerealtime(workfacerealtime);
            }
        }
        //this.workfacerealtimeService.insertWorkfacerealtimeList(list);
    }
	
	
	 /**
     * 修改
     * @param workfacerealtime 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateWorkfacerealtime")
	//@PreAuthorize("@ss.hasPermi('workfacerealtime:updateWorkfacerealtime')")
    public AjaxResult updateTRoadway(@RequestBody Workfacerealtime workfacerealtime){
        return AjaxResult.success(this.workfacerealtimeService.updateWorkfacerealtime(workfacerealtime));
    }
	
	 /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('workfacerealtime:id')")
    public AjaxResult selectOne(@RequestParam Integer id) {
        return AjaxResult.success(this.workfacerealtimeService.selectWorkfacerealtimeById(id));
    }
	
	/**
     * 根据id删除单挑数据
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteWorkfacerealtimeById")
	//@PreAuthorize("@ss.hasPermi('workfacerealtime:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id){
        int result=this.workfacerealtimeService.deleteWorkfacerealtimeById(id);
        return AjaxResult.success(result);
    }
	
	/**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteWorkfacerealtimeByIds")
    public AjaxResult delete (@RequestParam Integer[] ids){
        return AjaxResult.success(this.workfacerealtimeService.deleteWorkfacerealtimeByIds(ids));
    }
    

        /**
     * 导出excel
     * @param response res
     * @param list 列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<Workfacerealtime> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<Workfacerealtime> util = new ExcelUtil<Workfacerealtime>(Workfacerealtime.class);
        util.exportExcel(response, list, "模拟工作面抽采");
    }

    /**
     *
     * @param workfaceid 工作面ID
     * @return 列表
     */
    @PostMapping("getWorkFaceExtractionInfoById")
    public AjaxResult getWorkFaceExtractionInfoById(Integer workfaceid){
        List<Workfacerealtime> list=this.workfacerealtimeService.getWorkFaceExtractionInfoById(workfaceid);
        Workfacerealtime workfacerealtime=new Workfacerealtime();
        double totalmixtureflow=0.0;
        double totalpureflow=0.0;
        double totaltemperature=0.0;
        double totalch4=0.0;
        double totalco=0.0;
        double totalpressure=0.0;
        if(list.size()!=0){
            for(Workfacerealtime wf:list){
                totalmixtureflow+=wf.getMixTureFlow();
                totalpureflow+=wf.getPureFlow();
                totaltemperature+=wf.getTemperature();
                totalch4+=wf.getCh4Concentration();
                totalco+=wf.getCh4Concentration();
                totalpressure+=wf.getNegativePressure();
                if(workfacerealtime.getWorkfaceId()==null){
                    workfacerealtime.setWorkfaceId(wf.getWorkfaceId());
                    workfacerealtime.setBeginDrainageDate(wf.getBeginDrainageDate());
                    workfacerealtime.setWorkfaceName(wf.getWorkfaceName());
                }
            }
            workfacerealtime.setMixTureFlow(totalmixtureflow/list.size());
            workfacerealtime.setPureFlow(totalpureflow/list.size());
            workfacerealtime.setTemperature(totaltemperature/list.size());
            workfacerealtime.setCh4Concentration(totalch4/list.size());
            workfacerealtime.setCoConcentration(totalco/list.size());
            workfacerealtime.setNegativePressure(totalpressure/list.size());
            List<DrainageUnitTotal> duts=redisCache.getCacheList("sensordata");
            for(DrainageUnitTotal drainageUnitTotal:duts){
                if(drainageUnitTotal.getWorkfaceid().equals(workfaceid)){
                    workfacerealtime.setTotalMixTureFlow(drainageUnitTotal.getTotalmixtureflow());
                    workfacerealtime.setTotalPureFlow(drainageUnitTotal.getTotalpureflow());
                }
            }
//            redisCache.getCacheList();
        }
        return AjaxResult.success(workfacerealtime);
    }
    @GetMapping("getQueue")
    public AjaxResult getQueue(){
        return AjaxResult.success(this.simulationRealTimeData.getExceptionQueue());
    }

}

