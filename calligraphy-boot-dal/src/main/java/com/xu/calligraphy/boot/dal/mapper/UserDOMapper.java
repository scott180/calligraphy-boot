package com.xu.calligraphy.boot.dal.mapper;

import com.xu.calligraphy.boot.dal.query.UserQuery;
import com.xu.calligraphy.boot.dal.model.UserDO;

import java.util.List;

public interface UserDOMapper extends BaseMapper<UserDO> {

    List<UserDO> queryUserList(UserQuery query);

    Long queryUserListCount(UserQuery query);
}