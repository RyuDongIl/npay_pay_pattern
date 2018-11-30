package com.npay.npay_pattern_server.service.impl;

import com.npay.npay_pattern_server.dto.Store;
import com.npay.npay_pattern_server.mapper.StoreMapper;
import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.StoreService;
import com.npay.npay_pattern_server.utils.ResponseMessage;
import com.npay.npay_pattern_server.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {

    private final StoreMapper storeMapper;

    public StoreServiceImpl(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
    }

    @Override
    @Transactional
    public DefaultRes addStore(String name) {
        if (!storeMapper.checkExist(name).isEmpty()) return null;
        try {
            storeMapper.saveStore(name);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_PAYINFO);
        } catch (Exception e) {
            // Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    @Override
    public DefaultRes getStoreList() {
        final List<String> storeList = storeMapper.getStoreList();
        if (storeList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, storeList);
    }
}
