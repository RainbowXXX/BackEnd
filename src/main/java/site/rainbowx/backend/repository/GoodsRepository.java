package site.rainbowx.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.rainbowx.backend.entity.Goods;

public interface GoodsRepository  extends JpaRepository<Goods, Long> {
    Goods findByName(String name);


}
