package com.xu.calligraphy.boot.start.controller;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.params.ActivityPublishParams;
import com.xu.calligraphy.boot.dal.query.ActivityQuery;
import com.xu.calligraphy.boot.service.ActivityService;
import com.xu.calligraphy.boot.service.UserActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu
 * @date 2020/1/1 18:48
 */
@RestController
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private UserActivityService userActivityService;

    @ResponseBody
    @RequestMapping(value = "queryActivityList", method = RequestMethod.POST)
    public Result queryActivityList(ActivityQuery query) {
        return activityService.queryActivityList(query);
    }

    @ResponseBody
    @RequestMapping(value = "queryActivityDetail", method = RequestMethod.GET)
    public Result queryActivityDetail(Long id) {
        return activityService.queryActivityDetail(id);
    }

    @ResponseBody
    @RequestMapping(value = "publishActivity", method = RequestMethod.POST)
    public Result publishActivity(ActivityPublishParams params) {
        return activityService.publishActivity(params);
    }

    @ResponseBody
    @RequestMapping(value = "deleteActivity", method = RequestMethod.GET)
    public Result deleteActivity(Long id) {
        return activityService.deleteActivity(id);
    }

    @ResponseBody
    @RequestMapping(value = "joinActivity", method = RequestMethod.GET)
    public Result joinActivity(Long userId, Long activityId) {
        return userActivityService.joinActivity(userId, activityId);
    }

    @ResponseBody
    @RequestMapping(value = "cancelActivity", method = RequestMethod.GET)
    public Result cancelActivity(Long userId, Long activityId) {
        return userActivityService.cancelActivity(userId, activityId);
    }
}
