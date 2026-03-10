package com.ruoyi.hh.hhhz.controller.drilling;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.hhhz.domain.param.DesignParameters;
import com.ruoyi.hh.hhhz.service.ITDesignParameterService;
import com.ruoyi.hh.physical.service.ITDesignedBoreholeService;
import com.ruoyi.hh.physical.service.ITDesignedDirectionalBoreholeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设计参数公共类(TDesignParameters)表控制层
 *
 * @author eleven
 * @since 2023-07-19 13:59:56
 */
@RestController
@RequestMapping("tDesignParameters")
public class TDesignParametersController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDesignParameterService itDesignParametersService;
    @Resource
    private ITDesignedBoreholeService itDesignedBoreholeService;
    @Resource
    private ITDesignedDirectionalBoreholeService iTDesignedDirectionalBoreholeService;
    /**
     * 获取列表
     *
     * @param tDesignParameters  要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    // @PreAuthorize("@ss.hasPermi('tDesignParameters:list')")
    public TableDataInfo selectAll(@RequestBody DesignParameters tDesignParameters) {
        startPage();
        List<DesignParameters> list =this.itDesignParametersService.selectTDesignParametersList(tDesignParameters);
        return getDataTable(list);
    }
    
    /**
     * 插入单条数据
     *
     * @param designParameters 单条数据
     */
//    @PostMapping("insert")
//    // @PreAuthorize("@ss.hasPermi('tDesignParameters:insert')")
//    public AjaxResult insertTDesignParameters (@RequestBody DesignParameters tDesignParameters) {
//        return AjaxResult.success(this.itDesignParametersService.insertDesignParameters(tDesignParameters));
//    }

    @PostMapping("insert")
    @ApiOperation("插入钻孔设计数据带有定向钻孔设计参数")
    @Log(title = "钻孔设计带有定向钻孔设计参数", businessType = BusinessType.INSERT)
    public AjaxResult insertDesignParameter(@RequestBody DesignParameters designParameters){
        DesignParameters result=itDesignParametersService.insertDesignParameters(designParameters);
        return AjaxResult.success(result);
    }
    
     /**
     * 批量插入数据
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tDesignParameters:insert')")
    public void insert(@RequestBody List<DesignParameters> list){
        if(list.size()>0){
            for(DesignParameters td:list){
                this.itDesignParametersService.insertDesignParameters(td);
            }
        }
    }
    

     /**
     * 修改
     *
     * @param tDesignParameters 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDesignParameters")
    // @PreAuthorize("@ss.hasPermi('tDesignParameters:updateTDesignParameters')")
    public AjaxResult updateTDesignParameters(@RequestBody DesignParameters tDesignParameters) {
        return AjaxResult.success(this.itDesignParametersService.updateDesignParameters(tDesignParameters));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    // @PreAuthorize("@ss.hasPermi('tDesignParameters:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.itDesignParametersService.selectDesignParametersById(id));
    }
    
    
    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDesignParametersById")
    // @PreAuthorize("@ss.hasPermi('tDesignParameters:id')")
    public AjaxResult deleteTDesignParametersById(@RequestParam int id) {
//        this.iTDesignedDirectionalBoreholeService.delByDesignParameterId(id);
//        this.itDesignedBoreholeService.delByDesignParameterId(id);
//        int result = this.itDesignParametersService.deleteDesignParametersById(id);
        return AjaxResult.success(this.itDesignParametersService.deleteDesignParametersById(id));
    }
    
    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDesignParametersByIds")
    public AjaxResult delete(@RequestParam Integer[] ids) {
        return AjaxResult.success(this.itDesignParametersService.deleteDesignParametersByIds(ids));
    }
    
    

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody DesignParameters tDesignParameters) {
        List<DesignParameters> list=this.itDesignParametersService.selectTDesignParametersList(tDesignParameters);
        ExcelUtil<DesignParameters> util = new ExcelUtil<DesignParameters>(DesignParameters.class);
        util.exportExcel(response, list, "抽采单元数据");
    }

}
