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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Resource
    private RedisTemplate redisTemplate;

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
        // 清除redis缓存
        Long categoryId = dishDTO.getCategoryId();
        cleanCache("dish_" + categoryId);

        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
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

//        for (Long id : ids) {
//            cleanCache("dish_" + id);
//        }
        cleanCache("dish_*");

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

        cleanCache("dish_*");
        return Result.success();
    }

    @PutMapping
    @ApiOperation("编辑菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("编辑菜品：{}", dishDTO);
        dishService.update(dishDTO);

        cleanCache("*");
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("根据分类id查询所有菜品")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("查询分类{}下所有菜品：{}", categoryId);
        Dish dish = Dish.builder()
                        .categoryId(categoryId).build();

        List<Dish> dishList = dishService.list(dish);
        return Result.success(dishList);
    }

    /**
     * 清除redis缓存
     * @param pattern
     */
    public void cleanCache(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
