package com.npay.npay_pattern_server.mapper;

import com.npay.npay_pattern_server.dto.PayInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PayInfoMapper {

    @Select("SELECT id, payment_time, store, product, status, info, ad_id, create_time, update_time " +
            "FROM pay_info")
    List<PayInfo> findAll();

    @Select("SELECT id, payment_time, store, product, status, info, ad_id, create_time, update_time " +
            "FROM pay_info WHERE id = #{id}")
    PayInfo findById(@Param("id") final int id);

    @Insert("INSERT INTO pay_info(date, pay_id, user_id, user_birth, user_sex, merchant_name, product_name, product_type," +
            "payment_state, payment_type, main_payment_type, payment_amount, main_payment_amount, point_amount, receipt_number, " +
            "installment_month, ad_id, saved_point) " +
            "VALUES(#{payInfo.date}, #{payInfo.payId}, #{payInfo.userId}, #{payInfo.userBirth}, #{payInfo.userSex}, " +
            "#{payInfo.merchantName}, #{payInfo.productName}, #{payInfo.productType}, #{payInfo.paymentState}, " +
            "#{payInfo.paymentType}, #{payInfo.mainPaymentType}, #{payInfo.paymentAmount}, #{payInfo.mainPaymentAmount}, " +
            "#{payInfo.pointAmount}, #{payInfo.receiptNumber}, #{payInfo.installmentMonth}, #{payInfo.adId}, #{payInfo.savedPoint})")
    @Options(useGeneratedKeys = true, keyColumn = "payInfo.id")
    int save(@Param("payInfo") final PayInfo payInfo);

}