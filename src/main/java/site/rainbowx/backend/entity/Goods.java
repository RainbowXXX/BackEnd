package site.rainbowx.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 1023)
    public String description;

    @Column(nullable = false, length = 511)
    public String name;

    @Column(nullable = false)
    public Integer price;

    @Column(columnDefinition = "LONGTEXT")
    public String thumbnail;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Cart> carts;

    @OneToMany(mappedBy = "goods", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderGoods> orderGoods;

    // Getters and setters
}

