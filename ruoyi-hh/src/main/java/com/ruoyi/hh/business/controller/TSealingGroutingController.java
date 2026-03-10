package com.ruoyi.hh.business.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TSealingGrouting;
import com.ruoyi.hh.business.service.ITSealingGroutingService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (TSealingGrouting)表控制层
 *
 * @author Eleven
 * @since 2022-07-21 17:04:13
 */
@RestController
@RequestMapping("tSealingGrouting")
public class TSealingGroutingController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITSealingGroutingService tSealingGroutingService;
    @Resource
    protected Validator validator;

    /**
     * 获取列表
     *
     * @param tSealingGrouting 要查询的信息
     * @return 返回结果
     */
    @PostMapping("list")
    //@PreAuthorize("@ss.hasPermi('tSealingGrouting:list')")
    public TableDataInfo selectAll(@RequestBody TSealingGrouting tSealingGrouting) {
        startPage();
        List<TSealingGrouting> list = this.tSealingGroutingService.selectTSealingGroutingList(tSealingGrouting);
        return getDataTable(list);
    }

    /**
     * 插入单条数据
     *
     * @param tSealingGrouting 单挑数据
     */
    @PostMapping("insert")
    //@PreAuthorize("@ss.hasPermi('tSealingGrouting:insert')")
    public AjaxResult insertTSealingGrouting(@RequestBody TSealingGrouting tSealingGrouting) {
        int re=this.tSealingGroutingService.insertTSealingGrouting(tSealingGrouting);
        if(re>0){
            return AjaxResult.success("添加成功");
        }else if(re==-2){
            return AjaxResult.error("孔数据已存在，添加失败");
        }else{
            return AjaxResult.error("添加失败");
        }
    }

    /**
     * 批量插入数据
     *
     * @param list 数据列表
     */
    @PostMapping("insertList")
    // @PreAuthorize("@ss.hasPermi('tSealingGrouting:insert')")
    public AjaxResult insert(@RequestBody List<TSealingGrouting> list) {
        int suc=0;
        int err=0;
        if (list.size() > 0) {
            for (TSealingGrouting tSealingGrouting : list) {
                int re= this.tSealingGroutingService.insertTSealingGrouting(tSealingGrouting);
                if(re>0){
                    suc++;
                }else{
                    err++;
                }
            }
        }
        return AjaxResult.success("成功添加"+suc+"条，失败"+err+"条");
        //this.tSealingGroutingService.insertTSealingGroutingList(list);
    }


    /**
     * 修改
     *
     * @param tSealingGrouting 要修改的实体信息
     * @return 结果
     */
    @PutMapping("updateTSealingGrouting")
    //@PreAuthorize("@ss.hasPermi('tSealingGrouting:updateTSealingGrouting')")
    public AjaxResult updateTRoadway(@RequestBody TSealingGrouting tSealingGrouting) {
        int res=this.tSealingGroutingService.updateTSealingGrouting(tSealingGrouting);
        if(res==2){
            return AjaxResult.error("已有数据格式不正确，更改的孔号重复");
        }else if(res==-2){
            return AjaxResult.error("孔号重复");
        }return AjaxResult.success("更新成功");
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @PostMapping("selectOneById")
    //@PreAuthorize("@ss.hasPermi('tSealingGrouting:id')")
    public AjaxResult selectOne(@RequestParam int id) {
        return AjaxResult.success(this.tSealingGroutingService.selectTSealingGroutingById(id));
    }

    /**
     * 根据id删除单挑数据
     *
     * @param id 需要删除的ID
     * @return 结果
     */
    @DeleteMapping("deleteTSealingGroutingById")
    //@PreAuthorize("@ss.hasPermi('tSealingGrouting:id')")
    public AjaxResult deleteTRoadwayById(@RequestParam int id) {
        int result = this.tSealingGroutingService.deleteTSealingGroutingById(id);
        return AjaxResult.success(result);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    @DeleteMapping("deleteTSealingGroutingByIds")
    public AjaxResult delete(@RequestParam List<Integer> ids) {
        return AjaxResult.success(this.tSealingGroutingService.deleteTSealingGroutingByIds(ids));
    }

    /**
     * 分页查询
     *
     * @return 列表
     */
    @PostMapping("getPageList")
    public TableDataInfo getPageList() {
        startPage();
        List<TSealingGrouting> list = new ArrayList<>();
                list=this.tSealingGroutingService.getPageList();
        return getDataTable(list);
    }

    /**
     * 根据注浆日期，注浆人，巷道ID，班次信息查询（封孔注浆记录）
     *
     * @param groutingDate   注浆日期
     * @param groutingPerson 注浆人
     * @param roadwayId      巷道ID
     * @param shiftId        班次
     * @return 列表
     */
    @PostMapping("getSealingGroutingRecord")
    public TableDataInfo getSealingGroutingRecord(@RequestParam(required = false,name="groutingDate") Date groutingDate,
                                                  @RequestParam(required = false,name="roadwayId") List<Integer> roadwayId,
                                                  @RequestParam(required = false,name="groupNo") List<String> groupNo,
                                                  @RequestParam(required = false,name="boreholeNo") List<Integer> boreholeNo,
                                                  @RequestParam(required = false,name="groutingPerson") List<String> groutingPerson,
                                                  @RequestParam(required = false,name="shiftId") Integer shiftId) throws ParseException {
        startPage();
        List<TSealingGrouting> list=this.tSealingGroutingService.getSealingGroutingRecord(groutingDate, roadwayId, groupNo, boreholeNo, groutingPerson, shiftId);
        return getDataTable(list);
    }


    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestBody TSealingGrouting tSealingGrouting) {
        List<TSealingGrouting> list=this.tSealingGroutingService.selectTSealingGroutingList(tSealingGrouting);
        ExcelUtil<TSealingGrouting> util = new ExcelUtil<TSealingGrouting>(TSealingGrouting.class);
        util.exportExcel(response, list, "冲孔注浆信息数据");
    }

    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        int successNum = 0;
        int failureNum = 0;
        int repetitionNum=0;
        ExcelUtil<TSealingGrouting> util = new ExcelUtil<TSealingGrouting>(TSealingGrouting.class);
        List<TSealingGrouting> list = util.importExcel(file.getInputStream());
        if (StringUtils.isNull(list) || list.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        for (TSealingGrouting tSealingGrouting : list) {
            BeanValidators.validateWithException(validator, tSealingGrouting);
            int res = this.tSealingGroutingService.insertTSealingGrouting(tSealingGrouting);
            if (res > 0) {
                successNum++;
            }else if(res==-2){
                repetitionNum++;
            }else{
                failureNum++;
            }
        }
        if(successNum>0){
            return AjaxResult.success("成功添加" + successNum + "条,重复"+repetitionNum+"条");
        }else{
            return AjaxResult.error(repetitionNum+"条重复，"+failureNum+"条失败");
        }
    }




}

