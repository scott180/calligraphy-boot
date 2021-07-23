package com.xu.calligraphy.boot.start.controller;

/**
 * @author xyq
 * @date 2021/7/23 15:25
 */
@Controller
public class CalligraphyController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}
