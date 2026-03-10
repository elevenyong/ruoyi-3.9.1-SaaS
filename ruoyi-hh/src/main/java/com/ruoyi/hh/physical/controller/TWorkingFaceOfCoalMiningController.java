package com.ruoyi.hh.physical.controller;

import cn.hutool.Hutool;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hh.physical.domain.TActDirectionalBorehole;
import com.ruoyi.hh.physical.domain.TBkActBoreholeInfo;
import com.ruoyi.hh.physical.domain.TRockinfo;
import com.ruoyi.hh.physical.domain.TWorkingFaceOfCoalMining;
import com.ruoyi.hh.physical.service.ITRockinfoService;
import com.ruoyi.hh.physical.service.ITWorkingFaceOfCoalMiningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.arrow.flatbuf.Int;
import org.apache.pdfbox.tools.Decrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 采煤工作面Controller
 *
 * @author hhhz
 * @date 2023-04-28
 */
@RestController
@RequestMapping("/coalMiningWorkingFace")
@Api(value = "采煤工作面接口", tags = "DZWF采煤工作面接口")
public class TWorkingFaceOfCoalMiningController extends BaseController {
    @Resource
    private ITWorkingFaceOfCoalMiningService tWorkingFaceOfCoalMiningService;
    @Resource
    private ITRockinfoService rockinfoService;

