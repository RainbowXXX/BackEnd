package site.rainbowx.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    public enum UserPermission {
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UserPermission permission;

    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    @Column(length = 128, unique = true, updatable = false)
    private String username;

    @Column(length = 64)
    private String passwordHash;

    @Column(length = 36)
    private String salt;

    @Column(length = 128)
    private String Nickname;

    @Column(length = 11)
    private String PhoneNumber;

    @Column(length = 128)
    private String Address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cart> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orders> ordersList;

    public UserPermission getPermission() {
        return permission;
    }

    public void setPermission(UserPermission permission) {
        this.permission = permission;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public User(Long id, String avatar, String username, String passwordHash, String salt, String nickname, String phoneNumber, String address) {
        this.id = id;
        this.avatar = avatar;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        Nickname = nickname;
        PhoneNumber = phoneNumber;
        Address = address;
    }
}
