package site.rainbowx.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public Integer number;

    @ManyToOne
    @JoinColumn(name = "gid", nullable = false)
    public Goods goods;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    public User user;

    // Getters and setters
}
