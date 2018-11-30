package com.npay.npay_pattern_server.service.impl;

import com.npay.npay_pattern_server.dto.AmountSumUp;
import com.npay.npay_pattern_server.dto.PayInfo;
import com.npay.npay_pattern_server.mapper.AmountSumUpMapper;
import com.npay.npay_pattern_server.mapper.PayInfoMapper;
import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.PayInfoService;
import com.npay.npay_pattern_server.utils.BatchData;
import com.npay.npay_pattern_server.utils.ResponseMessage;
import com.npay.npay_pattern_server.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class PayInfoServiceImpl implements PayInfoService {

    private final PayInfoMapper payInfoMapper;
    private final AmountSumUpMapper amountSumUpMapper;
    private final AdvertisementServiceImpl advertisementService;
    private final ProductServiceImpl productService;
    private final StoreServiceImpl storeService;
    private static List<PayInfo> payInfoList = null;

    public PayInfoServiceImpl(PayInfoMapper payInfoMapper, AmountSumUpMapper amountSumUpMapper, AdvertisementServiceImpl advertisementService, ProductServiceImpl productService, StoreServiceImpl storeService) {
        this.payInfoMapper = payInfoMapper;
        this.amountSumUpMapper = amountSumUpMapper;
        this.advertisementService = advertisementService;
        this.productService = productService;
        this.storeService = storeService;
    }

    @Override
    @Transactional
    public DefaultRes addPayInfo(final PayInfo payInfo) {     // PayInfo raw하게 쌓는 테이블
        try {
            payInfoMapper.save(payInfo);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_PAYINFO);
        } catch (Exception e) {
            // Rollback
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error(e.getMessage());
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }

    }

    @Override
    @Transactional
    public DefaultRes batchPayInfoFromFile(final File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
        List<PayInfo> payInfoList = new ArrayList<>();
        List<AmountSumUp> amountSumUpList;

        String line;
        br.readLine();  // 첫 번째 라인 컬럼정보라 제외



        int cnt = 1;
        PayInfo payInfo = null;
        while ((line = br.readLine()) != null) {
            if (cnt % 10000 == 0) log.info("[" + String.valueOf(cnt) + "] " + line);
            String str[] = line.split(",");
            if(str[12].equals("")) str[12] = "0";
            if(str[13].equals("")) str[13] = "0";
            if(str[15].equals("")) str[15] = "0";
            if(str[17].equals("")) str[17] = "0";
            for (int i = 0; i < str.length; i++) {
                if(str[i].equals("") || str[i] == null) {
                    str[i] = "0";
                }
            }

            payInfo = payInfo.builder()
                    .date(str[0])
                    .payId(str[1])
                    .userId(str[3])
                    .userBirth(str[4])
                    .userSex(str[5])
                    .merchantName(str[2])
                    .productName(str[6])
                    .productType(str[7])
                    .paymentState(str[8])
                    .paymentType(str[9])
                    .mainPaymentType(str[10])
                    .paymentAmount(Integer.parseInt(str[11]))
                    .mainPaymentAmount(Integer.parseInt(str[12]))
                    .pointAmount(Integer.parseInt(str[13]))
                    .receiptNumber(str[14])
                    .installmentMonth(Integer.parseInt(str[15]))
                    .adId(str[16])
                    .savedPoint(Integer.parseInt(str[17]))
                    .build();

            addPayInfo(payInfo);
            cnt++;

            advertisementService.addAd(payInfo.getAdId());
            productService.addProduct(payInfo.getProductName(), payInfo.getProductType());

            addPayInfo(payInfo);
            payInfoList.add(payInfo);
        }
        amountSumUpList = BatchData.rollUp(payInfoList);

        for(AmountSumUp amountSumUp : amountSumUpList) {
            storeService.addStore(amountSumUp.getStoreName());
            List<AmountSumUp> amountSumUps = amountSumUpMapper.checkExist(amountSumUp);
            if(amountSumUps.isEmpty())
                try {
                    amountSumUpMapper.save(amountSumUp);
                } catch (Exception e) {
                    // Rollback
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error(e.getMessage());
                    return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
                }

            else {
                AmountSumUp newSumUp = amountSumUps.get(0);
                newSumUp.setAmountSum(newSumUp.getAmountSum() + amountSumUp.getAmountSum());
                newSumUp.setAmountCnt(newSumUp.getAmountCnt() + amountSumUp.getAmountCnt());
                newSumUp.setAmountMin(Math.min(newSumUp.getAmountMin(), amountSumUp.getAmountMin()));
                newSumUp.setAmountMax(Math.max(newSumUp.getAmountMax(), amountSumUp.getAmountMax()));

                try {
                    amountSumUpMapper.update(newSumUp);
                } catch (Exception e) {
                    // Rollback
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error(e.getMessage());
                    return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
                }
            }

        }
        return DefaultRes.res(StatusCode.CREATED, ResponseMessage.BATCH_PAYINFO);
    }

    @Override
    public DefaultRes getAllpayInfo() {
        final List<PayInfo> payInfoList = payInfoMapper.findAll();
        if (payInfoList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, payInfoList);
    }

    @Override
    public DefaultRes findById(int id) {
        final PayInfo payInfo = payInfoMapper.findById(id);
        if (payInfo == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, payInfo);
    }

    @Override
    public DefaultRes tempSave(PayInfo payInfo) {
        if(payInfoList == null) payInfoList = new ArrayList<>();
        payInfoList.add(payInfo);
        log.info(payInfo.toString());
        return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_PAYINFO);
    }

    @Scheduled(initialDelay = 36000, fixedDelay = 36000)
    public void bulkUpdate() throws ParseException {
        List<AmountSumUp> amountSumUpList;

        if (payInfoList == null) return;
        if (payInfoList.isEmpty()) return;

        amountSumUpList = BatchData.rollUp(payInfoList);

        for(AmountSumUp amountSumUp : amountSumUpList) {
            storeService.addStore(amountSumUp.getStoreName());
            List<AmountSumUp> amountSumUps = amountSumUpMapper.checkExist(amountSumUp);
            if(amountSumUps.isEmpty())
                try {
                    amountSumUpMapper.save(amountSumUp);
                } catch (Exception e) {
                    // Rollback
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error(e.getMessage());
                    return;
                }

            else {
                AmountSumUp newSumUp = amountSumUps.get(0);
                newSumUp.setAmountSum(newSumUp.getAmountSum() + amountSumUp.getAmountSum());
                newSumUp.setAmountCnt(newSumUp.getAmountCnt() + amountSumUp.getAmountCnt());
                newSumUp.setAmountMin(Math.min(newSumUp.getAmountMin(), amountSumUp.getAmountMin()));
                newSumUp.setAmountMax(Math.max(newSumUp.getAmountMax(), amountSumUp.getAmountMax()));

                try {
                    amountSumUpMapper.update(newSumUp);
                } catch (Exception e) {
                    // Rollback
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    log.error(e.getMessage());
                    return;
                }
            }

        }
        log.info(DefaultRes.res(StatusCode.CREATED, ResponseMessage.BATCH_PAYINFO).toString());
        payInfoList.clear();
    }

}