package edu.czjt.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.czjt.reggie.entity.Category;
import edu.czjt.reggie.mapper.CategoryMapper;
import edu.czjt.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * Created by jinkun.tian on 2023/4/27
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
