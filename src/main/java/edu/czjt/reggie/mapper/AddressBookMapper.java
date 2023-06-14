package edu.czjt.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.czjt.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {

}
