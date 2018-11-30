package com.npay.npay_pattern_server.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Store {
    private String name;
    private Date createTime;
    private Date updateTime;
}
