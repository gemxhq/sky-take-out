package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.DishService;
import com.sky.utils.AliOssUtil;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    @Transactional
    public void insert(DishDTO dishDTO) {
        // dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(StatusConstant.ENABLE);

        dishMapper.insert(dish);

        Long id = dish.getId();

        // flavor
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(flavor -> {
            flavor.setDishId(id);
        });
        dishFlavorMapper.insertBatch(flavors);
    }

    /**
     * 菜品分页查询
     */
    @Override
    public Page pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.pageQuery(dishPageQueryDTO);

        return page;
    }

    /**
     * 删除菜品
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        // 菜品是否还在售卖，则不删除
        for (Long id : ids) {
            Dish dish = getById(id);
            // 查询菜品状态
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException("菜品仍在售卖，不允许删除");
            }
        }

        // 菜品关联套餐，则不删除
        List<Long> setmealIdsByDishIds = setmealMapper.getSetmealIdsByDishIds(ids);
        if (setmealIdsByDishIds != null && setmealIdsByDishIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        // 菜品关联口味一起删除
        dishMapper.delete(ids);
        dishFlavorMapper.deleteBatch(ids);

    }

    /**
     * 通过id获取菜品
     * @param id
     * @return
     */
    public Dish getById(Long id) {
        return dishMapper.getById(id);
    }

    /**
     * 编辑菜品
     * @param dishDTO
     */
    @Override
    @Transactional
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 菜品更新
        dishMapper.update(dish);
        Long id = dish.getId();

        // 风味更新
        // 先删除原有数据 再重新插入
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 原本不带菜品id 要更改
        if (flavors != null && flavors.size() > 0) {

            flavors.forEach(flavor -> {
                flavor.setDishId(id);
            });
            ArrayList<Long> list = new ArrayList<>();
            list.add(id);

            dishFlavorMapper.deleteBatch(list);
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 查询菜品及口味
     * @param id
     * @return
     */
    @Override
    @Transactional
    public DishVO getVOById(Long id) {
        // 获得菜品
        Dish dish = dishMapper.getById(id);
        // 通过菜品id获得对应的flavors
        List<DishFlavor> flavor = dishFlavorMapper.getFlavor(id);

        //菜品封装成VO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavor);

        return dishVO;
    }

    @Override
    public void statusChange(Long id, Integer status) {
        // todo 菜品包含在套餐中，不允许改为0
        dishMapper.statusChange(id, status);
    }

    /**
     * 根据分类id查询对应的菜品集合
     * @param categoryId
     * @return
     */
    @Override
    @Transactional
    public List<DishVO> listWithFlavor(Long categoryId) {
        // 获取菜品集合
        Dish dish = new Dish(); // 构造查询参数
        dish.setCategoryId(categoryId);
        dish.setStatus(StatusConstant.ENABLE);

        List<Dish> dishList = dishMapper.list(dish);

        // 遍历菜品，获取口味并绑定，返回VO
        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish tmp : dishList) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(tmp, dishVO);

            List<DishFlavor> flavor = dishFlavorMapper.getFlavor(tmp.getId());
            dishVO.setFlavors(flavor);
            // 一定不要忘记
            dishVOList.add(dishVO);
        }

        return dishVOList;
    }

    /**
     * 查询符合dish条件的所有菜品
     * @param dish 根据里面set非空的条件进行查询
     * @return
     */
    @Override
    public List<Dish> list(Dish dish) {
        List<Dish> list = dishMapper.list(dish);
        return list;
    }
}
