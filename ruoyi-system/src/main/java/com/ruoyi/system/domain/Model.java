package com.ruoyi.system.domain;

/**
 * with x,y,z,entityId
 */
public class Model {
    private double x;
    private double y;
    private double z;
    private String modelId;

    public Model() {
    }

    public Model(double x, double y, double z, String modelId) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.modelId = modelId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setXYZ(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
}
