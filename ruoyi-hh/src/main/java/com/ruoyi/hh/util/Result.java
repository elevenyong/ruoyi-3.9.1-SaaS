package com.ruoyi.hh.util;

public class Result<T> {

    /**
     * 状态码
     */
    private Integer status;

    /**
     * 获取状态。
     *
     * @return 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态信息,错误描述.
     */
    private final String message;

    /**
     * 获取消息内容。
     *
     * @return 消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 数据.
     */
    private T data;

    /**
     * 获取数据内容。
     *
     * @return 数据
     */
    public T getData() {
        return data;
    }

    private Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private Result(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    private Result(String message) {
        this.message = message;
    }

    /**
     * 创建一个带有状态、消息和数据的结果对象.
     *
     * @param status  状态
     * @param message 消息内容
     * @param data    数据
     * @return 结构数据
     */
    public static <T> Result<T> buildResult(Status status, String message, T data) {
        return new Result<T>(status.getCode(), message, data);
    }

    /**
     * 创建一个带有状态、消息和数据的结果对象.
     *
     * @param status  状态
     * @param message 消息内容
     * @return 结构数据
     */
    public static <T> Result<T> buildResult(Status status, String message) {
        return new Result<T>(status.getCode(), message);
    }

    /**
     * 创建一个带有状态和数据的结果对象.
     *
     * @param status 状态
     * @param data   数据
     * @return 结构数据
     */
    public static <T> Result<T> buildResult(Status status, T data) {
        return new Result<T>(status.getCode(), status.getReason(), data);
    }

    /**
     * 创建一个带有状态的结果对象.
     *
     * @param status 状态
     * @return 结构数据
     */
    public static <T> Result<T> buildResult(Status status) {
        return new Result<T>(status.getCode(), status.getReason());
    }

    public enum Status {

        /**
         * 状态
         */
        OK(0, "操作成功"),
        EXIT(1, "已经存在"),
        ERROR(-1,"未知错误"),
        NULL_ERROR(9999, "数据不能为空"),
        OTHER_ERROR(402,"数据不存在");

        /**
         * 状态码,长度固定为6位的字符串.
         */
        private final Integer code;

        /**
         * 错误信息.
         */
        private final String reason;

        Status(Integer code, String reason) {
            this.code = code;
            this.reason = reason;
        }

        public Integer getCode() {
            return code;
        }

        public String getReason() {
            return reason;
        }

        @Override
        public String toString() {
            return code + ": " + reason;
        }

    }
}
