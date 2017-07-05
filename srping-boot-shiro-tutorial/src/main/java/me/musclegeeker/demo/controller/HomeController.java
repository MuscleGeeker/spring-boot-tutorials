package me.musclegeeker.demo.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by MuscleGeeker on 2017/7/4.
 */
@Controller
public class HomeController {

    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, Map<String, Object> map) {
        System.out.println("HomeController.login()");

        // 登录失败从request中获取shiro处理的异常信息
        // shiroLoginFailure:登录异常类的全类名
        String exception = (String) request.getAttribute("shiroLoginFailure");
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccoutException --> 账号不存在");
                msg = "UnknownAccoutException --> 账号不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException --> 密码不正确");
                msg = "IncorrectCredentialsException --> 密码不正确";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> " + exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功，由shiro进行处理
        return "/login";
    }
}
