package site.rainbowx.backend.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class OrderGoods {

    @EmbeddedId
    private OrderGoodsId id;

    private int quantity;

    // Constructors, getters, setters
    public OrderGoods() {}

    public OrderGoods(Orders order, Goods goods, int quantity) {
        this.id = new OrderGoodsId(order, goods);
        this.quantity = quantity;
    }

    // other getters and setters
}
