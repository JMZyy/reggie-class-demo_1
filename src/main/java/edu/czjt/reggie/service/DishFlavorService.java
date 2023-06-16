package edu.czjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.czjt.reggie.entity.DishFlavor;

import java.util.List;
public interface DishFlavorService extends IService<DishFlavor> {

    public List<DishFlavor> getFlavorsByDishId(Long dishId);

    boolean removeByDishId(Long id);
}
