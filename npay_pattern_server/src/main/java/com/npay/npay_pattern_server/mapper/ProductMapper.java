package com.npay.npay_pattern_server.mapper;

import com.npay.npay_pattern_server.dto.Advertisement;
import com.npay.npay_pattern_server.dto.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    @Insert("INSERT INTO product(name, type) VALUES(#{name}, #{type})")
    int saveProduct(@Param("name") final String name, @Param("type") final String type);

    @Select("SELECT * FROM product WHERE name = #{name} AND type = #{type}")
    List<Product> checkExist(@Param("name") final String name, @Param("type") final String type);
}
