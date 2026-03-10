package com.ruoyi.hh.simulate.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.simulate.domain.BoreholeConstructionBook;
import com.ruoyi.hh.simulate.service.BoreholeConstructionBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("BoreholeConstructionBook")
public class BoreholeConstructionBookController extends BaseController {

    @Resource
    private BoreholeConstructionBookService boreholeConstructionBookService;

    /**
     * 根据巷道ID，钻孔用途，组号，孔号查询钻孔施工台账
     * @param roadwayId 巷道ID
     * @param purposeboreholeid 钻孔用途ID
     * @param groupNo 组号
     * @param boreholeNo 孔号
     * @param startdate 开始时间
     * @param enddate 结束时间
     * @return 列表
     */
    @PostMapping("selectBoreholeConstructionBook")
    public TableDataInfo selectBoreholeConstructionBook(@RequestParam (value="roadwayId",required = false) Integer roadwayId,
                                                        @RequestParam(value="purposeboreholeid",required = false) List<Integer> purposeboreholeid,
                                                        @RequestParam(value="groupNo",required = false) List<String> groupNo,
                                                        @RequestParam(value="boreholeNo",required = false) List<Integer> boreholeNo,
                                                        @RequestParam(value="startdate",required = false) Date startdate,
                                                        @RequestParam(value="enddate",required = false) Date enddate){
        startPage();
        if(startdate!=null&&enddate==null){
            enddate=new Date();
        }
        List<BoreholeConstructionBook> list=this.boreholeConstructionBookService.selectBoreholeConstructionBook(roadwayId, purposeboreholeid, groupNo, boreholeNo, startdate, enddate);
        return getDataTable(list);
    }

    /**
     * 导出excel
     *
     * @param response res
     */
    @PostMapping("export")
    public void export(HttpServletResponse response, @RequestParam (value="roadwayId",required = false) Integer roadwayId,
                       @RequestParam(value="purposeboreholeid",required = false) List<Integer> purposeboreholeid,
                       @RequestParam(value="groupNo",required = false) List<String> groupNo,
                       @RequestParam(value="boreholeNo",required = false) List<Integer> boreholeNo,
                       @RequestParam(value="startdate",required = false) Date startdate,
                       @RequestParam(value="enddate",required = false) Date enddate) {
        List<BoreholeConstructionBook> list=this.boreholeConstructionBookService.selectBoreholeConstructionBook(roadwayId, purposeboreholeid, groupNo, boreholeNo, startdate, enddate);
        ExcelUtil<BoreholeConstructionBook> util = new ExcelUtil<BoreholeConstructionBook>(BoreholeConstructionBook.class);
        util.exportExcel(response, list, "钻孔施工台账数据");
    }

}
