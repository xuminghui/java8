package com.dingdang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.dingdang.domain.User;

@Mapper
public interface UserDao {

    //根据age查找info
    List<User> get(@Param("age") int age,@Param("name") String name);
    List<User> getMap(Map<String,Object> map);

}