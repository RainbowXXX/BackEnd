package site.rainbowx.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.entity.Cart;
import site.rainbowx.backend.service.CartService;
import site.rainbowx.backend.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    static class GetCartInfo{
        public String token;
    }

    static class DeleteCartInfo{
        public String token;
        public Long cartId;
    }

    static class AddCartInfo{
        public String token;
        public Long goodsId;
        public Integer number;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    private List<Cart> getAllCart(@RequestBody GetCartInfo getCartInfo) {
        String username = TokenUtils.ValidateToken(getCartInfo.token);
        return username == null ? new ArrayList<>() : cartService.getCartByUserName(username);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private boolean addCart(@RequestBody AddCartInfo addCartInfo) {
        String username = TokenUtils.ValidateToken(addCartInfo.token);
        return username != null && cartService.addCart(username, addCartInfo.goodsId, addCartInfo.number);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private boolean deleteCart(@RequestBody DeleteCartInfo deleteCartInfo) {
        String username = TokenUtils.ValidateToken(deleteCartInfo.token);
        return username != null && cartService.deleteCart(deleteCartInfo.cartId);
    }
}
