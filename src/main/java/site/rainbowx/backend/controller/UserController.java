package site.rainbowx.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.service.RedisService;
import site.rainbowx.backend.service.UserService;
import site.rainbowx.backend.utils.ErrorUtils;
import site.rainbowx.backend.utils.HashUtils;
import site.rainbowx.backend.utils.TokenUtils;

import java.util.concurrent.TimeUnit;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static class UserLogin{
        public String username;
        public String password;
    }

    public static class UserResetLogin{
        public String token;
        public String username;
        public String newPassword;
    }

    public static class UserModifyInfo{
        public String token;
        public User newInfo;
    }

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject verification(@RequestBody UserLogin loginInfo) {
        JSONObject jsonObject = new JSONObject();
        User userInfo = userService.verification(loginInfo.username, loginInfo.password);
        jsonObject.put("ok", userInfo != null);
        if (userInfo != null) {
            // 如果登陆成功，返回一个token
            jsonObject.put("token", TokenUtils.GenerateToken(userInfo.getUsername()));
        }
        return jsonObject;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User createUser(@RequestBody UserLogin loginInfo) {
        String salt = TokenUtils.GenerateSalt();
        String passwordHash = HashUtils.calculateSHA256(loginInfo.password+salt);
        User userInfo = new User(0L,null, loginInfo.username, passwordHash, salt, null, null, null);
        return userService.saveUser(userInfo);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public JSONObject resetPasswd(@RequestBody UserResetLogin resetInfo) {
        JSONObject jsonObject = new JSONObject();
        String username = TokenUtils.ValidateToken(resetInfo.token);
        if(username == null) {
            jsonObject.put("ok", false);
            logger.warn("User {} Validate Token Failed.", resetInfo.username);
            return jsonObject;
        }
        ErrorUtils.Ensure(username.equals(resetInfo.username), "The token should match the username.");
        boolean res = userService.changePasswd(resetInfo.username, resetInfo.newPassword);
        jsonObject.put("ok", res);
        return jsonObject;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public JSONObject modifyInfo(@RequestBody UserModifyInfo newUserInfo) {
        JSONObject jsonObject = new JSONObject();
        String username = TokenUtils.ValidateToken(newUserInfo.token);
        if(username == null) {
            jsonObject.put("ok", false);
            logger.warn("User {} Validate Token Failed.", newUserInfo.newInfo.getUsername());
            return jsonObject;
        }
        ErrorUtils.Ensure(username.equals(newUserInfo.newInfo.getUsername()), "The token should match the username.");
        boolean res = userService.updateInfo(newUserInfo.newInfo);
        jsonObject.put("ok", res);
        return jsonObject;
    }
}