package site.rainbowx.backend.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 511, nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "LONGTEXT")
    private String thumbnail;

    @Column(length = 1023)
    private String description;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cart> carts;

    @ManyToMany(mappedBy = "goodsList", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Orders> orderList = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }
}
