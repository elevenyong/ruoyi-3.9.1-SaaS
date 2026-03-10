package com.ruoyi.hh.util;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;



public class Statistics {

    @Value("${printLog}")
    private boolean printlog;
    int count = 0; // num values so far
    double mean = Double.NaN; // E[X]
    double meanOfSquares = Double.NaN; // E[X^2]
    LimitQueue<Double> list;

    public Statistics(LimitQueue<Double> list) {
        this.list=list;
        for(double d:list) {
            add(d);
        }
    }

    public void add(final double value) {
        if (count == 0) {
            mean = value;
            meanOfSquares = value  * value;
        } else {
            mean = (mean * count + value) / (count + 1);
            meanOfSquares = (meanOfSquares * count + value * value) / (count + 1);
        }
        count++;
    }

    public double getMean() {
        // sum of all values divided by count
        return mean;
    }

    public double getVariance() {
        // σ^2 = E[X^2] - E[X]^2;
        return meanOfSquares - mean * mean;
    }

    public double getStandardDeviation() {
        // variance is square of standard deviance
        return Math.sqrt(getVariance());
    }

    public List<Integer> checkData(double n) {
        List<Integer> errData=new ArrayList<>();
        double mean = getMean();
        double sigma = getStandardDeviation();
        for(int i=0;i<list.size()-1;i++) {
            if(formateData(list.getindex(i)) <formateData(mean - n*sigma) || formateData(list.getindex(i)) > formateData(mean + n*sigma)) {
                if(printlog){
                    System.err.println("范围："+formateData(mean-n*sigma)+"----"+formateData(mean+n*sigma)+",异常值"+list.getindex(i)+",下标为"+i);
                }
                errData.add(i);
            }
        }
        return errData;
    }

    /**
     * 取两位小数
     * @param data
     * @return
     */
    public double formateData(double data){
        return Double.parseDouble(String.format("%.2f",data));
    }
}
