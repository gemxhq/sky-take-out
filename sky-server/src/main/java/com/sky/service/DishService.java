package com.sky.service;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import io.swagger.models.auth.In;

import java.util.List;

public interface DishService {

    void insert(DishDTO dishDTO);

    /**
     * 菜品分页查询
     */
    Page pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     * @param ids
     */
    void delete(List<Long> ids);

    Dish getById(Long id);

    void update(DishDTO dishDTO);

    /**
     * 获取dishVO
     * @param id
     */
    DishVO getVOById(Long id);

    /**
     * 更改菜品状态
     * @param id
     * @param status
     */
    void statusChange(Long id, Integer status);
}
