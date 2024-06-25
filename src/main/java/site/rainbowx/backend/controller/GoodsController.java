package site.rainbowx.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.entity.Goods;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.service.GoodsService;
import site.rainbowx.backend.service.UserService;
import site.rainbowx.backend.utils.TokenUtils;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;

    public static class AddArg {
        public String token;
        public String name;
        public int price;
        public String thumbnail;
        public String description;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addGoods(@RequestBody AddArg addArg) {
        String username = TokenUtils.validateToken(addArg.token);
        if (username == null) {
            return false;
        }

        User user = userService.getUserByUsername(username);

        if(user == null || user.getPermission().compareTo(User.UserPermission.ADMIN)<0) {
            return false;
        }

        Goods goods = new Goods();
        goods.setName(addArg.name);
        goods.setPrice(addArg.price);
        goods.setThumbnail(addArg.thumbnail);
        goods.setDescription(addArg.description);
        goodsService.saveGoods(goods);
        return true;
    }
}
