package me.musclegeeker.oauth.controller;

import me.musclegeeker.oauth.domain.OAuthUser;
import me.musclegeeker.oauth.domain.User;
import me.musclegeeker.oauth.repository.OAuthUserRepository;
import me.musclegeeker.oauth.repository.UserRepository;
import me.musclegeeker.oauth.service.OAuthServiceDecorator;
import me.musclegeeker.oauth.service.OAuthServices;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
@Controller
public class AccountController {
    public static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    OAuthServices oAuthServices;
    @Autowired
    OAuthUserRepository oAuthUserRepository;
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = {"", "/login"}, method = RequestMethod.GET)
    public String showLogin(Model model) {
        model.addAttribute("oAuthServices", oAuthServices.getAllOAuthServices());
        return "index";
    }

    @RequestMapping(value = "/oauth/{type}/callback", method = RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = true) String code,
                           @PathVariable(value = "type") String type,
                           HttpServletRequest request, Model model) {
        OAuthServiceDecorator oAuthService = oAuthServices.getOAuthService(type);
        Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
        OAuthUser oAuthInfo = oAuthService.getOAuthUser(accessToken);
        OAuthUser oAuthUser = oAuthUserRepository.findByOAuthTypeAndOAuthId(oAuthInfo.getoAuthType(),
                oAuthInfo.getoAuthId());
        if (oAuthUser == null) {
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "register";
        }
        request.getSession().setAttribute("oauthUser", oAuthUser);
        return "redirect:/success";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, User user,
                           @RequestParam(value = "oAuthType", required = false, defaultValue = "") String oAuthType,
                           @RequestParam(value = "oAuthId", required = true, defaultValue = "") String oAuthId,
                           HttpServletRequest request) {
        OAuthUser oAuthInfo = new OAuthUser();
        oAuthInfo.setoAuthId(oAuthId);
        oAuthInfo.setoAuthType(oAuthType);
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errorMessage", "用户名已存在");
            model.addAttribute("oAuthInfo", oAuthInfo);
            return "register";
        }
        user = userRepository.save(user);
        OAuthUser oAuthUser = oAuthUserRepository.findByOAuthTypeAndOAuthId(oAuthType, oAuthId);
        if (oAuthUser == null) {
            oAuthInfo.setUser(user);
            oAuthUser = oAuthUserRepository.save(oAuthInfo);
        }
        request.getSession().setAttribute("oauthUser", oAuthUser);
        return "redirect:/success";
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET)
    @ResponseBody
    public Object success(HttpServletRequest request) {
        return request.getSession().getAttribute("oauthUser");
    }
}
