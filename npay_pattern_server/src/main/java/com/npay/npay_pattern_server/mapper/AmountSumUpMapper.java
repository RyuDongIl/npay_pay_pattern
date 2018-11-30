package com.npay.npay_pattern_server.mapper;

import com.npay.npay_pattern_server.dto.AmountSumUp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AmountSumUpMapper {
    @Insert("INSERT INTO amount_sum_up(payment_date, store_name, product_name, status, is_ad, amount_sum, amount_min, amount_max, amount_cnt) " +
            "VALUES(#{amountSumUp.paymentDate}, #{amountSumUp.storeName}, #{amountSumUp.productName}, #{amountSumUp.status}, #{amountSumUp.isAd}, " +
            "#{amountSumUp.amountSum}, #{amountSumUp.amountMin}, #{amountSumUp.amountMax}, #{amountSumUp.amountCnt})")
    @Options(useGeneratedKeys = true, keyColumn = "amountSumUp.id")
    int save(@Param("amountSumUp") final AmountSumUp amountSumUp);

    @Select("SELECT * FROM amount_sum_up WHERE payment_date = DATE(#{amountSumUp.paymentDate}) AND store_name = #{amountSumUp.storeName}" +
            "AND product_name = #{amountSumUp.productName} AND status = #{amountSumUp.status} AND is_ad = #{amountSumUp.isAd}")
    List<AmountSumUp> checkExist(@Param("amountSumUp") final AmountSumUp amountSumUp);

    @Update("UPDATE amount_sum_up SET amount_sum = #{amountSumUp.amountSum}, amount_min = #{amountSumUp.amountMin}, " +
            "amount_max = #{amountSumUp.amountMax}, amount_cnt = #{amountSumUp.amountCnt} WHERE id = #{amountSumUp.id} LIMIT 5")
    int update(@Param("amountSumUp") final AmountSumUp amountSumUp);
}
