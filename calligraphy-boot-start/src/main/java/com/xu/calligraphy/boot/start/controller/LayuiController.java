package com.xu.calligraphy.boot.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xyq
 * @date 2021/7/23 14:59
 */
@Controller
public class LayuiController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}

