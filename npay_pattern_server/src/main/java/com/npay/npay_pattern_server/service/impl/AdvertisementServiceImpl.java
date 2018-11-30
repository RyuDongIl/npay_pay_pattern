package com.npay.npay_pattern_server.service.impl;

import com.npay.npay_pattern_server.dto.Advertisement;
import com.npay.npay_pattern_server.mapper.AdvertisementMapper;
import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.AdvertisementService;
import com.npay.npay_pattern_server.utils.ResponseMessage;
import com.npay.npay_pattern_server.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementMapper advertisementMapper;

    public AdvertisementServiceImpl(AdvertisementMapper advertisementMapper) {
        this.advertisementMapper = advertisementMapper;
    }

    @Override
    @Transactional
    public DefaultRes addAd(String ad_id) {
        if(!advertisementMapper.checkExist(ad_id).isEmpty()) return null;
        try {
            advertisementMapper.saveAd(ad_id);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_PAYINFO);
        } catch (Exception e) {
            // Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
