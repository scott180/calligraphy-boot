package com.xu.calligraphy.boot.dal.query;

import lombok.Data;

/**
 * @author xu
 * @date 2020/1/1 22:07
 */
@Data
public class UserActivityQuery extends BaseQuery {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 活动id
     */
    private Long activityId;
    /**
     * 是否取消 1_是 0_否
     */
    private Integer cancel;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getCancel() {
        return cancel;
    }

    public void setCancel(Integer cancel) {
        this.cancel = cancel;
    }
}
