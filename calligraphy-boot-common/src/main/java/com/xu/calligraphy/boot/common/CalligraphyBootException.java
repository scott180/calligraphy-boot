package com.xu.calligraphy.boot.common;

import com.xu.calligraphy.boot.common.enums.ResultEnum;
import lombok.Data;

/**
 * @author xyq
 * @date 2021/7/23 14:15
 */
@Data
public class CalligraphyBootException extends RuntimeException {

    private Integer code;
    private String message;

    public CalligraphyBootException(String message) {
        this(ResultEnum.FAIL.getCode(), message);
    }


    public CalligraphyBootException(ResultEnum resultEnum) {
        this(resultEnum.getCode(), resultEnum.getCh());
    }

    public CalligraphyBootException(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}