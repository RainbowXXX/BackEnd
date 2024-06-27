package site.rainbowx.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.entity.Goods;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.entity.UserPermission;
import site.rainbowx.backend.service.GoodsService;
import site.rainbowx.backend.service.UserService;
import site.rainbowx.backend.utils.ReturnVal;
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
    public JSONObject getAllGoods() {
        List<Goods> goodsList = goodsService.getAllGoods();
        return new ReturnVal.ReturnValFac()
                .ok(goodsList != null)
                .put("goods", goodsList)
                .failure("Fail to read goods list.")
                .build().getVal();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject addGoods(@RequestBody AddArg addArg) {
        String username = TokenUtils.validateToken(addArg.token);
        if (username == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }

        User user = userService.getUserByUsername(username);

        if(user == null || user.permission.compareTo(UserPermission.ADMIN)<0) {
            return new ReturnVal.ReturnValFac()
                    .failure("Permission denied.")
                    .build().getVal();
        }

        Goods goods = new Goods();
        goods.name = addArg.name;
        goods.price = addArg.price;
        goods.thumbnail = addArg.thumbnail;
        goods.description = addArg.description;
        goodsService.saveGoods(goods);
        return new ReturnVal.ReturnValFac()
                .ok(true)
                .build().getVal();
    }
}
