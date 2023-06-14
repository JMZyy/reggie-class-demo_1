package edu.czjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.czjt.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by jinkun.tian on 2023/3/16
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
