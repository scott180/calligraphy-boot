package com.xu.calligraphy.boot.common;

import com.alibaba.fastjson.JSONArray;
import com.xu.calligraphy.boot.common.enums.ResultEnum;
import lombok.Data;

/**
 * @author xu
 * @date 2020/1/1 12:41
 */
@Data
public class Result<T> {

    private boolean success;
    private int code;
    private T content;
    private String errorMsg;

    public Result() {
    }

    public Result(Boolean success) {
        this.setCode(success ? ResultEnum.SUCCESS.getCode() : ResultEnum.FAIL.getCode());
        this.setSuccess(success);
    }

    public static Result success() {
        return success(null);
    }

    public static <T> Result success(T t) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setSuccess(true);
        result.setContent(t);
        return result;
    }

    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setSuccess(false);
        resultEnum = resultEnum == null ? ResultEnum.FAIL : resultEnum;
        result.setCode(resultEnum.getCode());
        result.setErrorMsg(resultEnum.getCh());
        return result;
    }

    public static Result error(String errorMsg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultEnum.FAIL.getCode());
        result.setErrorMsg(errorMsg);
        return result;
    }

    public static Result error(CalligraphyBootException e) {
        Result result = new Result();
        result.setCode(e.getCode() == null ? ResultEnum.FAIL.getCode() : e.getCode());
        result.setErrorMsg(e.getMessage());
        result.setContent(new JSONArray());
        return result;
    }

    public void error(int failCode, String errorMsg) {
        this.setCode(failCode);
        this.setErrorMsg(errorMsg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
