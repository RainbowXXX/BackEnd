package site.rainbowx.backend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import site.rainbowx.backend.repository.UserRepository;
import site.rainbowx.backend.service.RedisService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class TokenUtils {
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    private static TokenUtils instance;

    private TokenUtils() {
        instance = this;
    }

    public static String generateToken(String username) {
        String token = UUID.randomUUID().toString();
        instance.redisService.saveWithExpire(token, username, 90, TimeUnit.DAYS);
        return token;
    }

    public static String validateToken(String token) {
        if (!instance.redisService.contains(token)) {
            return null;
        }
        return instance.redisService.get(token);
    }

    public static String generateSalt() {
        return UUID.randomUUID().toString();
    }

    public static String getUserName(String token) {
        return instance.redisService.get(token);
    }
}
