package site.rainbowx.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.annotation.SubController;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.entity.UserPermission;
import site.rainbowx.backend.service.UserService;
import site.rainbowx.backend.utils.ErrorUtils;
import site.rainbowx.backend.utils.HashUtils;
import site.rainbowx.backend.utils.ReturnVal;
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

        public String token;
        public UserPermission permission;
    }

    public static class UserResetLogin{
        public String token;
        public String newPassword;
    }

    public static class UserModifyInfo{
        public String token;
        public User newInfo;
    }

    @Autowired
    private UserService userService;

    @SubController
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject verification(@RequestBody UserLogin loginInfo) {
        User userInfo = userService.verification(loginInfo.username, loginInfo.password);

        return new ReturnVal.ReturnValFac()
                .ok(userInfo != null)
                .put("token", TokenUtils.generateToken(userInfo != null ? userInfo.username : ""))
                .failure("账号或密码错误")
                .build().getVal();
    }

    @SubController
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject createUser(@RequestBody RegisterInfo registerInfo) {
        String salt = TokenUtils.generateSalt();
        String passwordHash = HashUtils.calculateSHA256(registerInfo.password+salt);

        User userInfo = new User();
        userInfo.salt = salt;
        userInfo.passwordHash = passwordHash;

        userInfo.avatar = registerInfo.avatar;
        userInfo.username = registerInfo.username;
        userInfo.address = registerInfo.address;
        userInfo.phoneNumber = registerInfo.phoneNumber;
        userInfo.nickname = registerInfo.nickname;

        return new ReturnVal.ReturnValFac()
                .ok(userService.saveUser(userInfo) != null)
                .failure("Fail to create user")
                .build().getVal();
    }

    @SubController
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public JSONObject resetPasswd(@RequestBody UserResetLogin resetInfo) {
        String username = TokenUtils.validateToken(resetInfo.token);

        if(username == null){
            return new ReturnVal.ReturnValFac().failure("User token is not valid.").build().getVal();
        }
        return new ReturnVal.ReturnValFac()
                .ok(userService.changePasswd(username, resetInfo.newPassword))
                .failure("Fail to reset passwd.")
                .build().getVal();
    }

    @SubController
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public JSONObject modifyInfo(@RequestBody UserModifyInfo newUserInfo) {
        String username = TokenUtils.validateToken(newUserInfo.token);

        if(username == null){
            return new ReturnVal.ReturnValFac().failure("User token is not valid.").build().getVal();
        }
        return new ReturnVal.ReturnValFac()
                .ok(userService.updateInfo(newUserInfo.newInfo))
                .failure("Fail to modify user info.")
                .build().getVal();
    }
}