package com.ruoyi.hh.business.controller;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.hh.business.domain.TGasremnantStatistics;
import com.ruoyi.hh.business.domain.vo.*;
import com.ruoyi.hh.business.service.ITGasremnantStatisticsService;
import com.ruoyi.hh.physical.domain.*;
import com.ruoyi.hh.physical.service.*;
import com.ruoyi.hh.util.WordUtil;
import com.ruoyi.hh.simulate.domain.ReportParameter;
import com.ruoyi.hh.simulate.domain.StFaultinfo;
import com.ruoyi.hh.simulate.service.IStFaultinfoService;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("judgeReportUnit")
public class JudgeReportUnitController {

    /**
     * 服务对象
     */
    @Resource
    private ITEvaluateUnitService tEvaluateUnitService;
    @Resource
    private ITDrainageUnitService tDrainageUnitService;
    @Resource
    private ITActualBoreholeService tActualBoreholeService;
    @Resource
    private ISysDictDataService iSysDictDataService;
    @Resource
    private IStFaultinfoService iStFaultinfoService;
    @Resource
    private ITCoalinfoService itCoalinfoService;
    @Resource
    private ITGasremnantStatisticsService itGasremnantStatisticsService;
    @Resource
    private ITWorkingFaceService itWorkingFaceService;

    @GetMapping("getJudgeReportUnit")
    public TEvaluateUnit JudgeReportUnit(Integer envaluateUnitId){
        TEvaluateUnit tEvaluationUnit= this.tEvaluateUnitService.selectTEvaluateUnitById(envaluateUnitId);
        long coalreserves=0; //评价单元储煤量
        long gascontentoriginal=0; //评价单元原始瓦斯含量
        long gastotalreserves=0; //评价单元瓦斯储量
        long totaldrainagequantity=0; //评价单元累计抽采量
        long gasamountreachstandard=0; //预抽瓦斯达标量
        long reachstandardgasremaining=0; //达标残存瓦斯含量（m³/t）
        long gasremaining=0; //残存瓦斯含量 （m³/t）
        long accumulatedmix=0; //累计抽采量
        long accumulatedpure=0; //累计抽采纯量
        List<TDrainageUnit> tDrainageUnitList=tDrainageUnitService.getByEvaluateUnitId(envaluateUnitId);
        for(TDrainageUnit tDrainageUnit:tDrainageUnitList){
            coalreserves+=tDrainageUnit.getCoalReserves();
            gasamountreachstandard+=tDrainageUnit.getGasContentOriginal();
            gastotalreserves+=tDrainageUnit.getGasTotalReserves();


        }

        return tEvaluationUnit;
    }


    @PostMapping("exportWord")
    public void exportword(HttpServletResponse resp, HttpServletRequest req, Integer evaluateunitId){
        //根据评价单元ID获取评价单元信息
        TEvaluateUnit tEvaluateUnit=this.tEvaluateUnitService.selectTEvaluateUnitById(evaluateunitId);
        //根据评价单元获取所属的工作面的信息
        TWorkingFace workingFace=this.tEvaluateUnitService.getWorkfaceByEvaluateUnitId(evaluateunitId);
        //根据工作面ID查询断层信息
        List<StFaultinfo> stFaultinfos=this.iStFaultinfoService.getByworkfaceid(workingFace.getId());
        //获取评价单元
        List<TEvaluateUnit> tEvaluateUnits=this.tEvaluateUnitService.getByWorkfaceId(workingFace.getId());
        List<TActualBorehole> tActualBoreholes=this.tActualBoreholeService.getByWorkfaceId(workingFace.getId());
        //获取字典
        HashMap<String, ArrayList<SysDictData>> dictData=iSysDictDataService.getAllDictDataMap();
        //生成结果集
        Map<String, Object> dataMap = new HashMap<String,Object>();
        List<TEvaluateUnitVo> evaluateUnitvoList=new ArrayList<>();
        List<StFaultinfoVo>  stFaultinfoVoList=new ArrayList<>();
        List<TActualBoreholeVo> tActualBoreholeVoList=new ArrayList<>();
        for(TEvaluateUnit eu:tEvaluateUnits){
            evaluateUnitvoList.add(new TEvaluateUnitVo(eu,dictData));
        }
        for(TActualBorehole t:tActualBoreholes){
            tActualBoreholeVoList.add(new TActualBoreholeVo(t,dictData));
        }
        TWorkingFaceVo workingFaceVo=new TWorkingFaceVo(workingFace,dictData);
        TEvaluateUnitVo evaluateUnitVo=new TEvaluateUnitVo(tEvaluateUnit,dictData);
        for(StFaultinfo sf:stFaultinfos){
            stFaultinfoVoList.add(new StFaultinfoVo(sf,dictData));
        }
        dataMap.put("stFaultinfos",stFaultinfoVoList);
        dataMap.put("evaluateUnit",evaluateUnitVo);
        dataMap.put("workFace",workingFaceVo);
        dataMap.put("evaluateUnits",evaluateUnitvoList);
        dataMap.put("actualboreholes",tActualBoreholeVoList);
        //设置生成文件存储路径
        String directory="/schemeList";
        WordUtil.CheckDownloadPath(directory);
        //生成文件
//        WordUtil.CreateWord(resp,req,dataMap,"ruoyi-quartz\\src\\main\\resources\\template\\dataApparatusCheckScheme","JudgeReport.ftl"
//                , RuoYiConfig.getDownloadPath()+directory+"/JudgeReport.doc");
        WordUtil.CreateWordWithPath(resp,req,dataMap,"JudgeReport.ftl"
                , RuoYiConfig.getDownloadPath()+directory+"/JudgeReport.doc");
        System.err.println(RuoYiConfig.getDownloadPath());
        //下载后删除生成文件
//        FileUtils.deleteFile(RuoYiConfig.getDownloadPath()+directory+"/BKJC-CX-007-2.doc");
    }

//    @PostMapping("exportWordModule")
//    public void exportWordModule(HttpServletResponse resp, HttpServletRequest req,
//            @RequestParam(value="workfaceid",required =false)Integer workfaceid,
//                                 @RequestParam(value="evaluateunitId",required =false)Integer evaluateunitId){

