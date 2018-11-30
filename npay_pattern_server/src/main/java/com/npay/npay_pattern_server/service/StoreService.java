package com.npay.npay_pattern_server.service;

import com.npay.npay_pattern_server.model.DefaultRes;

public interface StoreService {
    DefaultRes addStore(final String name);
    DefaultRes getStoreList();
}
