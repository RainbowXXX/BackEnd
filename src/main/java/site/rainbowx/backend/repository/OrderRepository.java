package site.rainbowx.backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.rainbowx.backend.entity.Orders;
import site.rainbowx.backend.entity.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);

    @Modifying
    @Transactional
    @Query("UPDATE Orders o SET o.is_paid = :is_paid WHERE o.id = :id")
    void updateById(@Param("id")Long id, @Param("is_paid")Boolean is_paid);
}
