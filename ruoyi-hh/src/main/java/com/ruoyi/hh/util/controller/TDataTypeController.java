package com.ruoyi.hh.util.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.util.domain.TDataType;
import com.ruoyi.hh.util.service.ITDataTypeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * (TDataType)表控制层
 *
 * @author Eleven
 * @since 2022-07-14 21:03:42
 */
@RestController
@RequestMapping("tDataType")
public class TDataTypeController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITDataTypeService tDataTypeService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tDataType 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tDataType:list')")
    public TableDataInfo selectAll(@RequestBody TDataType tDataType) {
        startPage();
        List<TDataType> list =this.tDataTypeService.selectTDataTypeList(tDataType);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tDataType 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tDataType:insert')")
    public void insertTDataType(@RequestBody TDataType tDataType) {

        this.tDataTypeService.insertTDataType(tDataType);
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    //@PreAuthorize("@ss.hasPermi('tDataType:insert')")
    public void insert(@RequestBody List<TDataType> list) {
        this.tDataTypeService.insertTDataTypeList(list);
    }


    /**
     * 修改
     *
     * @param tDataType 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTDataType")
    //@PreAuthorize("@ss.hasPermi('tDataType:updateTDataType')")
    public AjaxResult updateTRoadway(@RequestBody TDataType tDataType) {
        return AjaxResult.success(this.tDataTypeService.updateTDataType(tDataType));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
//    //@PreAuthorize("@ss.hasPermi('tDataType:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tDataTypeService.selectTDataTypeById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTDataTypeById")
    //@PreAuthorize("@ss.hasPermi('tDataType:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tDataTypeService.deleteTDataTypeById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTDataTypeByIds")
    //@PreAuthorize("@ss.hasPermi('tDataType:ids')")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tDataTypeService.deleteTDataTypeByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    //@PreAuthorize("@ss.hasPermi('tDataType:id')")
    public TableDataInfo getPageList() {
        startPage();
        List<TDataType> list = new ArrayList<>();
        list=this.tDataTypeService.getPageList();
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     * @param list     列表
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody List<TDataType> list) {
//        List<TActualBorehole> list=this.tActualBoreholeService.getPageList();
        ExcelUtil<TDataType> util = new ExcelUtil<>(TDataType.class);
        util.exportExcel(response, list, "数据类型数据");
    }

    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        ExcelUtil<TDataType> util = new ExcelUtil<TDataType>(TDataType.class);
        List<TDataType> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (TDataType tDataType : list) {
            BeanValidators.validateWithException(validator, tDataType);
            int res = this.tDataTypeService.insertTDataType(tDataType);
            if (res > 0) {
                successNum++;
            }
//            if (StringUtils.isNull(tDataType))
//            {
//                BeanValidators.validateWithException(validator,tDataType);
//                this.tDataTypeService.insertTDataType(tDataType);
//                successNum++;
//                successMsg.append("<br/>" + successNum +  " 条导入成功");
//            }
//            else if(updateSupport){
//                BeanValidators.validateWithException(validator,tDataType);
//                this.tDataTypeService.updateTDataType(tDataType);
//                successNum++;
//                successMsg.append("<br/>" + successNum +  " 条导入成功");
//            }
//            else{
//                failureNum++;
//                failureMsg.append("<br/>"+ tDataType.getDatatype()+" 已存在");
//            }

        }
        return AjaxResult.success("添加" + successNum + "条");
    }

}

