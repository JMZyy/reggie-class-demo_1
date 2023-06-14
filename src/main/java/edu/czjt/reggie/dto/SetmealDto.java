package edu.czjt.reggie.dto;


import edu.czjt.reggie.entity.Setmeal;
import edu.czjt.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
