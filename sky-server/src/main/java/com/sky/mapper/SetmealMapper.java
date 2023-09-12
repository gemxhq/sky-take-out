package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
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

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);



    /**
     * 根据套餐id查询菜品选项
     * @param id
     * @return
     */
    @Select("select sd.name,sd.copies,d.image,d.description" +
            " from setmeal_dish sd left join dish d on sd.dish_id=d.id " +
            "where sd.setmeal_id=#{id}")
    List<DishItemVO> getDishItemBySetmealId(Long id);

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 通过ids批量获取套餐
     * @param ids
     * @return
     */
    List<Setmeal> getByIds(List<Long> ids);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 更新套餐
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

    /**
     * 获取套餐详细信息
     * @param id
     * @return
     */
    SetmealVO getSetmealItemById(Long id);

    /**
     * 查询套餐包含的所有菜品id
     * @param id
     * @return
     */
    List<Long> getDishIdsBySetmealId(Long id);
}
