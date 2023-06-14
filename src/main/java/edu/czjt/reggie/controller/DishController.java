package edu.czjt.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.czjt.reggie.common.R;
import edu.czjt.reggie.dto.DishDto;
import edu.czjt.reggie.entity.Category;
import edu.czjt.reggie.entity.Dish;
import edu.czjt.reggie.entity.DishFlavor;
import edu.czjt.reggie.service.CategoryService;
import edu.czjt.reggie.service.DishFlavorService;
import edu.czjt.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jinkun.tian on 2023/4/27
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        // 根据分类查询菜品，并且为起售的菜品
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus, 1);
        List<Dish> dishes = dishService.list(queryWrapper);

        // 遍历dishs，创建List<DishDto>
        List<DishDto> dishDtos = dishes.stream().map((item) -> {
            // 创建DishDto
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            // 补充CategoryName
            Category category = categoryService.getById(item.getCategoryId());
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }

            // 补充List<DishFlavor>
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> flavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(flavors);

            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtos);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        log.debug("通过ID：{} 获取菜品", id);
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        // 构建分页构造器对象
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>(page, pageSize);

        // 条件构造器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(name != null, Dish::getName, name);
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        // 分页查询
        dishService.page(dishPage, dishLambdaQueryWrapper);

        // 将分页信息拷贝到dishDtoPage
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

        // 将dishPage的record转为dishDtoPage的record
        List<Dish> records = dishPage.getRecords();

        List<DishDto> dishDtoList = records.stream().map((item) -> {
            return dish2dishDto(item);
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoList);

        return R.success(dishDtoPage);
    }

    /**
     * 将dish转化为dishDto
     *
     * @param dish
     * @return
     */
    private DishDto dish2dishDto(Dish dish) {
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        Category category = categoryService.getById(dish.getCategoryId());

        if (category != null) {
            dishDto.setCategoryName(category.getName());
        }

        List<DishFlavor> dishFlavors = dishFlavorService.getFlavorsByDishId(dish.getId());

        dishDto.setFlavors(dishFlavors);

        return dishDto;
    }

    @PostMapping()
    public R<String> save(@RequestBody DishDto dishDto) {
        log.debug("保存dish: {}", dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }

    @PutMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status, @RequestBody String ids) {
        log.debug("修改菜品ids:{} 的状态为：{}。", ids, status);

        List<Long> idList = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        UpdateWrapper<Dish> dishUpdateWrapper = new UpdateWrapper<>();
        dishUpdateWrapper.in("id", idList).set("status", status);

        dishService.update(dishUpdateWrapper);
        return R.success("更新成功");
    }

    @PutMapping()
    @Transactional
    public R<String> update(@RequestBody DishDto dishDto) {
        log.debug("更新dishDto:{}", dishDto.toString());

        dishService.updateById(dishDto);

        // 按照dishid删除dishflavor
        dishFlavorService.removeByDishId(dishDto.getId());

        List<DishFlavor> dishFlavors = dishDto.getFlavors().stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(dishFlavors);

        // dishFlavorService.updateBatchById(dishFlavors);

        return R.success("更新成功");
    }

    @DeleteMapping()
    @Transactional
    public R<String> delete(@RequestParam("ids") String ids) {
        log.info("删除菜名ids：{}", ids);
        List<Long> idList = Arrays.stream(ids.split(","))
                .map(item->{
                    Long id = Long.valueOf(item);
                    dishFlavorService.removeByDishId(id);
                    dishService.removeById(id);
                    return id;
                })
                .collect(Collectors.toList());
        return R.success("删除菜品成功。");
    }

}
