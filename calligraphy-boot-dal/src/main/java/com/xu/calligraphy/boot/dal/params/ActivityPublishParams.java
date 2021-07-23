package com.xu.calligraphy.boot.dal.params;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author xu
 * @date 2020/1/4 18:31
 */
@Data
public class ActivityPublishParams {
    private String name;

    private String beginTime;

    private String endTime;

    private String address;

    private String type;

    private String host;

    private Integer limitUser;

    private BigDecimal cost;

    private String content;
}
