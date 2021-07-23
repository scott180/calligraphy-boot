package com.xu.calligraphy.boot.service;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.query.UserActivityQuery;

/**
 * @author xu
 * @date 2020/1/1 22:16
 */
public interface UserActivityService {
    Result queryUserActivityList(UserActivityQuery query);

    Result joinActivity(Long userId, Long activityId);

    Result cancelActivity(Long userId, Long activityId);
}