    @PostMapping("exportWordModule")
    public void exportWordModule(HttpServletResponse resp, HttpServletRequest req,
                                 @RequestBody ReportParameter reportParameter){
        //获取字典
        HashMap<String, ArrayList<SysDictData>> dictData=iSysDictDataService.getAllDictDataMap();
        Integer workfaceid=reportParameter.getWorkfaceid();
        Integer evaluateUnitid=reportParameter.getEvaluateunitid();
        TEvaluateUnit tEvaluateUnit=new TEvaluateUnit();
        if(evaluateUnitid!=null){
            tEvaluateUnit=this.tEvaluateUnitService.selectTEvaluateUnitById(evaluateUnitid);
        }
        //生成结果集
        Map<String, Object> dataMap = new HashMap<String,Object>();
        if(workfaceid==null){
            workfaceid=this.tEvaluateUnitService.getWorkfaceByEvaluateUnitId(reportParameter.getEvaluateunitid()).getId();
        }
        TWorkingFace workingFace=this.itWorkingFaceService.selectTWorkingFaceById(workfaceid);
        //根据工作面ID查询残存瓦斯含量信息
        List<TGasremnantStatistics> tGasremnantStatistics=this.itGasremnantStatisticsService.getByworkfaceid(workfaceid);
        //根据工作面ID查询断层信息
        List<StFaultinfo> stFaultinfos=this.iStFaultinfoService.getByworkfaceid(workfaceid);
        //评价单元相关信息
        List<TEvaluateUnit> tEvaluateUnits=this.tEvaluateUnitService.getJudgeReportInfo(workfaceid, reportParameter.getEvaluateunitid());
        List<TActualBorehole> tActualBoreholes=this.tActualBoreholeService.getByWorkfaceId(workfaceid);
        List<TActualBorehole> tgasContent=this.tActualBoreholeService.getGasContentPressureByWorkfaceid(workfaceid);
        TCoalinfo tCoalinfo=this.itCoalinfoService.getCoalInfo(workfaceid);
        List<TEvaluateUnitVo> tEvaluateUnitVos=new ArrayList<>();
        List<TActualBoreholeVo> tActualBoreholeVos=new ArrayList<>();
        List<TActualBoreholeVo> tgasContentVos=new ArrayList<>();
        List<StFaultinfoVo> stFaultinfoVos=new ArrayList<>();
        List<TGasremnantStatisticsVo> tGasremnantStatisticsVos=new ArrayList<>();
        for(TActualBorehole tgcv:tgasContent){
            tgasContentVos.add(new TActualBoreholeVo(tgcv,dictData));
        }
        for(TGasremnantStatistics ts:tGasremnantStatistics){
            tGasremnantStatisticsVos.add(new TGasremnantStatisticsVo(ts,dictData));
        }
        for(StFaultinfo si:stFaultinfos){
            stFaultinfoVos.add(new StFaultinfoVo(si,dictData));
        }
        for(TEvaluateUnit tu:tEvaluateUnits){
            tEvaluateUnitVos.add(new TEvaluateUnitVo(tu,dictData));
        }
        TCoalinfoVo tCoalinfoVo= new TCoalinfoVo(tCoalinfo,dictData);
        TWorkingFaceVo workingFaceVo=new TWorkingFaceVo(workingFace,dictData);
        TEvaluateUnitVo evaluateUnitVo=new TEvaluateUnitVo(tEvaluateUnit,dictData);
        ReportParameterVo reportParameterVo=new ReportParameterVo(reportParameter,dictData);
        for(TActualBorehole t:tActualBoreholes){
            tActualBoreholeVos.add(new TActualBoreholeVo(t,dictData));
        }
        dataMap.put("evaluateUnit",evaluateUnitVo);
        dataMap.put("tGasremnantStatisticsVos",tGasremnantStatisticsVos);
        dataMap.put("tEvaluateUnitVos",tEvaluateUnitVos);
        dataMap.put("tCoalinfoVo",tCoalinfoVo);
        dataMap.put("stFaultinfoVos",stFaultinfoVos);
        dataMap.put("tActualBoreholeVos",tActualBoreholeVos);
        dataMap.put("tgasContentVos",tgasContentVos);
        dataMap.put("reportParameterVo",reportParameterVo);
        dataMap.put("workFace",workingFaceVo);
        //设置生成文件存储路径
        String directory="/schemeList";
        WordUtil.CheckDownloadPath(directory);
        //生成文件
//        WordUtil.CreateWord(resp,req,dataMap,"ruoyi-quartz\\src\\main\\resources\\template\\dataApparatusCheckScheme","JudgeReport.ftl"
//                , RuoYiConfig.getDownloadPath()+directory+"/JudgeReport.doc");
        WordUtil.CreateWordWithPath(resp,req,dataMap,"exportWordModule.ftl"
                , RuoYiConfig.getDownloadPath()+directory+"/exportWordModule.doc");
        System.err.println(RuoYiConfig.getDownloadPath());
    }


}
