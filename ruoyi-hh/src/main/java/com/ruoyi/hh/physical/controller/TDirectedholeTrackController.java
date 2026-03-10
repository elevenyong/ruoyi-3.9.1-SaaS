package com.ruoyi.hh.physical.controller;



import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TDirectedholeTrack;
import com.ruoyi.hh.physical.service.ITDirectedholeTrackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计定向钻轨迹(TDirectedholeTrack)表控制层
 *
 * @author makejava
 * @since 2023-03-06 17:53:08
 */
@RestController
@RequestMapping("tDirectedholeTrack")
public class TDirectedholeTrackController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDirectedholeTrackService tDirectedholeTrackService;

    /**
     * 获取列表
     *
     * @param tDirectedholeTrack  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeTrack:list')")
    public TableDataInfo selectAll(@RequestBody TDirectedholeTrack tDirectedholeTrack) {
        startPage();
        List<TDirectedholeTrack> list =this.tDirectedholeTrackService.selectTDirectedholeTrackList(tDirectedholeTrack);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param tDirectedholeTrack 单条数据
     */
    @PostMapping("insert")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeTrack:insert')")
    public void insertTDirectedholeTrack (@RequestBody TDirectedholeTrack tDirectedholeTrack) {
        this.tDirectedholeTrackService.insertTDirectedholeTrack(tDirectedholeTrack);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeTrack:insert')")
    public void insert(@RequestBody List<TDirectedholeTrack> list){
        if(list.size()>0){
            for(TDirectedholeTrack td:list){
                this.tDirectedholeTrackService.insertTDirectedholeTrack(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDirectedholeTrack 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDirectedholeTrack")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeTrack:updateTDirectedholeTrack')")
    public AjaxResult updateTDirectedholeTrack(@RequestBody TDirectedholeTrack tDirectedholeTrack) {
        return AjaxResult.success(this.tDirectedholeTrackService.updateTDirectedholeTrack(tDirectedholeTrack));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeTrack:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDirectedholeTrackService.selectTDirectedholeTrackById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDirectedholeTrackById")
    // @PreAuthorize("@ss.hasPermi('tDirectedholeTrack:id')")
    public AjaxResult deleteTDirectedholeTrackById(@RequestParam int id) {
        int result = this.tDirectedholeTrackService.deleteTDirectedholeTrackById(id);
        return AjaxResult.success(result);
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDirectedholeTrackByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.tDirectedholeTrackService.deleteTDirectedholeTrackByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TDirectedholeTrack tDirectedholeTrack) {
        List<TDirectedholeTrack> list=this.tDirectedholeTrackService.selectTDirectedholeTrackList(tDirectedholeTrack);
        ExcelUtil<TDirectedholeTrack> util = new ExcelUtil<TDirectedholeTrack>(TDirectedholeTrack.class);
        util.exportExcel(response, list, "定向钻孔轨迹数据");
    }
    
    

    
}

