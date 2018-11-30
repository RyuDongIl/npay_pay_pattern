package com.npay.npay_pattern_server.service.impl;

import com.npay.npay_pattern_server.dto.Product;
import com.npay.npay_pattern_server.mapper.ProductMapper;
import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.ProductService;
import com.npay.npay_pattern_server.utils.ResponseMessage;
import com.npay.npay_pattern_server.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    @Transactional
    public DefaultRes addProduct(String name, String type) {
        if (!productMapper.checkExist(name, type).isEmpty()) return null;
        try {
            productMapper.saveProduct(name, type);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_PAYINFO);
        } catch (Exception e) {
            // Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
