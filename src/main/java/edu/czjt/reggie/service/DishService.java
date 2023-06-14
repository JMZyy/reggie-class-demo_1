package edu.czjt.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.czjt.reggie.dto.DishDto;
import edu.czjt.reggie.entity.Dish;

/**
 * Created by jinkun.tian on 2023/4/27
 */
public interface DishService extends IService<Dish> {
    public DishDto getByIdWithFlavor(Long id);

    public void saveWithFlavor(DishDto dishDto);
}
