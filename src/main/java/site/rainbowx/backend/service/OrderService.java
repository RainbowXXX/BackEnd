package site.rainbowx.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.rainbowx.backend.entity.Goods;
import site.rainbowx.backend.entity.OrderGoods;
import site.rainbowx.backend.entity.Orders;
import site.rainbowx.backend.entity.User;
import site.rainbowx.backend.repository.OrderGoodsRepository;
import site.rainbowx.backend.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGoodsRepository orderGoodsRepository;

    public Orders saveOrder(Orders order) {
        return orderRepository.save(order);
    }

    public Orders getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Orders> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public void addOrderGoods(Orders order, Goods goods, int quantity) {
        OrderGoods orderGoods = new OrderGoods(order, goods, quantity);
        orderGoodsRepository.save(orderGoods);
    }
}
