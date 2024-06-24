package site.rainbowx.backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.rainbowx.backend.entity.Cart;
import site.rainbowx.backend.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.avatar = :#{#newInfo.avatar}, u.Nickname = :#{#newInfo.nickname}, " +
            "u.PhoneNumber = :#{#newInfo.phoneNumber}, u.Address = :#{#newInfo.address} WHERE u.username = :username")
    void updateUserByUsername(@Param("username") String username, @Param("newInfo") User newInfo);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.passwordHash = :passwordHash, u.salt = :salt WHERE u.username = :username")
    void updateUserByUsername(@Param("username") String username, @Param("passwordHash") String passwordHash, @Param("salt") String salt);
}
