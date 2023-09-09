package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味
     * @param
     */
    void insertBatch(List<DishFlavor> list);

    /**
     * 获取菜品对应的口味
     * @param dish_id
     */
    List<DishFlavor> getFlavor(Long dish_id);

    /**
     * 口味批量删除
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
