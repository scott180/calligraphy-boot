package com.xu.calligraphy.boot.dal.mapper;

import com.xu.calligraphy.boot.dal.query.ActivityQuery;
import com.xu.calligraphy.boot.dal.model.ActivityDO;

import java.util.List;

public interface ActivityDOMapper extends BaseMapper<ActivityDO> {
    List<ActivityDO> queryActivityList(ActivityQuery query);

    Long queryActivityListCount(ActivityQuery query);
}