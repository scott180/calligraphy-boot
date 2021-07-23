package com.xu.calligraphy.boot.service;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.params.ActivityPublishParams;
import com.xu.calligraphy.boot.dal.query.ActivityQuery;

public interface ActivityService {

    Result queryActivityList(ActivityQuery query);

    Result queryActivityDetail(Long id);

    Result publishActivity(ActivityPublishParams params);

    Result deleteActivity(Long id);
}
