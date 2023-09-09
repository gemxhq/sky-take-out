package com.sky.service;

import java.util.List;

public interface SetmealService {

    /**
     * 通过list 菜品id获取对应的套餐ids
     * @param ids
     * @return
     */
    List<Long> getSetamealIdsByDishIds(List<Long> ids);
}
