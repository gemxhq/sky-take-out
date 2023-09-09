package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id=#{id}")
    Integer countByCategoryId(Long id);

    /**
     * 通过list 菜品id获取对应的套餐ids
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);
}
