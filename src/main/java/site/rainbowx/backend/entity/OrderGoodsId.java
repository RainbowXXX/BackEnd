package site.rainbowx.backend.entity;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderGoodsId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id")
    public Orders order;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    public Goods goods;

    // Constructors, getters, setters, hashCode, and equals
    public OrderGoodsId() {}

    public OrderGoodsId(Orders order, Goods goods) {
        this.order = order;
        this.goods = goods;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderGoodsId that = (OrderGoodsId) o;
        return Objects.equals(order, that.order) && Objects.equals(goods, that.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, goods);
    }
}
