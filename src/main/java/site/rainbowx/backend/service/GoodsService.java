package site.rainbowx.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.rainbowx.backend.entity.Goods;
import site.rainbowx.backend.repository.GoodsRepository;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private GoodsRepository goodsRepository;

    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }
    public Goods saveGoods(Goods goods) {
        return goodsRepository.save(goods);
    }
    public void deleteGoods(Long id) {
        goodsRepository.deleteById(id);
    }

}
