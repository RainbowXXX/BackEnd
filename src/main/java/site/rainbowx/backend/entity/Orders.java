package site.rainbowx.backend.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    public User user;

    @ColumnDefault("false")
    @Column(nullable = false)
    public Boolean is_paid;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<OrderGoods> orderGoods;

    // Getters and setters
}

