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


}
