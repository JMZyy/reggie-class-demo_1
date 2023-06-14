package edu.czjt.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.czjt.reggie.entity.DishFlavor;
import edu.czjt.reggie.mapper.DishFlavorMapper;
import edu.czjt.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jinkun.tian on 2023/4/27
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
    @Override
    public List<DishFlavor> getFlavorsByDishId(Long dishId) {
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
        return this.list(dishFlavorLambdaQueryWrapper);
    }

    @Override
    public boolean removeByDishId(Long dishId) {
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
        return this.remove(dishFlavorLambdaQueryWrapper);
    }
}
