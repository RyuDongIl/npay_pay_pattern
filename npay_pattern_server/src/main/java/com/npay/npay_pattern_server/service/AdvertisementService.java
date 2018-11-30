package com.npay.npay_pattern_server.service;

import com.npay.npay_pattern_server.dto.Advertisement;
import com.npay.npay_pattern_server.dto.Product;
import com.npay.npay_pattern_server.model.DefaultRes;

public interface AdvertisementService {
    DefaultRes addAd(final String ad_id);
}
