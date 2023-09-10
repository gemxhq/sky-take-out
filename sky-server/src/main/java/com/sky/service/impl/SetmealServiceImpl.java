package com.sky.service.impl;

import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public List<Long> getSetamealIdsByDishIds(List<Long> ids) {
        return setmealMapper.getSetmealIdsByDishIds(ids);
    }
}
