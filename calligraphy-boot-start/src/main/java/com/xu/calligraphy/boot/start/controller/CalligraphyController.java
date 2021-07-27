package com.xu.calligraphy.boot.start.controller;

import com.alibaba.fastjson.JSON;
import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.common.util.CommonUtil;
import com.xu.calligraphy.boot.dal.params.ActivityPublishParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xyq
 * @date 2021/7/23 15:25
 */
@Controller
public class CalligraphyController {

    private static final Logger logger = LoggerFactory.getLogger(CalligraphyController.class);

    @Autowired
    private HttpServletRequest httpServletRequest;
    private static Integer num = 1;

    @RequestMapping("/shu")
    public String index() {
        String ipAddr = CommonUtil.getIpAddr(httpServletRequest);
        logger.info("shu_ip={},num={}", ipAddr, num++);
        return "书法练习轨迹--明月几时有.html";
    }

    @ResponseBody
    @RequestMapping(value = "/shu/test", method = RequestMethod.GET)
    public Result queryActivityDetail(@ModelAttribute ActivityPublishParams publishParams) {
        return Result.success(JSON.toJSONString(publishParams));
    }

}
