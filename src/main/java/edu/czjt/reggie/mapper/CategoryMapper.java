package edu.czjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.czjt.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by jinkun.tian on 2023/4/27
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