    @GetMapping("/mixBorehole")
    @ApiOperation("综合查询工作面钻孔数据")
    public AjaxResult getBoreholeList(@RequestParam String roadwayIds, @RequestParam String boreholeTypes) {
        String[] rds = roadwayIds.split(",");
        String[] tps = boreholeTypes.split(",");
        ArrayList<Integer> roadways = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<>();
        try {
            for (String rd : rds) {
                // 去重
                if (!roadways.contains(Integer.valueOf(rd))) {
                    roadways.add(Integer.parseInt(rd));
                }
            }
            for (String tp : tps) {
                // 去重
                if (!types.contains(Integer.valueOf(tp))) {
                    types.add(Integer.parseInt(tp));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("传入参数有误，请检查后重试！");
        }
        HashMap<String, List<?>> boreholes = tWorkingFaceOfCoalMiningService.getBoreholes(roadways, types);

        return AjaxResult.success(boreholes);
    }

    @GetMapping("/allBoreholeCount")
    @ApiOperation(value = "统计工作面的钻孔数量信息")
    public AjaxResult queryBoreholeCountList(@ApiParam(name = "workfaceId", value = "工作面ID")
                                             @RequestParam Integer workfaceId) {

        HashMap<String, HashMap<Integer, ?>> retMaps = tWorkingFaceOfCoalMiningService.getBoreholesCountDetail(workfaceId);
        return AjaxResult.success(retMaps);
    }


    /**
     * 带条件查询采煤工作面列表
     */
    @GetMapping("/list")
    @ApiOperation("查询全部采煤工作面列表")
    public TableDataInfo list() {
        startPage();
        List<TWorkingFaceOfCoalMining> list = tWorkingFaceOfCoalMiningService.selectTWorkingFaceOfCoalMiningList(new TWorkingFaceOfCoalMining());
        return getDataTable(list);
    }

    /**
     * 导出采煤工作面列表
     */
    @Log(title = "采煤工作面", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ApiOperation("导出采煤工作面列表")
    public void export(HttpServletResponse response, TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        List<TWorkingFaceOfCoalMining> list = tWorkingFaceOfCoalMiningService.selectTWorkingFaceOfCoalMiningList(tWorkingFaceOfCoalMining);
        ExcelUtil<TWorkingFaceOfCoalMining> util = new ExcelUtil<TWorkingFaceOfCoalMining>(TWorkingFaceOfCoalMining.class);
        util.exportExcel(response, list, "采煤工作面数据");
    }

    /**
     * 获取采煤工作面详细信息
     */
    @GetMapping(value = "/{id}")
    @ApiOperation("根据ID获取采煤工作面详细信息")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(tWorkingFaceOfCoalMiningService.selectTWorkingFaceOfCoalMiningById(id));
    }

    /**
     * 新增采煤工作面
     */
    @Log(title = "采煤工作面", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation("新增采煤工作面")
    public AjaxResult add(@RequestBody TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        TWorkingFaceOfCoalMining face = tWorkingFaceOfCoalMiningService.insertTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
        if (face != null) {
            TRockinfo defRockinfo = new TRockinfo();
            defRockinfo.setSerialNumber(0);
            defRockinfo.setWorkfaceId(face.getId());
            defRockinfo.setRockType(1);
            defRockinfo.setThickness(face.getDefaultCoalThickness());
            rockinfoService.insertTRockinfo(defRockinfo);
            return AjaxResult.success(face);
        } else return AjaxResult.error("新增工作面失败");
    }

    /**
     * 修改采煤工作面
     */
    @Log(title = "采煤工作面", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation("修改采煤工作面")
    public AjaxResult edit(@RequestBody TWorkingFaceOfCoalMining tWorkingFaceOfCoalMining) {
        TWorkingFaceOfCoalMining face = tWorkingFaceOfCoalMiningService.updateTWorkingFaceOfCoalMining(tWorkingFaceOfCoalMining);
        if (face != null) {
            TRockinfo qRockinfo = new TRockinfo();
            qRockinfo.setWorkfaceId(face.getId());
            qRockinfo.setSerialNumber(0);
            qRockinfo.setRockType(1);
            List<TRockinfo> tRockinfos = rockinfoService.selectTRockinfoList(qRockinfo);
            if (!tRockinfos.isEmpty()) {
                TRockinfo rockinfo = tRockinfos.get(0);
                rockinfo.setThickness(face.getDefaultCoalThickness());
                rockinfoService.updateTRockinfo(rockinfo);
            }
            return AjaxResult.success(face);
        } else return AjaxResult.error("修改工作面信息失败");
    }

    /**
     * 删除采煤工作面
     */
    @Log(title = "采煤工作面", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation("删除采煤工作面")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(tWorkingFaceOfCoalMiningService.deleteTWorkingFaceOfCoalMiningByIds(ids));
    }

    /**
     * 导出工作面数据
     *
     * @return 工作面数据
     */
    @Log(title = "采煤工作面", businessType = BusinessType.EXPORT)
    @GetMapping("/export/{id}")
    @ApiOperation("导出采煤工作面数据")
    public void exportData(@PathVariable Integer id, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, String> map = tWorkingFaceOfCoalMiningService.exportData(id);
        if (map != null) {
            String fileName = URLEncoder.encode(map.get("name") + ".wfdb", "UTF-8");
            try {

                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

                try (PrintWriter writer = response.getWriter()) {
                    String rawStr = mapper.writeValueAsString(map);
                    HashMap<String, String> resultMap = new HashMap<>();

                    // 加密导出数据
                    byte[] key = "ZZHKZZHKzzhkzzhk".getBytes();
                    SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
                    String encryptHex = aes.encryptHex(rawStr);
                    resultMap.put("name", map.get("name"));
                    resultMap.put("data", encryptHex);
                    writer.write(mapper.writeValueAsString(resultMap));
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("工作面信息不存在");
        }
    }

    /**
     * 导入工作面数据
     *
     * @param file 数据文件
     * @return 结果
     */
    @PostMapping("/import")
    @ApiOperation("导入采煤工作面数据")
    @Log(title = "采煤工作面数据导入", businessType = BusinessType.IMPORT)
    public AjaxResult importData(@ApiParam(name = "file", value = "工作面数据文件（.wfdb结尾）") @RequestParam("file") MultipartFile file) throws JsonProcessingException {
        // 开发用读取数据
        /*HashMap<String, String> map = tWorkingFaceOfCoalMiningService.exportData(119);
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);*/
        ObjectMapper mapper = new ObjectMapper();
        String filename = file.getOriginalFilename();
        if (filename != null && filename.endsWith(".wfdb")) {
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException("数据读取失败，请稍后重试。");
            }
            String dataString = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
            HashMap<String, String> dataMap = mapper.readValue(dataString, HashMap.class);
            if (dataMap.size() >= 3) {
                // 老版本，未加密，原样导入
                try {
                    return tWorkingFaceOfCoalMiningService.importData(dataString);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("数据解析失败，请稍后重试。");
                }
            } else {
                // 解密导入数据
                byte[] key = "ZZHKZZHKzzhkzzhk".getBytes();
                SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key);
                String decryptStr = aes.decryptStr(dataMap.get("data"), CharsetUtil.CHARSET_UTF_8);
                try {
                    return tWorkingFaceOfCoalMiningService.importData(decryptStr);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("数据解析失败，请稍后重试。");
                }
            }
        } else {
            return AjaxResult.error("导入数据文件有误，请导入.wfdb文件。");
        }
    }
}
