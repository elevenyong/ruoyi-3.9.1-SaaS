package com.ruoyi.system.domain;

/**
 * with handleUUID,handle,entityId,before,to,timestamp
 */
public class Handle {
    private String handleUUID;
    private String handle;
    private String modelId;
    private String before;
    private String to;
    //private long timestamp;

    public String getHandleUUID() {
        return handleUUID;
    }

    public void setHandleUUID(String handleUUID) {
        this.handleUUID = handleUUID;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    /*public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }*/

    /*public Handle(String handleUUID, String handle, String modelId, String before, String to, long timestamp) {
        this.handleUUID = handleUUID;
        this.handle = handle;
        this.modelId = modelId;
        this.before = before;
        this.to = to;
    }*/

    public Handle(String handleUUID, String handle, String modelId, String before, String to) {
        this.handleUUID = handleUUID;
        this.handle = handle;
        this.modelId = modelId;
        this.before = before;
        this.to = to;
    }

    public Handle() {
    }
}
