package com.npay.npay_pattern_server.mapper;

import com.npay.npay_pattern_server.dto.Advertisement;
import com.npay.npay_pattern_server.dto.Store;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Insert("INSERT INTO store(name) VALUES(#{name})")
    int saveStore(@Param("name") final String name);

    @Select("SELECT * FROM store WHERE name = #{name}")
    List<Store> checkExist(@Param("name") final String name);

    @Select("SELECT name FROM store")
    List<String> getStoreList();
}
