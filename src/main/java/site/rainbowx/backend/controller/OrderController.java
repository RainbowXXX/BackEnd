package site.rainbowx.backend.controller;

import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.rainbowx.backend.entity.OrderGoods;
import site.rainbowx.backend.entity.Orders;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.service.OrderService;
import site.rainbowx.backend.service.UserService;
import site.rainbowx.backend.utils.ReturnVal;
import site.rainbowx.backend.utils.TokenUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    public static class AddArgs{
        public String token;
        public List<OrderGoods> orderGoods;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public JSONObject getAllOrders(@RequestParam String token) {
        String username = TokenUtils.getUserName(token);
        if (username == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }

        List<Orders> orders = orderService.getOrdersByUser(user);

        return new ReturnVal.ReturnValFac()
                .ok(orders != null)
                .put("orders", orders)
                .failure("Fail to read order list.")
                .build().getVal();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject addOrder(@RequestBody AddArgs addArgs) {
        Orders orders = new Orders();
        String username = TokenUtils.validateToken(addArgs.token);
        if (username == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }
        orders.user = userService.getUserByUsername(username);
        if (orders.user == null) {
            return new ReturnVal.ReturnValFac()
                    .failure("Invalid user token.")
                    .build().getVal();
        }

        orders.orderGoods.addAll(addArgs.orderGoods);
        orderService.saveOrder(orders);

        return new ReturnVal.ReturnValFac()
                .ok(orderService.saveOrder(orders) != null)
                .failure("Fail to add goods")
                .build().getVal();
    }
}
