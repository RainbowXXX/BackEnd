package site.rainbowx.backend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.entity.Orders;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.service.GoodsService;
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

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Orders> getAllOrders(@RequestParam String token) {
        String username = TokenUtils.GetUserName(token);
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return new ArrayList<>();
        }
        return orderService.getOrdersByUser(user);


    }
}
