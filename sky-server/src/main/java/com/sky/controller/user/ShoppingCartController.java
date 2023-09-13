package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shoppingCart")
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("添加到购物车")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success();
    }

    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list= shoppingCartService.list();
        return Result.success(list);
    }

    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result delete() {
        log.info("清空购物车");
        shoppingCartService.deleteAll();
        return Result.success();
    }

    @ApiOperation("减少商品数量")
    @PostMapping("/sub")
    public Result deleteOne(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("减少商品数量：{}", shoppingCartDTO);
        shoppingCartService.deleteOne(shoppingCartDTO);
        return Result.success();
    }
}
