package site.rainbowx.backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderGoods> orderGoodsList = new ArrayList<>();

    // Constructors, getters, setters

    public Orders() {}

    public Orders(User user) {
        this.user = user;
    }

    // other getters and setters
}
