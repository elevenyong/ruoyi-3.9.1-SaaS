package com.ruoyi.hh.physical.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TRoadtype;
import com.ruoyi.hh.physical.service.ITRoadtypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * (TRoadtype)表控制层
 *
 * @author Eleven
 * @since 2022-07-20 09:57:08
 */
@Api(value = "DZ巷道类型接口", tags = "DZ巷道类型接口")
@RestController
@RequestMapping("tRoadtype")
public class TRoadtypeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITRoadtypeService tRoadtypeService;

    /**
     * 获取列表
     *
     * @param tRoadtype 要查询的信息
     * @return 返回结果
     */
    @ApiOperation("获取巷道类型")
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tRoadtype:list')")
    public TableDataInfo selectAll(@RequestBody TRoadtype tRoadtype) {
        startPage();
        List<TRoadtype> list = this.tRoadtypeService.selectTRoadtypeList(tRoadtype);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tRoadtype 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tRoadtype:insert')")
    public void insertTRoadtype(@RequestBody TRoadtype tRoadtype) {
        this.tRoadtypeService.insertTRoadtype(tRoadtype);
    }


//    /**
//     * 插入单条数据
//     *
//     * @param tRoadtype 单挑数据
//     */
//    @PostMapping("insert")
//    //@PreAuthorize("@ss.hasPermi('tRoadtype:insert')")
//    public AjaxResult insertTRoadtype(@RequestBody TRoadtype tRoadtype) {
//        Result result=this.tRoadtypeService.insertTRoadtype(tRoadtype);
//        if(result.getStatus()==0){
//            return AjaxResult.success("添加成功");
//        }else if(result.getStatus()==1){
//            return AjaxResult.error("数据已存在");
//        }else{
//            return AjaxResult.error("添加失败");
//        }
//
//    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tRoadtype:insert')")
    public void insert(@RequestBody List<TRoadtype> list) {
        if (list.size() > 0) {
            for (TRoadtype tRoadtype : list) {
                this.tRoadtypeService.insertTRoadtype(tRoadtype);
            }
        }
    }


//    /**
//     * 批量插入数据
//     *
//     * @param list 数据列表
//     */
//    @PostMapping("insertList")
//    // @PreAuthorize("@ss.hasPermi('tRoadtype:insert')")
//    public AjaxResult insert(@RequestBody List<TRoadtype> list) {
//        int suc=0;
//        int err=0;
//        if (list.size() > 0) {
//            for (TRoadtype tRoadtype : list) {
//                Result result= this.tRoadtypeService.insertTRoadtype(tRoadtype);
//                if(result.getStatus()==0){
//                    suc++;
//                }else{
//                    err++;
//                }
//            }
//        }
//        if(suc==list.size()){
//            return AjaxResult.success("成功添加"+suc+"条");
//        }else{
//            return AjaxResult.error("成功添加"+suc+"条，失败"+err+"条");
//        }
//    }


    /**
     * 修改
     *
     * @param tRoadtype 要修改的实体信息
     * @return 结果
     */
    @ApiOperation("更新巷道类型名称")
    @PutMapping("updateTRoadtype")
    //@PreAuthorize("@ss.hasPermi('tRoadtype:updateTRoadtype')")
    public AjaxResult updateTRoadway(@RequestBody TRoadtype tRoadtype) {
        return AjaxResult.success(this.tRoadtypeService.updateTRoadtype(tRoadtype));
    }


//    /**
//     * 修改
//     *
//     * @param tRoadtype 要修改的实体信息
//     * @return 结果
//     */
//    @PutMapping("updateTRoadtype")
//    //@PreAuthorize("@ss.hasPermi('tRoadtype:updateTRoadtype')")
//    public AjaxResult updateTRoadway(@RequestBody TRoadtype tRoadtype) {
//        Result result=this.tRoadtypeService.updateTRoadtype(tRoadtype);
//        if(result.getStatus()==0){
//            return AjaxResult.success("修改成功");
//        }else if(result.getStatus()==1){
//            return AjaxResult.error("数据已存在");
//        }else{
//            return AjaxResult.error("修改失败");
//        }
//
//    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tRoadtype:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tRoadtypeService.selectTRoadtypeById(id));
    }

    /**
     * 根据id删除单条数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTRoadtypeById")
    //@PreAuthorize("@ss.hasPermi('tRoadtype:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tRoadtypeService.deleteTRoadtypeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTRoadtypeByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tRoadtypeService.deleteTRoadtypeByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TRoadtype> list = new ArrayList<>();
        list = this.tRoadtypeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TRoadtype tRoadtype) {
        List<TRoadtype> list = this.tRoadtypeService.selectTRoadtypeList(tRoadtype);
        ExcelUtil<TRoadtype> util = new ExcelUtil<TRoadtype>(TRoadtype.class);
        util.exportExcel(response, list, "巷道类型数据");
    }


}

