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
public class AmountSumUp {
    private int id;
    private Date paymentDate;
    private String storeName;
    private String productName;
    private String status;
    private int isAd;
    private int amountSum;
    private int amountMin;
    private int amountMax;
    private int amountCnt;
    private Date createTime;
    private Date updateTime;
}
