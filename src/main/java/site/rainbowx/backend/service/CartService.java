package site.rainbowx.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.rainbowx.backend.entity.Cart;
import site.rainbowx.backend.entity.Goods;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.repository.CartRepository;
import site.rainbowx.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Cart> getCartByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getCarts).orElse(new ArrayList<>());
    }

    public List<Cart> getCartByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
        return user == null ? new ArrayList<>() : user.getCarts();
    }

    public boolean addCart(String username, Long goodsId, Integer num) {
        User user = userRepository.findByUsername(username);
        if (user == null) return false;
        Cart cart = new Cart();
        cart.setUser(user);
        Goods goods = new Goods();
        goods.setId(goodsId);
        cart.setGoods(goods);
        cart.setNumber(num);
        cartRepository.save(cart);
        return true;
    }

    public boolean deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
        return true;
    }
}
