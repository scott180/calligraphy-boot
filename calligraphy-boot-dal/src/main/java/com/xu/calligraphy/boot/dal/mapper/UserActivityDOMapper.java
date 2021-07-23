package com.xu.calligraphy.boot.dal.mapper;

import com.xu.calligraphy.boot.dal.query.UserActivityQuery;
import com.xu.calligraphy.boot.dal.model.UserActivityDO;

import java.util.List;

public interface UserActivityDOMapper extends BaseMapper<UserActivityDO> {


    List<UserActivityDO> queryUserActivityList(UserActivityQuery query);

    Long queryUserActivityListCount(UserActivityQuery query);

    void updateBatch(List<UserActivityDO> list);
}