package com.ruoyi.hh.util.Enum;

/**
 * 传感器类型编码字典
 */
public enum SensorTypeEnum {
    JIAWAN("0001","甲烷"),FENGSU("0002","风速"),HUANJINGWENDU("0003","环境温度"),YIYANGHUATAN("0004","一氧化碳"),FENGYA("0005","风压"),
    FUYA("0006","负压"),SHUICHISHUIWEI("0007","水池水位"),    MEIWEI("0008","煤位"),LIUHUAQING("0009","硫化氢"),SHUIWENDU("0010","水温度"),
    GAODINONGDUWASI("0011","高低浓度瓦斯"),YANGQI("0012","氧气"),ERYANGHUATAN("0013","二氧化碳"),FENCHEN("0014","粉尘"),DIANYA("0015","电压"),
    PINLV("0016","频率"),DIANLIU("0017","电流"),SHIDU("0018","湿度"),FENGLIANG("0019","风量"),DINGBANLICENGWEIYI("0020","顶板离层位移"),
    BATIWEIYI("0021","坝体位移"),GUANDAOWASI("0022","管道瓦斯"),GUANDAOWENDU("0023","管道温度"),SHUIZHI("0024","水质"),GUANDAOYALI("0025","管道压力"),
    ZHOUCHENGWENDU("0026","轴承温度"),ZAOYIN("0027","噪音"),DIANJIWENDU("0028","电机温度"),SHUIKUWENDU("0029","水库温度"),QINRUNXIAN("0030","浸润线"),
    JIANGYULIANG("0031","降雨量"),YEYAYALI("0032","液压压力"),WEIYANYINGLI("0033","围岩应力"),ZUANKONGYINGLI("0034","钻孔应力"),MAOGANYINGLI("0035","锚杆应力"),
    HUNHEWASILIULINAG("0036","混合瓦斯流量"),CHUNWASILIULIANG("0037","纯瓦斯流量"),GUANDAOYIYANGHUATAN("0038","管道一氧化碳"),QINGQI("0039","氢气"),
    GUANDAOLIULIANG("0040","管道流量"),ERYANGHUADAN("0041","二氧化氮"),ERYANGHUALIU("0042","二氧化硫"),JIAGUANGJIAWAN("0043","激光甲烷"),
    ANQI("0044","氨气"),DANQI("0045","氮气"),YIXI("0046","乙烯"),YIWAN("0047","乙烷"),YAQIANG("0048","压强"),YEWEI("0049","液位"),
    WUWEI("0050","物位"),KAIDU("0051","开度"),GAODU("0052","高度"),LIULIANG("0053","流量"),WASISHISHICHUNLIANG("0054","瓦斯实时纯量"),
    WASIRILEIJICHUNLIANG("0055","瓦斯日累计纯量"),WASIYUELEIJICHUNLIANG("0056","瓦斯月累计纯量"),
    JUBUTONGFENG("1001","局部通风机"),FENGMIN("1002","风门"),FENGTONGZHUANGTAI("1003","风筒状态"),SHEBEIKAITING("1004","设备开停"),
    KAIGUAN("1005","开关"),FENGXIANG("1006","风向"),MEICANGKONGMAN("1007","煤仓空满"),YANWU("1008","烟雾"),DUANKAIQI("1009","断电器"),
    ZHUYAOTONGFENGJI("1010","主要通风机"),KUIDIANQI("1011","馈电器"),SHENGGUANGBAOJINGQI("1012","声光报警器"),
    JILIANGKAITINGKONGZHIQI("1013","计量开停控制器"),KONGZHILIANG("1014","控制量"),KUIDIAN("1015","馈电"),CHANLIANG("3001","产量"),
    WASICHOUFANGLIANG("3002","瓦斯抽放量"),PAISHUILIANG("3003","排水量"),GOUSHU("3004","钩数"),SHUILIULIANG("3005","水流量"),
    FENZHAN("3006","分站"),DIANYUANZHUANGTAI("3007","电源状态");

    public final String code;
    public final String value;

    private SensorTypeEnum(String code,String value) {
        this.code=code;
        this.value=value;
    }

    /**
     * 将传感器类型编码转换成中文传感器类型
     * @return 结果
     */
    public static String getSensorType(String key){
        for(SensorTypeEnum sensorTypeEnum:SensorTypeEnum.values()){
            if(sensorTypeEnum.code.equals(key)){
                return sensorTypeEnum.value;
            }
        }
        return null;
    }

}