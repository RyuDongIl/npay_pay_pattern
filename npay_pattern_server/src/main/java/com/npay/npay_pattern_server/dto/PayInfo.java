package com.npay.npay_pattern_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayInfo {
    private String date;
    private String payId;
    private String userId;
    private String userBirth;
    private String userSex;
    private String merchantName;
    private String productName;
    private String productType;
    private String paymentState;
    private String paymentType;
    private String mainPaymentType;
    private int paymentAmount;
    private int mainPaymentAmount;
    private int pointAmount;
    private String receiptNumber;
    private int installmentMonth;
    private String adId;
    private int savedPoint;
}