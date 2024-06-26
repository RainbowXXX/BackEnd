package site.rainbowx.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.rainbowx.backend.entity.OrderGoods;
import site.rainbowx.backend.entity.Orders;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.service.OrderService;
import site.rainbowx.backend.service.UserService;
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
    public List<Orders> getAllOrders(@RequestParam String token) {
        String username = TokenUtils.getUserName(token);
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ArrayList<>();
        }
        return orderService.getOrdersByUser(user);


    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public boolean addOrder(@RequestBody AddArgs addArgs) {
        Orders orders = new Orders();
        String username = TokenUtils.validateToken(addArgs.token);
        orders.user = userService.getUserByUsername(username);
        orders.orderGoods.addAll(addArgs.orderGoods);
        return true;
    }
}
