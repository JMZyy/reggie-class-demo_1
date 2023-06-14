package edu.czjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.czjt.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User>{
}
