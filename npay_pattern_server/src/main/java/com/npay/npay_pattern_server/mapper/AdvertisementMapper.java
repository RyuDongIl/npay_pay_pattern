package com.npay.npay_pattern_server.mapper;

import com.npay.npay_pattern_server.dto.Advertisement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdvertisementMapper {
    @Insert("INSERT INTO advertisement(ad_id) VALUES(#{ad_id})")
    int saveAd(@Param("ad_id") final String ad_id);

    @Select("SELECT * FROM advertisement WHERE ad_id = #{ad_id}")
    List<Advertisement> checkExist(@Param("ad_id") final String ad_id);
}
