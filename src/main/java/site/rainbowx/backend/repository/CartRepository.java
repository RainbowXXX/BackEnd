package site.rainbowx.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.rainbowx.backend.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    void deleteByUserId(Long userId);
}
