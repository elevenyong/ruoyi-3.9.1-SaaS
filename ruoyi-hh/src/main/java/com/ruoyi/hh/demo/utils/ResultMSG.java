package com.ruoyi.hh.demo.utils;

import org.springframework.stereotype.Component;

@Component
public class ResultMSG {
    public int Status;
    public String MSG;
    public Object Date;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public Object getDate() {
        return Date;
    }

    public void setDate(Object date) {
        Date = date;
    }
}
