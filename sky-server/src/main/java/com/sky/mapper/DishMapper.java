package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据id查询分类下菜品数量
     * @param id
     * @return
     */
    @Select("select count(id) from dish where category_id=#{id}")
    Integer countByCategoryId(Long id);

    /**
     * 新增菜品
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 菜品分页查询
     */
    Page pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 删除菜品
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 通过id获取菜品
     * @param id
     */
    Dish getById(Long id);


    void update(Dish dish);

    Dish getVOById(Long id);

    void statusChange(Long id, Integer status);

    List<Dish> list(Dish dish);
}
