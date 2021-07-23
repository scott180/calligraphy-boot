package com.xu.calligraphy.boot.start.test;

/**
 * @author xu
 * @date 2020/1/4 17:15
 */

import com.alibaba.fastjson.JSON;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.params.ActivityPublishParams;
import com.xu.calligraphy.boot.dal.query.ActivityQuery;
import com.xu.calligraphy.boot.Application;
import com.xu.calligraphy.boot.start.controller.ActivityController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

//import java.util.logging.Logger;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ActivityTest {
    private Logger logger = Logger.getLogger(ActivityTest.class.getName());
    @Autowired
    private ActivityController activityController;

    @Test
    public void testQueryActivityList() {
        ActivityQuery query = new ActivityQuery();
        //query.setBeginTime("2020-01-04 10:00:00");
        //query.setName("周日");
        Result result = activityController.queryActivityList(query);
        logger.info("result: " + JSON.toJSONString(result));
    }

    @Test
    public void testQueryActivityDetail() {
        Result result = activityController.queryActivityDetail(7L);
        logger.info("result: " + JSON.toJSONString(result));
    }

    private String content = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            ".cities {\n" +
            "    background-color:black;\n" +
            "    color:white;\n" +
            "    margin:20px;\n" +
            "    padding:20px;\n" +
            "} \n" +
            "</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<div class=\"cities\">\n" +
            "<h2>London</h2>\n" +
            "<p>\n" +
            "活动介绍 活动主题  London is the capital city of England. \n" +
            "It is the most populous city in the United Kingdom, \n" +
            "with a metropolitan area of over 13 million inhabitants.\n" +
            "</p>\n" +
            "</div> \n" +
            "\n" +
            "</body>\n" +
            "</html>\n";

    @Test
    public void testPublishActivity() {
        ActivityPublishParams publishParams = new ActivityPublishParams();
        publishParams.setName("武功山之旅");
        publishParams.setAddress("武功山");
        publishParams.setBeginTime("2020-01-04 09:00:00");
        publishParams.setEndTime("2020-01-04 14:00:00");
        publishParams.setContent(content);
        Result result = activityController.publishActivity(publishParams);
        logger.info("result: " + JSON.toJSONString(result));
    }

    @Test
    public void testDeleteActivity() {
        Result result = activityController.deleteActivity(7L);
        logger.info("result: " + JSON.toJSONString(result));
    }

    @Test
    public void testJoinActivity() {
        Long userId = 1L;
        Long activityId = 1L;
        Result result = activityController.joinActivity(userId,activityId);
        logger.info("result: " + JSON.toJSONString(result));
    }

    @Test
    public void testCancelActivity() {
        Long userId = 1L;
        Long activityId = 1L;
        Result result = activityController.cancelActivity(userId,activityId);
        logger.info("result: " + JSON.toJSONString(result));
    }
}
