package edu.czjt.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.czjt.reggie.entity.Employee;
import edu.czjt.reggie.mapper.EmployeeMapper;
import edu.czjt.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * Created by jinkun.tian on 2023/3/16
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
