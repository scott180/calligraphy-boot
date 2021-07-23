package com.xu.calligraphy.boot.common.enums;

import lombok.Data;

/**
 * @author xu
 * @date 2020/1/1 12:44
 */
public enum ResultEnum {

    /**
     * 成功
     */
    SUCCESS(200, "success", "成功"),
    /**
     * 失败
     */
    FAIL(999, "fail", "失败"),
    /**
     * 页面找不到
     */
    NOT_FOUND(404, "not_found", "页面找不到"),

    ID_IS_NULL(501, "id_is_null", "id不能为空"),

    RECORD_NOT_EXIST(502, "record_not_exist", "记录不存在"),

    ;
    private int code;

    private String en;

    private String ch;

    ResultEnum(int code, String en, String ch) {
        this.code = code;
        this.en = en;
        this.ch = ch;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }
}
