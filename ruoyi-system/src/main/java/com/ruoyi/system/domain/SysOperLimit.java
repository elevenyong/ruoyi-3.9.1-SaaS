package com.ruoyi.system.domain;

/**
 * 接口操作限制
 */
public class SysOperLimit {

    private Long userid;

    private Integer desBoreholeLimit;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getDesBoreholeLimit() {
        return desBoreholeLimit;
    }


    public void setDesBoreholeLimit(Integer desBoreholeLimit) {
        this.desBoreholeLimit = desBoreholeLimit;
    }

    @Override
    public String toString() {
        return "SysOperLimit{" +
                "userid=" + userid +
                ", desBoreholeLimit=" + desBoreholeLimit +
                '}';
    }
}
