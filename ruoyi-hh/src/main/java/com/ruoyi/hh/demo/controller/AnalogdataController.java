package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.AnalogDataAvg;
import com.ruoyi.hh.demo.domain.Analogdata;
import com.ruoyi.hh.demo.service.AnalogdataService;
import com.ruoyi.hh.util.PageListUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (Analogdata)表控制层
 *
 * @author Eleven
 * @since 2022-05-17 14:58:27
 */
@RestController
@RequestMapping("analogdata")
@Api(value="传感器数据Controller",tags={"传感器相关接口"})
public class AnalogdataController {
    /**
     * 服务对象
     */
    @Resource
    private AnalogdataService analogdataService;


    /**
     * 分页查询所有数据
     *
     * @param page       分页对象
     * @param analogdata 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ApiIgnore
    public AjaxResult selectAll(Page<Analogdata> page, Analogdata analogdata) {
        return AjaxResult.success(this.analogdataService.page(page, new QueryWrapper<>(analogdata)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiIgnore
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.analogdataService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param analogdata 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiIgnore
    public AjaxResult insert(@RequestBody Analogdata analogdata) {
        return AjaxResult.success(this.analogdataService.save(analogdata));
    }

    /**
     * 修改数据
     *
     * @param analogdata 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiIgnore
    public AjaxResult update(@RequestBody Analogdata analogdata) {
        return AjaxResult.success(this.analogdataService.updateById(analogdata));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @ApiIgnore
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.analogdataService.removeByIds(idList));
    }

    /**
     * 获取所有传感器列表数据
     * @return
     * @throws ParseException 1
     */
    @GetMapping("getAnalogData")
    @ApiOperation("查询所有传感器信息，参数index为页码下标，size为单个页面有多少条数据")
//    public R getAnalogData(int index,int size){
//        Page<Analogdata> page=new Page<Analogdata>(index,size);
//        return AjaxResult.success(this.analogdataService.page(page).getRecords());
//    }
    public AjaxResult getAnalogData(int index,int size,String stratTime,String endTime,String pointCode) throws ParseException {
    	Date s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stratTime);
    	Date e=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
    	List<Analogdata> list=this.analogdataService.getDataInTime(s, e, pointCode);
    	Page page= PageListUtils.getPages(index, size, list);
    	return AjaxResult.success(page);
    }
    
    @GetMapping("getOne")
    public AjaxResult getOne(String stratTime,String PointCode) throws ParseException {
    	Date s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stratTime);
    	return AjaxResult.success(this.analogdataService.getOne(s, PointCode));
    }

    /**
     * 获取某一个传感器的实时数据（数据库中的最新数据）
     * @param PointCode PointCode
     * @return 获取某一个传感器的实时数据（数据库中的最新数据）
     */
    @PostMapping("getRealTimeDataByPointCode")
    @ApiOperation("根据点位编号PointCode查询传感器的实时（数据库中最新的）数据信息")
    public AjaxResult getRealTimeDataByPointCode(String PointCode){
        return AjaxResult.success(this.analogdataService.getRealTimeDataByPointCode(PointCode));
    }

    /**
     * 获取所有传感器最新时间的数据
     * @param index 第几页
     * @param size 每页多少条
     * @return 获取所有传感器最新时间的数据
     */
    @GetMapping("getRealTimeData")
    @ApiOperation("查询所有传感器的实时（数据库中最新的）数据信息，参数index为页码下标，size为单个页面有多少条数据")
    public AjaxResult getRealTimeData(int index,int size){
        List<Analogdata> list=this.analogdataService.getRealTimeData();
        Page<Analogdata> page = PageListUtils.getPages(index,size,list);
        return AjaxResult.success(page.getRecords());
    }
    
    @GetMapping("getAvgEveryHour")
    public AjaxResult getAvgEveryHour(String PointCode) {
    	List<AnalogDataAvg> analogDataAvgs=this.analogdataService.getAvgEveryHour(PointCode);
    	return AjaxResult.success(analogDataAvgs);
    }
    
//    @Autowired
//    private AnalogdataDao analogdataDao;

//    /**
//     * 定时生成标签历史分表任务(每天生成次日表)
//     */
//    @GetMapping("createSubTable")
//    public void createSubTable() {
//        Date today = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(today);
//        c.add(Calendar.DAY_OF_MONTH, 1);
//        Date pastDay = c.getTime();
//        String year = String.format("%tY", pastDay);
//        String mon = String.format("%tm", pastDay);
//        String day = String.format("%td", pastDay);
//        String tbName = new StringBuilder().append("Analogdata").append(year).append(mon).append(day).toString();
//        analogdataDao.createTodayTable(tbName);
//    }
//
//    /**
//     * 定时删除标签历史分表任务
//     */
//    @GetMapping("deleteSubTable")
//    public void deleteSubTable() {
//        String saveTime = "2";
//        Date today = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(today);
//        c.add(Calendar.DAY_OF_MONTH, -Integer.parseInt(saveTime)); //今天-保存天数
//        Date pastDay = c.getTime();
//        String year = String.format("%tY", pastDay);
//        String mon = String.format("%tm", pastDay);
//        String day = String.format("%td", pastDay);
//        String tbName = new StringBuilder().append("Analogdata").append(year).append(mon).append(day).toString();
//        analogdataDao.deleteSubTable(tbName);
//    }
//    
//    @PostMapping("insertAnalogDataList")
//    public void insertAnalogDataList(@RequestParam("list") String list) {
//    	List<Analogdata> analogdataList = JSONArray.parseArray(list,Analogdata.class);
//    	analogdataService.insertAnalogDataList(analogdataList);
//    }
    
    
    
    


}

