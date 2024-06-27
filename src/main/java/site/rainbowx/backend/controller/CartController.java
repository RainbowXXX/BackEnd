package site.rainbowx.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.rainbowx.backend.entity.Cart;
import site.rainbowx.backend.service.CartService;
import site.rainbowx.backend.utils.ReturnVal;
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
    private JSONObject getAllCart(@RequestParam String token) {
        String username = TokenUtils.validateToken(token);
        if (username == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }
        List<Cart> ret = cartService.getCartByUserName(username);
        return new ReturnVal.ReturnValFac()
                .ok(ret != null)
                .put("carts", ret)
                .failure("Fail to read cart list")
                .build().getVal();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    private JSONObject addCart(@RequestBody AddCartInfo addCartInfo) {
        String username = TokenUtils.validateToken(addCartInfo.token);
        if (username == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }
        return new ReturnVal.ReturnValFac()
                .ok(cartService.addCart(username, addCartInfo.goodsId, addCartInfo.number))
                .failure("Fail to add cart")
                .build().getVal();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    private JSONObject deleteCart(@RequestBody DeleteCartInfo deleteCartInfo) {
        String username = TokenUtils.validateToken(deleteCartInfo.token);
        if (username == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }
        return new ReturnVal.ReturnValFac()
                .ok(cartService.deleteCart(deleteCartInfo.cartId))
                .failure("Fail to delete cart")
                .build().getVal();
    }
}
