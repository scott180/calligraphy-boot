package com.xu.calligraphy.boot.dal.query;

import lombok.Data;

import java.util.List;

/**
 * @author xu
 * @date 2020/1/1 18:50
 */
@Data
public class ActivityQuery extends BaseQuery {
    /**
     * 开始时间
     */
    private String beginTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 创建人
     */
    private String creator;
    /**
     * 是否删除
     */
    private Integer del;

    /**
     * 是否取消
     */
    private String cancel;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户id列表
     */
    private List<Long> idList;
    /**
     * 活动名称
     */
    private String name;
}
