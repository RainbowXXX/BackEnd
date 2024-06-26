package site.rainbowx.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_goods")
@IdClass(OrderGoodsId.class)
public class OrderGoods {
    @Id
    @ManyToOne
    @JoinColumn(name = "goods_id", nullable = false)
    private Goods goods;

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    @Column(nullable = false)
    private Integer quantity;

    public OrderGoods() {
    }

    public OrderGoods(Orders orders, Goods goods, Integer quantity) {
        this.goods = goods;
        this.orders = orders;
        this.quantity = quantity;
    }
}

