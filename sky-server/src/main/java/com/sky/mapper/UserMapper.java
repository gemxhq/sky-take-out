package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 获取用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid=#{openid}")
    User getUserByOpenid(String openid);

    void insert(User user);
}