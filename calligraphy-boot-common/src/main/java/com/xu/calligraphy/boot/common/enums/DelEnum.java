package com.xu.calligraphy.boot.common.enums;

/**
 * @author xu
 * @date 2020/1/1 22:40
 */
public enum DelEnum {
    /**
     * 未删除
     */
    NOT_DEL(0, "未删除"),
    /**
     * 已删除
     */
    DELETED(1, "已删除"),
    ;
    private Integer code;
    private String description;


    DelEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
