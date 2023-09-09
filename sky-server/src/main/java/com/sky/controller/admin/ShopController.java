package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("adminShopController")
@Slf4j
@RequestMapping("/admin/shop")
@Api(tags = "管理端店铺接口")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 查询店铺营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("查询店铺营业状态")
    public Result<Integer> getStatus() {
        Integer status = shopService.getStatus();
        log.info("查看店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }

    /**
     * 设置店铺营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("店铺状态更改")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        shopService.setStatus(status);
        return Result.success();
    }
}
