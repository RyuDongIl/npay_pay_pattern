package com.npay.npay_pattern_server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private String name;
    private String type;
    private Date createTime;
    private Date updateTime;
}
