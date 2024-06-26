package site.rainbowx.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 128)
    public String address;

    @Column(length = 128)
    public String nickname;

    @Column(length = 11)
    public String phoneNumber;

    @Column(columnDefinition = "LONGTEXT")
    public String avatar;

    @Column(length = 64)
    public String passwordHash;

    @Enumerated(EnumType.ORDINAL)
    public UserPermission permission;

    @Column(length = 36)
    public String salt;

    @Column(length = 128, unique = true)
    public String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Cart> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Orders> orders;

    // Getters and setters
}
