package com.ruoyi.hh.util.config;

/**
 * 感知数据解析工具
 */
public class PerceptionDataUtil {
    /**
     * 解析测点编码
     * ★★★……★★   ◇◇          ○○              △△△△      □□□□□□□□
     * 煤矿编码    系统编码  传感器数值类型编码    传感器类型编码   原系统测点编码
     * @param originalCode 原始测点编码
     * @return 结果
     */
    public static String coalMineCode; //煤矿编码
    public static String sysCode;  //系统编码
    public static String sensorValueType; //传感器数值类型
    public static String sensorTypeCode; //传感器类型编码
    public static String sysInteriorCode; //系统内部编码
//    public static String substationCode; //系统内部编码
    public static String analysisMeasuringPointCode(String originalCode){
        if(originalCode.trim().length()>20){
            coalMineCode=originalCode.substring(0,12);
            sysCode=originalCode.substring(12,14);
            sensorValueType=originalCode.substring(14,16);
            sensorTypeCode=originalCode.substring(16,20);
            sysInteriorCode=originalCode.substring(20,originalCode.length());
            return sysInteriorCode;
        }
        return null;
    }

    /**
     * 解析煤矿名称(煤矿编码)
     * @param originalCode 原测点编码
     * @return 结果
     */
    public static  String analysisCoalMineName(String originalCode){
        if(originalCode.trim().length()>20){
            coalMineCode=originalCode.substring(0,12);
//            if(coalMineCode.equals("410403004688")){
//                return "八矿";
//            }
            return coalMineCode;
        }
        return null;
    }

    /**
     * 解析系统编码
     * @param originalCode 原测点编码
     * @return 结果
     */
    public static String analysissysCode(String originalCode){
        if(originalCode.trim().length()>20){
            sysCode=originalCode.substring(12,14);
            if ("01".equals(sysCode)) {
                return "安全监控系统";
            }
            return "瓦斯抽放系统";
        }
        return null;
    }

    /**
     * @param originalCode 原测点编码
     * @return
     */
    public static String analysissysmeasureCode(String originalCode){
        if(originalCode.trim().length()>20){
            sysInteriorCode = originalCode.substring(20, originalCode.length());
            return sysInteriorCode;
        }
        return null;
    }

    /**
     * 编码方式：煤矿编码（12位）+△△△△△
     * @param originalCode 原分站编码
     * @return 结果
     */
    public static String analysisSubstationCode(String originalCode){
        coalMineCode=originalCode.substring(0,12);
         return originalCode.substring(12,originalCode.length());
    }

    /**
     * 测点状态
     * @param code 测点状态码
     * @return 结果
     */
    public static String measurePointStatus(int code){
        switch (code){
            case 1:
                return "报警";
            case 2:
                return "断电";
            case 4:
                return "标校";
            case 8:
                return "超量程";
            case 16:
                return "分站故障";
            case 32:
                return "不巡检";
            case 64:
                return "暂停";
            case 128:
                return "传感器故障";
            default:
                return "正常";
        }
    }

    /**
     * 分站运行状态
     * @param code 状态编码
     * @return 结果
     */
    public static String subStationWorkStatus(int code){
        switch (code){
            case 1:
                return "通讯中断";
            case 2:
                return "故障";
            case 9:
                return "未知";
            default:
                return "通讯正常";
        }
    }

    /**
     * 分站供电状态
     * @param code 状态编码
     * @return 结果
     */
    public static String subStationElectricityStatus(int code){
        switch (code){
            case 1:
                return "直流供电";
            case 2:
                return "交流供电";
            case 9:
                return "电源故障";
            default:
                return "未知";
        }
    }

    /**
     * 测点基础信息开停描述
     * @param code 编码
     * @return 结果
     */
    public static String CDDYDescribe(int code){
        switch (code){
            case 0:
                return "无烟雾";
            case 1:
                return "有烟雾";
            default:
                return "error";
        }
    }

    /**
     * 参看表B.2： 传感器类型编码
     * @param code 编码
     * @return 结果
     */
    public static String sensorType(int code){
        switch(code){
            case 1101:
                return "工作面支架阻力";
            case 1201:
                return "钻孔应力";
            case 1202:
                return "围岩应力";
            case 1301:
                return "锚杆应力";
            case 1302:
                return "锚索应力";
            case 1401:
                return "顶板离层位移";
            default:
                return "error";
        }
    }



}
