package com.ruoyi.hh.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Sensordata;
import com.ruoyi.hh.demo.service.SensordataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (Sensordata)表控制层
 *
 * @author Eleven
 * @since 2022-06-09 10:19:56
 */
@RestController
@RequestMapping("sensordata")
@Api(value="防突传感器 Controller",tags={"（防突信息）传感器数据相关接口  2022-06-09"})
public class SensordataController  {
    /**
     * 服务对象
     */
    @Resource
    private SensordataService sensordataService;

    /**
     * 分页查询所有数据
     *
     * @param index 页码下标（从0开始）
     * @param size  每页的数量
     * @return 分页结果
     */
    @PostMapping("selectAll")
    @ApiIgnore
    public AjaxResult selectAll(int index, int size) {
        Page<Sensordata> page = new Page<Sensordata>(index, size);
        return AjaxResult.success(this.sensordataService.page(page, new QueryWrapper<>()));
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
        return AjaxResult.success(this.sensordataService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param sensordata 实体对象
     * @return 新增结果
     */
    @PostMapping("insert")
    @ApiIgnore
    public AjaxResult insert(@RequestBody Sensordata sensordata) {
        return AjaxResult.success(this.sensordataService.save(sensordata));
    }
    
    
    @GetMapping("creatData")
    @ApiIgnore
    public AjaxResult creatData() {
    	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
    	for(int i=0;i<135;i++) {
    		Sensordata sensordata=new Sensordata();
    		sensordata.setPointname("12130机抽巷2#瓦斯抽采管道传感器");
    		sensordata.setSensorid(1);
    		Calendar calendar=Calendar.getInstance();
    		calendar.setTime(new Date(122,00,15));
    		calendar.add(Calendar.DATE, i);
    		double d1=0.0;
    		
////    		if(i<109) {
////    			d=Math.random()/10+(100+i-1)/10;
////    		}else if(i>=109&&i<180) {
////    			d=Math.random()/10+(190-i*1.5-1)/10;
////    		}
//    		d=Math.random()*10+70+(150-i);	
////    		double d1=Double.parseDouble(String.format("%.2f", d));
////    		sensordata.setData(Double.parseDouble(String.format("%.3f", d1)));
    	
    		double d=Math.random();
    		d1=(150-i+d)*2+40;
//    		d1=15-i+d;
//    		if(i<85) {
//    			d1=85-i/3+9+d*2;
//    		}else {
//    			d1=62+i/15+d*2;
//    		}
    		
    		sensordata.setData(0.0);
    		String  dString = sim.format(calendar.getTime());
    		//四、将String转化为date，须要注意java.sql.Date.valueOf()函数只能接受参数类型为yyyy-MM-dd类型的
    		Date data = java.sql.Date.valueOf(dString);
    		sensordata.setDate(data);
    		this.sensordataService.save(sensordata);
    		
    	}
//    	return AjaxResult.success(this.sensordataService.save());
    	return null;
    }

    /**
     * 修改数据
     *
     * @param sensordata 实体对象
     * @return 修改结果
     */
    @PutMapping("update")
    @ApiIgnore
    public AjaxResult update(@RequestBody Sensordata sensordata) {
        return AjaxResult.success(this.sensordataService.updateById(sensordata));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping("delete")
    @ApiIgnore
    public AjaxResult delete(@RequestParam("idList") List<Long> idList) {
        return AjaxResult.success(this.sensordataService.removeByIds(idList));
    }
    
    @GetMapping("getSensorData")
    @ApiOperation("查询所有传感器数据的列表")
    public AjaxResult getSensorData(int index, int size) {
    	return AjaxResult.success(this.sensordataService.getSensorData());
    }
    
    @PostMapping("getSensorDataByPointName")
    @ApiOperation("根据点位查看传感器数据(含有‘#’需要转义成'%23')")
    public AjaxResult getSensorDataByPointName(String PointName) {
    	return AjaxResult.success(this.sensordataService.getSensorDataByPointName(PointName));
    }
     
    @PostMapping("getSensorDataBySensorType")
    @ApiOperation("根据传感器类型查看传感器数据")
    public AjaxResult getSensorDataBySensorType(String Kind) {
    	return AjaxResult.success(this.sensordataService.getSensorDataBySensorType(Kind));
    }
    
    
    
    
}

