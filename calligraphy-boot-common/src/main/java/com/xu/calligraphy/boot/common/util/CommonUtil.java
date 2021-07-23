package com.xu.calligraphy.boot.common.util;

import java.text.SimpleDateFormat;

/**
 * @author xyq
 * @date 2021/7/23 14:20
 */
public class CommonUtil {

    /*** 时间格式化*/
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*** 小时格式化*/
    public static final SimpleDateFormat SIMPLE_HOUR_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH");
    /*** 日期格式化*/
    public static final SimpleDateFormat SIMPLE_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_MONTH = new SimpleDateFormat("yyyy-MM");
}
