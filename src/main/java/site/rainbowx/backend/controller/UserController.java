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
import site.rainbowx.backend.service.UserService;
import site.rainbowx.backend.utils.ErrorUtils;
import site.rainbowx.backend.utils.HashUtils;
import site.rainbowx.backend.utils.TokenUtils;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public static class UserLogin{
        public String username;
        public String password;
    }

    public static class RegisterInfo{
        public String username;
        public String password;

        public String avatar;
        public String address;
        public String nickname;
        public String phoneNumber;
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
            jsonObject.put("token", TokenUtils.generateToken(userInfo.username));
        }
        return jsonObject;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User createUser(@RequestBody RegisterInfo registerInfo) {
        String salt = TokenUtils.generateSalt();
        String passwordHash = HashUtils.calculateSHA256(registerInfo.password+salt);

        User userInfo = new User();
        userInfo.salt = salt;

        userInfo.username = registerInfo.username;
        userInfo.address = registerInfo.address;
        userInfo.phoneNumber = registerInfo.phoneNumber;
        userInfo.nickname = registerInfo.nickname;


        return userService.saveUser(userInfo);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public JSONObject resetPasswd(@RequestBody UserResetLogin resetInfo) {
        JSONObject jsonObject = new JSONObject();
        String username = TokenUtils.validateToken(resetInfo.token);
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
        String username = TokenUtils.validateToken(newUserInfo.token);
        if(username == null) {
            jsonObject.put("ok", false);
            logger.warn("User {} Validate Token Failed.", newUserInfo.newInfo.username);
            return jsonObject;
        }
        ErrorUtils.Ensure(username.equals(newUserInfo.newInfo.username), "The token should match the username.");
        boolean res = userService.updateInfo(newUserInfo.newInfo);
        jsonObject.put("ok", res);
        return jsonObject;
    }
}