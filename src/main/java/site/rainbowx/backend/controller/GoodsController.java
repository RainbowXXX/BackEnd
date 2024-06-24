package site.rainbowx.backend.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import site.rainbowx.backend.entity.Goods;
import site.rainbowx.backend.service.GoodsService;


import java.util.List;

@RestController
public class GoodsController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private GoodsService goodsService;
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<Goods> getAllGoods() {
        return goodsService.getAllGoods();
    }
}
