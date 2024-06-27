package site.rainbowx.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.entity.UserPermission;
import site.rainbowx.backend.repository.UserRepository;
import site.rainbowx.backend.utils.HashUtils;
import site.rainbowx.backend.utils.RandomUtils;
import site.rainbowx.backend.utils.TokenUtils;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        if(userRepository.count() == 0) {
            User user = new User();
            user.username = "root";
            user.salt = TokenUtils.generateSalt();
            user.passwordHash = HashUtils.calculateSHA256("passwd"+user.salt);
            user.permission = UserPermission.ADMIN;

            userRepository.save(user);
        }
    }

    public User verification(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user == null){
            RandomUtils.RandomSleep(690, 2568);
            return null;
        }

        String input_hash = HashUtils.calculateSHA256(password + user.salt);
        if(input_hash.equals(user.passwordHash)) {
            RandomUtils.RandomSleep(690, 2568);
            return user;
        }

        RandomUtils.RandomSleep(690, 2568);
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean changePasswd(String username, String newPassword) {
        User oldInfo = userRepository.findByUsername(username);
        if(oldInfo == null){ return false; }
        String salt = TokenUtils.generateSalt();
        String passwdHash = HashUtils.calculateSHA256(newPassword + salt);
        userRepository.updateUserByUsername(username, passwdHash, salt);
        return true;
    }

    public boolean updateInfo(User newInfo) {
        userRepository.updateUserByUsername(newInfo.username, newInfo);
        return true;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}