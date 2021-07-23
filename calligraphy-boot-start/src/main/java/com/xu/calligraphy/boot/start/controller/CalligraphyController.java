package com.xu.calligraphy.boot.start.controller;

import com.xu.calligraphy.boot.common.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
