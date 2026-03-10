package com.ruoyi.hh.util.Enum;

/**
 * 测点报警/异常类型字典
 */
public enum MeasurePointWarnEnum {
    CHAOXIANBAOJING("001","超限报警"),  //基站不通
    DUANDIANBAOJING("002","断电报警"), //标校
    KUIDIANYICHANG("003","馈电报警"),  //超量程
    CHUANGANQIDUANXIAN("004","传感器断线"), //超上限报警
    JIZHANDUANDIAN("005","基站断电"),  //超下限报警
    JIZHANBUTONG("006","基站不通"),
    BIAOJIAO("007","标校"),
    CHAOLIANGCHENG("008","超量程"),
    CHAOSHANGXIANYUJING("009","超上限预警"),
    CHAOXIAXIANYUJING("010","超下限预警");

    private final String code;
    private final String value;

    private MeasurePointWarnEnum(String code,String value) {
        this.code=code;
        this.value=value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getMeasurePointWarn(String key){
        for(MeasurePointWarnEnum measurePointWarnEnum:MeasurePointWarnEnum.values())
        {
            if(measurePointWarnEnum.code.equals(key)){
                return measurePointWarnEnum.value;
            }
        }
        return null;
    }
}
