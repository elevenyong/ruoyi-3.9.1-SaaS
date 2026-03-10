package com.ruoyi.hh.util.Enum;

/**
 * 系统编码字典
 */
public enum SysCodeEnum {
    ANQUANJIANKONGXITONG("01","安全监控系统"), //安全监控系统
    WASICHOUFANGXITONG("02","瓦斯抽放系统");   //瓦斯抽放系统

    private final String code;
    private final String value;

    private SysCodeEnum(String code,String value) {
        this.code=code;
        this.value=value;
    }

    public static String getSysCode(String key){
        for(SysCodeEnum sysCodeEnum:SysCodeEnum.values()){
            if(sysCodeEnum.code.equals(key)){
                return sysCodeEnum.value;
            }
        }
        return null;
    }
}
