package com.npay.npay_pattern_server.mapper;

import com.npay.npay_pattern_server.dto.AmountSumUp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DateMapper {

    @Select("SELECT * FROM amount_sum_up WHERE store_name = #{store} ORDER BY payment_date")
    List<AmountSumUp> getByYear(@Param("store") final String store);

    @Select("SELECT * FROM amount_sum_up WHERE store_name = #{store} AND year(payment_date) = #{year} " +
            "ORDER BY payment_date")
    List<AmountSumUp> getByMonth(@Param("store") final String store, @Param("year") final int year);

    @Select("SELECT * FROM amount_sum_up WHERE store_name = #{store} AND year(payment_date) = #{year} " +
            "AND month(payment_date) = #{month} ORDER BY payment_date")
    List<AmountSumUp> getByDay(@Param("store") final String store, @Param("year") final int year, @Param("month") final int month);

    @Select("SELECT * FROM amount_sum_up WHERE store_name = #{store} AND year(payment_date) = #{year} ORDER BY product_name")
    List<AmountSumUp> getProductByYear(@Param("store") final String store, @Param("year") final int year);

    @Select("SELECT * FROM amount_sum_up WHERE store_name = #{store} AND year(payment_date) = #{year} " +
            "AND month(payment_date) = #{month} ORDER BY product_name")
    List<AmountSumUp> getProductByMonth(@Param("store") final String store, @Param("year") final int year, @Param("month") final int month);

    @Select("SELECT * FROM amount_sum_up WHERE store_name = #{store} AND year(payment_date) = #{year} " +
            "AND month(payment_date) = #{month} AND day(payment_date) = #{day} ORDER BY product_name")
    List<AmountSumUp> getProductByDay(@Param("store") final String store, @Param("year") final int year, @Param("month") final int month, @Param("day") final int day);

}
