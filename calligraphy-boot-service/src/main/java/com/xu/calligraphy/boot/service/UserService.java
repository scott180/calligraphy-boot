package com.xu.calligraphy.boot.service;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.query.UserQuery;

public interface UserService {
    String queryLoginName(Long id);

    Result selectById(Long id);

    Result queryUserList(UserQuery query);

}
