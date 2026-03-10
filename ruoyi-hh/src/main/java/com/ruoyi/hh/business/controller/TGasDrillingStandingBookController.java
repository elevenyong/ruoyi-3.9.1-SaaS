package com.ruoyi.hh.business.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.business.domain.TGasDrillingStandingBook;
import com.ruoyi.hh.business.service.ITGasDrillingStandingBookService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * (TActualBorehole)表控制层
 *
 * @author Eleven
 * @since 2022-07-13 15:07:42
 */
@RestController
@RequestMapping("tGasDrillingStandingBook")
@Api(value = "瓦斯抽采钻孔施工台帐Controller", tags = {"瓦斯抽采钻孔施工台帐接口"})
public class TGasDrillingStandingBookController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private ITGasDrillingStandingBookService tGasDrillingStandingBookService;

    @PostMapping("getTGasDrillingStandingBookList")
    public TableDataInfo getTGasDrillingStandingBookList(@RequestParam(required = false,value="roadwayId") Integer roadwayId,
                                                         @RequestParam(required = false,value="groupNo") List<String> groupNo,
                                                         @RequestParam(required = false,value="purposeboreholeId") List<Integer> purposeboreholeId,
                                                         @RequestParam(required = false,value="startDate") Date startDate,
                                                         @RequestParam(required = false,value="endDate") Date endDate) throws ParseException {
        startPage();
        if(endDate==null&&startDate!=null){
            endDate=new Date();
        }
        List<TGasDrillingStandingBook> list = tGasDrillingStandingBookService.getTGasDrillingStandingBookList(roadwayId, groupNo, purposeboreholeId, startDate,
                endDate);
        return getDataTable(list);
    }


//    @PostMapping("ex")
//    public void ex(HttpServletResponse response) throws ParseException {
////        List<TGasDrillingStandingBook> list = new ArrayList<>();
//        List<Integer> g=new ArrayList<Integer>();
//        g.add(6);
//        List<Integer> p=new ArrayList<Integer>();
//        p.add(1);
//        ExcelUtil<TGasDrillingStandingBook> util = new ExcelUtil<TGasDrillingStandingBook>(TGasDrillingStandingBook.class);
//        util.exportExcel(response, getTGasDrillingStandingBookList(23,g,p,"2022-08-10 18:41:40","2022-08-21 18:41:40"), "瓦斯抽采钻孔施工台帐");
//    }

    @PostMapping("export")
    public void export(HttpServletResponse response,
                       @RequestParam(required = false,value="roadwayId") Integer roadwayId,
                       @RequestParam(required = false,value="groupNo") List<String> groupNo,
                       @RequestParam(required = false,value="purposeboreholeId") List<Integer> purposeboreholeId,
                       @RequestParam(required = false,value="startDate") Date startDate,
                       @RequestParam(required = false,value="endDate") Date endDate
    ){
        List<TGasDrillingStandingBook> list = tGasDrillingStandingBookService.getTGasDrillingStandingBookList(roadwayId, groupNo, purposeboreholeId, startDate,
                endDate);
        ExcelUtil<TGasDrillingStandingBook> util = new ExcelUtil<TGasDrillingStandingBook>(TGasDrillingStandingBook.class);
        util.exportExcel(response, list, "瓦斯抽采钻孔施工台帐");
    }

}
