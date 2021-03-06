package com.wangsw.blog.common;


/**
 * Created by wangsw on 2019/12/18.
 */
public class Result {
    String status;//返回状态
    String message;//返回内容
    Object data;//返回数据

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result() {
        super();
    }

    public Result(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public String toString() {
        return "{\"status\":\""+ this.status +"\",\"message\":\"" +this.message+ "\"}";
    }

}
