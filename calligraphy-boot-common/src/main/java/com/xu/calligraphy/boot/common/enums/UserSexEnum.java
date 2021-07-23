package com.xu.calligraphy.boot.common.enums;

/**
 * @author xu
 * @date 2020/1/1 12:44
 */
public enum UserSexEnum {
    /**
     * 男
     */
    SUCCESS(0, "man", "男"),
    /**
     * 女
     */
    WOMAN(1, "woman", "女"),
    /**
     * 未填
     */
    UNKNOWN(2, "unknown", "未填"),

    ;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    private Integer code;

    private String en;

    private String ch;

    public static String getSexTextByCode(Integer code) {
        for (UserSexEnum sexEnum : UserSexEnum.values()) {
            if (sexEnum.getCode().equals(code)) {
                return sexEnum.getCh();
            }
        }
        return UserSexEnum.UNKNOWN.getCh();
    }

    UserSexEnum(int code, String en, String ch) {
        this.code = code;
        this.en = en;
        this.ch = ch;
    }

}
