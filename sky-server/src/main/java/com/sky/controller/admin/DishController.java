package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/{id}")
    @ApiOperation("查询菜品")
    public Result<DishVO> getVOById(@PathVariable Long id) {
        log.info("查询菜品：{}", id);
        DishVO dishVO = dishService.getVOById(id);
        return Result.success(dishVO);
    }

    @PostMapping
    @ApiOperation("新增菜品")
    public Result insert(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品:{}", dishDTO);
        dishService.insert(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        Page page = dishService.pageQuery(dishPageQueryDTO);

        long total = page.getTotal();
        List result = page.getResult();
        PageResult pageResult = new PageResult(total, result);

        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam("ids") List<Long> ids) {
        log.info("菜品删除：{}", ids);
        dishService.delete(ids);
        return Result.success();
    }

    /**
     * 菜品起售、停售
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品上架状态")
    public Result status(@PathVariable Integer status, Long id) {
        log.info("菜品上架状态：{}-->{}", id, status);
        dishService.statusChange(id, status);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("编辑菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("编辑菜品：{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }




}
