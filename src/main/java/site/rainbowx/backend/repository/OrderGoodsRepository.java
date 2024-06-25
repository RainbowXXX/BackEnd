package site.rainbowx.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.rainbowx.backend.entity.OrderGoods;

@Repository
public interface OrderGoodsRepository extends JpaRepository<OrderGoods, Long> {
}
