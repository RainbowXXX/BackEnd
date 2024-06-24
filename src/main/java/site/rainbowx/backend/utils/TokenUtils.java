package site.rainbowx.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.service.RedisService;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {
    @Autowired
    private RedisService redisService;
    private static TokenUtils instance;
    private TokenUtils(){
        instance = this;
    }

    public static String GenerateToken(String username) {
        String token = UUID.randomUUID().toString();
        instance.redisService.saveWithExpire(token, username, 90, TimeUnit.DAYS);
        return token;
    }

    public static String ValidateToken(String token) {
        if(!instance.redisService.contains(token)){ return null; }
        return instance.redisService.get(token);
    }

    public static String GenerateSalt() {
        return UUID.randomUUID().toString();
    }

    public static String GetUserName(String token) {
        return instance.redisService.get(token);
    }
}
