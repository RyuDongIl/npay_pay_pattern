package com.npay.npay_pattern_server.service;

import com.npay.npay_pattern_server.model.DefaultRes;

public interface ProductService {
    DefaultRes addProduct(final String name, final String type);
}
