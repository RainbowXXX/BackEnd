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

    public OrderGoodsId getId() {
        return id;
    }

    public void setId(OrderGoodsId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders getOrder() {
        return id.getOrder();
    }

    public void setOrder(Orders order) {
        this.id.setOrder(order);
    }

    public Goods getGoods() {
        return id.getGoods();
    }

    public void setGoods(Goods goods) {
        this.id.setGoods(goods);
    }
}
