package site.rainbowx.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class OrderGoodsId implements Serializable {
    private Long goods;
    private Long orders;

    // Default constructor
    public OrderGoodsId() {}

    public OrderGoodsId(Long goods, Long orders) {
        this.goods = goods;
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderGoodsId that = (OrderGoodsId) o;
        return Objects.equals(goods, that.goods) && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods, orders);
    }
}
