package com.sky.service;

import com.sky.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService{
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public List<Long> getSetamealIdsByDishIds(List<Long> ids) {
        return setmealMapper.getSetmealIdsByDishIds(ids);
    }
}
