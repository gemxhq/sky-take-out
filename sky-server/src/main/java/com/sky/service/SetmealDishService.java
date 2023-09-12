package com.sky.service;

import com.sky.annotation.AutoFill;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SetmealDishService {

    /**
     * 批量添加套餐菜品关联关系
     * @param setmealDishes
     */
    @AutoFill(OperationType.INSERT)
    void insertBatch(List<SetmealDish> setmealDishes);
}
