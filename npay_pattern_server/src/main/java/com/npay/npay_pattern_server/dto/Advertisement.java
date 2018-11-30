package com.npay.npay_pattern_server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Advertisement {
    private String adId;
    private Date createTime;
    private Date updateTime;
}
