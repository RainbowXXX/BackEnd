package site.rainbowx.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.rainbowx.backend.entity.Orders;
import site.rainbowx.backend.entity.User;

public interface OrderRepository extends JpaRepository<Orders, Long> {


}
