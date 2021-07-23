package com.xu.calligraphy.boot.service.impl;

import com.xu.calligraphy.boot.common.Page;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.common.enums.UserSexEnum;
import com.xu.calligraphy.boot.common.util.CopyUtil;
import com.xu.calligraphy.boot.dal.mapper.UserDOMapper;
import com.xu.calligraphy.boot.dal.model.UserDO;
import com.xu.calligraphy.boot.dal.query.UserQuery;
import com.xu.calligraphy.boot.service.UserService;
import com.xu.calligraphy.boot.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDOMapper userDOMapper;

    @Override
    public String queryLoginName(Long id) {
        return "test";
    }

    @Override
    public Result selectById(Long id) {
        return Result.success(userDOMapper.selectByPrimaryKey(id));
    }

    @Override
    public Result queryUserList(UserQuery query) {
        Long count = userDOMapper.queryUserListCount(query);
        Result result = new Result();
        if (count > 0) {
            List<UserDO> list = userDOMapper.queryUserList(query);
            List<UserDTO> dtoList = CopyUtil.copyPropertiesList(list, UserDTO.class);
            dtoList.stream().forEach(userDTO -> {
                userDTO.setSexText(UserSexEnum.getSexTextByCode(userDTO.getSex()));
            });
            Page page = new Page(query.getCurrentPage(), count, dtoList);
            result.setContent(page);
        }
        return result;
    }
}
