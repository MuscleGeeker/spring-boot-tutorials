package me.musclegeeker.demo.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by MuscleGeeker on 2017/7/5.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userList")
    public String user() {
        return "userInfo";
    }

    @RequestMapping("/userAdd")
    @RequiresPermissions("user:add")
    public String userAdd() {
        return "userAdd";
    }

}
