package com.ruoyi.hh.physical.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.hh.physical.domain.WarningAlerts;
import com.ruoyi.hh.physical.service.WarningAlertsService;
import com.ruoyi.hh.util.AlertsWSServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/warning")
public class WarningAlertsController extends BaseController {

    @Resource
    private WarningAlertsService alertsService;


    /**
     * 获取全部异常信息列表
     *
     * @return result
     */
    @ApiOperation("获取全部异常信息列表")
    @GetMapping("/all")
    public AjaxResult getWarningAlerts() {
        ArrayList<WarningAlerts> faultsWarnings = alertsService.getFaultsWarningAlerts();
        ArrayList<WarningAlerts> drillingWarnings = alertsService.getDrillingWarningAlerts();
        ArrayList<WarningAlerts> coalbedsWarnings = alertsService.getCoalbedsWarningAlerts();

        List<WarningAlerts> resultLists = new ArrayList<>();
        if (faultsWarnings != null) {
            for (WarningAlerts faultsWarning : faultsWarnings) {
                faultsWarning.setLevel(faultsWarning.getType());
            }
            resultLists.addAll(faultsWarnings);
        }
        if (drillingWarnings != null) {
            for (WarningAlerts drillingWarning : drillingWarnings) {
                drillingWarning.setLevel(drillingWarning.getType());
            }
            resultLists.addAll(drillingWarnings);
        }
        if (coalbedsWarnings != null) {
            for (WarningAlerts coalbedsWarning : coalbedsWarnings) {
                coalbedsWarning.setLevel(coalbedsWarning.getType());
            }
            resultLists.addAll(coalbedsWarnings);
        }
        return AjaxResult.success(resultLists);
    }

    /**
     * 修改异常记录
     *
     * @param id
     * @param status
     * @return
     */
    @PutMapping("/status")
    public AjaxResult updateWarningAlerts(Integer id, Integer status) {
        WarningAlerts alt = alertsService.selectWarningAlertsById(id);
        alt.setStatus(status);
        int i = alertsService.updateWarningAlerts(alt);
        if (i == 1) {
            return AjaxResult.success("异常记录状态,修改成功");
        }
        return AjaxResult.error("异常记录状态,修改失败");
    }

    /**
     * 推送告警异常
     *
     * @param request alerts
     * @return result
     */
    @PostMapping("/push")
    public AjaxResult pushWarningAlerts(HttpServletRequest request) throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = streamReader.readLine()) != null) {
            sb.append(line);
        }
        String s = decodeUnicode(sb.toString());
        System.out.println("SSWSMessage:" + s);
        //String WSMessage = new String(sb.toString().getBytes(), StandardCharsets.UTF_8);
        //System.out.println("WSMessage:" + WSMessage);
        try {
            AlertsWSServer.sendInfo(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AjaxResult.success("推送成功");
    }

    /**
     * 获取异常信息
     *
     * @param x    x
     * @param y    y
     * @param z    z
     * @param dis  距离
     * @param type 类型：0=断层异常，1=钻孔异常，2=煤层异常
     * @return warningAlertsList
     */
    @ApiOperation("获取异常告警信息")
    @GetMapping("/point")
    public AjaxResult getWarningAlertsWithParams(Double x, Double y, Double z, Double dis, String type) {
        List<WarningAlerts> alertsList = alertsService.getWarningAlertsWithParams(x, y, z, dis, type, 1);
        return AjaxResult.success(alertsList);
    }

    @ApiOperation("获取历史告警记录")
    @GetMapping("/records")
    public AjaxResult getWarningAlertsRecordsList() {
        List<WarningAlerts> records = alertsService.selectWarningAlertsList(new WarningAlerts());
        return AjaxResult.success(records);
    }


    /**
     * Unicode转UTF8
     * @param unicodeStr
     * @return
     */
    public static String decodeUnicode(String unicodeStr) {
        char aChar;
        int len = unicodeStr.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = unicodeStr.charAt(x++);
            if (aChar == '\\') {
                aChar = unicodeStr.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = unicodeStr.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                return "";
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
