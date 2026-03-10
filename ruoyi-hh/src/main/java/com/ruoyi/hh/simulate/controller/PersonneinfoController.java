package com.ruoyi.hh.simulate.controller;
 
 
 
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.demo.domain.Personneinfo;
import com.ruoyi.hh.simulate.service.IPersonneinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * (Personneinfo)表控制层
 *
 * @author Eleven
 * @since 2023-02-11 11:08:18
 */
@RestController
@RequestMapping("personneinfo")
public class PersonneinfoController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private IPersonneinfoService personneinfoService;
	
	/**
     * 获取列表
     * @param personneinfo 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('personneinfo:list')")
    public TableDataInfo selectAll(@RequestBody Personneinfo personneinfo) {
        List<Personneinfo> list=this.personneinfoService.selectPersonneinfoList(personneinfo);
        return getDataTable(list);
    }
	
	 /**
     * 插入单条数据
     * @param personneinfo 单条数据
     */
    @PostMapping("insert")
	//@PreAuthorize("@ss.hasPermi('personneinfo:insert')")
    public void insertPersonneinfo (@RequestBody Personneinfo personneinfo){
        this.personneinfoService.insertPersonneinfo(personneinfo);
    }
	
	  /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
   // @PreAuthorize("@ss.hasPermi('personneinfo:insert')")
    public void insert(@RequestBody List<Personneinfo> list){
        if(list.size()>0){
            for(Personneinfo personneinfo:list){
                this.personneinfoService.insertPersonneinfo(personneinfo);
            }
        }
        //this.personneinfoService.insertPersonneinfoList(list);
    }
	
	
	 /**
     * 修改
     * @param personneinfo 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updatePersonneinfo")
	//@PreAuthorize("@ss.hasPermi('personneinfo:updatePersonneinfo')")
    public AjaxResult updateTRoadway(@RequestBody Personneinfo personneinfo){
        return AjaxResult.success(this.personneinfoService.updatePersonneinfo(personneinfo));
    }
	
	 /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('personneinfo:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.personneinfoService.selectPersonneinfoById(id));
    }
	
	/**
     * 根据id删除单挑数据
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deletePersonneinfoById")
	//@PreAuthorize("@ss.hasPermi('personneinfo:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id){
        int result=this.personneinfoService.deletePersonneinfoById(id);
        return AjaxResult.success(result);
    }
	
	/**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deletePersonneinfoByIds")
    public AjaxResult delete (@RequestParam Integer[] ids){
        return AjaxResult.success(this.personneinfoService.deletePersonneinfoByIds(ids));
    }
    

        /**
     * 导出excel
     * @param response res
     * @param list 列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<Personneinfo> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<Personneinfo> util = new ExcelUtil<Personneinfo>(Personneinfo.class);
        util.exportExcel(response, list, "工作面数据");
    }
	
    
}

