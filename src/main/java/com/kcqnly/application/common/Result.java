package com.kcqnly.application.common;

import lombok.Data;

@Data
public class Result {
    /**ajax响应码500出错*/
    public static final int AJAX_ERROR = 500;
    /**ajax响应码200成功*/
    public static final int AJAX_SUCCESS = 200;


    private Integer status;
    private String msg;
    private Object data;

    public Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }


    public Result(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    public Result(Integer status) {
        this.status = status;
    }


    public Result(Integer state, Object data) {
        this.status = state;
        this.data = data;
    }

    public static Result error(String msg) {
        return new Result(500, msg, null);
    }
    public static Result ok(String msg,Object data) {
        return new Result(200, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(200, "成功", data);
    }
}
