package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

        /**
         * 查询符合添加进购物车条件的物品
         * @param shoppingCart
         * @return
         */
        List<ShoppingCart> list(ShoppingCart shoppingCart);

        @Update("update shopping_cart set number=#{number} where id=#{id}")
        void update(ShoppingCart shoppingCart);

        void insert(ShoppingCart shoppingCart);

        /**
         * 清空购物车
         * @param userId
         */
        @Delete("delete from shopping_cart where user_id=#{userId}")
        void deleteAll(Long userId);

        /**
         * 物品减少一份
         * @param shoppingCart
         */
        @Delete("delete from shopping_cart where id=#{id}")
        void deleteOne(ShoppingCart shoppingCart);
}
