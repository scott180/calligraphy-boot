package com.xu.calligraphy.boot.service.impl;

import com.xu.calligraphy.boot.common.Page;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.common.enums.DelEnum;
import com.xu.calligraphy.boot.common.util.CopyUtil;
import com.xu.calligraphy.boot.dal.mapper.ActivityDOMapper;
import com.xu.calligraphy.boot.dal.mapper.UserActivityDOMapper;
import com.xu.calligraphy.boot.dal.model.UserActivityDO;
import com.xu.calligraphy.boot.dal.query.ActivityQuery;
import com.xu.calligraphy.boot.dal.query.UserActivityQuery;
import com.xu.calligraphy.boot.service.UserActivityService;
import com.xu.calligraphy.boot.service.dto.UserActivityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author xu
 * @date 2020/1/1 22:16
 */
@Service
public class UserActivityServiceImpl implements UserActivityService {

    @Autowired
    private UserActivityDOMapper userActivityDOMapper;

    @Autowired
    private ActivityDOMapper activityDOMapper;

    @Override
    public Result queryUserActivityList(UserActivityQuery query) {
        Long count = userActivityDOMapper.queryUserActivityListCount(query);
        Result result = new Result();
        if (count > 0) {
            List<UserActivityDO> list = userActivityDOMapper.queryUserActivityList(query);
            List<UserActivityDTO> dtoList = CopyUtil.copyPropertiesList(list, UserActivityDTO.class);
            dtoList.stream().forEach(userActivityDTO -> {
                userActivityDTO.setCancelText("1".equals(userActivityDTO.getCancel()) ? "是" : "否");
            });
            Page page = new Page(query.getCurrentPage(), count, dtoList);
            result.setContent(page);
        }
        return result;
    }

    @Override
    public Result joinActivity(Long userId, Long activityId) {
        Result result = new Result();
        checkUser(userId);
        result = checkActivity(activityId);
        if (result.isSuccess()) {
            UserActivityDO record = new UserActivityDO();
            record.setActivityId(activityId);
            record.setUserId(userId);
            record.setCreateDate(new Date());
            record.setModifyDate(new Date());
            record.setCancel(DelEnum.NOT_DEL.getCode());
            userActivityDOMapper.insert(record);
        }
        return result;
    }

    @Override
    public Result cancelActivity(Long userId, Long activityId) {
        Result result = new Result();
        checkUser(userId);
        result = checkActivity(activityId);
        if (result.isSuccess()) {
            UserActivityQuery query = new UserActivityQuery();
            query.setActivityId(activityId);
            query.setUserId(userId);
            List<UserActivityDO> list = userActivityDOMapper.queryUserActivityList(query);
            if(!CollectionUtils.isEmpty(list)){
                list.stream().forEach(userActivityDO -> {
                    userActivityDO.setCancel(DelEnum.DELETED.getCode());
                    userActivityDO.setModifyDate(new Date());
                });
                userActivityDOMapper.updateBatch(list);
            }
        }
        return result;
    }

    private Result checkActivity(Long activityId) {
        Result result = new Result(true);
        ActivityQuery query = new ActivityQuery();
        query.setIdList(Arrays.asList(activityId));
        query.setDel(DelEnum.NOT_DEL.getCode());
        List list = activityDOMapper.queryActivityList(query);
        if (CollectionUtils.isEmpty(list)) {
            return Result.error("活动不存在");
        }
        return result;
    }

    private void checkUser(Long userId) {
    }
}
