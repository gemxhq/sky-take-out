package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@Slf4j
@RequestMapping("/user/shop")
@Api(tags = "用户端店铺接口")
public class ShopController {

    public static String KEY = "SHOP_STATUS";

    @Autowired
    private ShopService shopService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询店铺营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("查询店铺营业状态")
    public Result<Integer> getStatus() {
        int status = shopService.getStatus();
        log.info("查看店铺营业状态为：{}", status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }

}
