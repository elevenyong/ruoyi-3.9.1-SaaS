package com.ruoyi.hh.demo.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.demo.domain.Drillproductionbill;
import com.ruoyi.hh.demo.mapper.DrillproductionbillDao;
import com.ruoyi.hh.demo.service.DrillproductionbillService;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Drillproductionbill)表控制层
 *
 * @author Eleven
 * @since 2022-06-08 17:49:25
 */
@RestController
@RequestMapping("drillproductionbill")
@Api(value="钻孔施工台账Controller",tags={"（工程集控）钻孔施工台账数据相关接口  2022-06-09"})
public class DrillproductionbillController{
    /**
     * 服务对象
     */
    @Resource
    private DrillproductionbillService drillproductionbillService;
    @Resource
    private DrillproductionbillDao drillproductionbillMapper;



//    public AjaxResult selectAll(Page<Drillproductionbill> page, Drillproductionbill drillproductionbill) {
//        return AjaxResult.success(this.drillproductionbillService.page(page, new QueryWrapper<>(drillproductionbill)));
//    }
    
    @PostMapping("getAll")
    @ApiOperation("获取钻孔台账列表，index为页面下标（从0开始）,size为每页的个数")
    public AjaxResult getAll(int index, int size){
    	Page<Drillproductionbill>  page=new Page<Drillproductionbill>(index,size);
    	return AjaxResult.success(this.drillproductionbillMapper.selectPage(page, new QueryWrapper<>()));
    }

//    /**
//     * 分页查询所有数据
//     *
//     * @param index 页码下标（从0开始）
//     * @param size 每页的数量
//     * @return 分页结果
//     */
//    @PostMapping("selectDrillproductionbill")
//    @ApiOperation("查询钻孔施工台账信息，index为页面下标（从0开始）,size为每页的个数,data为实体json字符串,格式可参考钻孔台账列表")
//    public AjaxResult selectDrillproductionbill(int index,int size,@RequestParam String data) {
//    	JSONObject jsonObj = JSONObject.fromObject(data);
//    	Drillproductionbill drillproductionbill = (Drillproductionbill) JSONObject.toBean(jsonObj,Drillproductionbill.class);
//    	Page<Drillproductionbill>  page=new Page<Drillproductionbill>(index,size);
//    	QueryWrapper<Drillproductionbill> qu=new QueryWrapper<>();
//    	if(drillproductionbill.getDrillsiteLocal()!=null) {
//    		qu.eq("drillsiteLocal", drillproductionbill.getDrillsiteLocal());
//    	}
//    	if(drillproductionbill.getDrillpurpose()!=null) {
//    		qu.eq("drillPurpose", drillproductionbill.getDrillpurpose());
//    	}
//    	if(drillproductionbill.getDrillsiteCode()!=null) {
//    		qu.eq("drillsiteCode", drillproductionbill.getDrillsiteCode());
//    	}
//    	if(drillproductionbill.getHoleno()!=null) {
//    		qu.eq("holeno", drillproductionbill.getHoleno());
//    	}
//        return AjaxResult.success(this.drillproductionbillMapper.selectPage(page, qu));
//    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ApiIgnore
    public AjaxResult selectOne(@PathVariable Serializable id) {
        return AjaxResult.success(this.drillproductionbillService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param drillproductionbill 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ApiIgnore
    public AjaxResult insert(@RequestBody Drillproductionbill drillproductionbill) {
        return AjaxResult.success(this.drillproductionbillService.save(drillproductionbill));
    }

    /**
     * 修改数据
     *
     * @param drillproductionbill 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ApiIgnore
    public AjaxResult update(@RequestBody Drillproductionbill drillproductionbill) {
        return AjaxResult.success(this.drillproductionbillService.updateById(drillproductionbill));
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
        return AjaxResult.success(this.drillproductionbillService.removeByIds(idList));
    }
    /**
     * 查询所有的钻场编号
     * @return
     */
    @GetMapping("getDrillsiteCode")
    @ApiOperation(value="查询所有的钻场编号")
    public AjaxResult getDrillsiteCode(){
    	return AjaxResult.success(this.drillproductionbillService.getDrillsiteCode());
    }
    /**
     * 查询所有钻孔地点
     * @return
     */
    @GetMapping("getDrillsiteLocal")
    @ApiOperation(value="查询所有钻孔地点")
    public AjaxResult getDrillsiteLocal(){
    	return AjaxResult.success(this.drillproductionbillService.getDrillsiteLocal());
    }
    /**
     * 查询所有钻孔用途
     * @return
     */
    @GetMapping("getDrillpurpose")
    @ApiOperation(value="查询所有钻孔用途")
    public AjaxResult getDrillpurpose(){
    	return AjaxResult.success(this.drillproductionbillService.getDrillpurpose());
    }
    /**
     * 查询所有钻孔编号
     * @return
     */
    @GetMapping("getHoleNO")
    @ApiOperation(value="查询所有钻孔编号")
    public AjaxResult getHoleNO(){
    	return AjaxResult.success(this.drillproductionbillService.getHoleNO());
    }
    
    
    
}

