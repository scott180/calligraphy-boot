package com.xu.calligraphy.boot.start.controller;

import com.xu.calligraphy.boot.common.Result;
import com.xu.calligraphy.boot.dal.query.UserQuery;
import com.xu.calligraphy.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("selectById")
    public Result selectById(Long id) {
        return userService.selectById(id);
    }

    @ResponseBody
    @RequestMapping("hello")
    public String hello() {
        return "hello world";
    }

    @ResponseBody
    @RequestMapping(value = "queryUserList",method = RequestMethod.POST)
    public Result queryUserList(UserQuery query) {
        return userService.queryUserList(query);
    }
}
