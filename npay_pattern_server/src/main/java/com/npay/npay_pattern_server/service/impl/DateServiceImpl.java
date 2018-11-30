package com.npay.npay_pattern_server.service.impl;

import com.npay.npay_pattern_server.dto.AmountSumUp;
import com.npay.npay_pattern_server.dto.PayInfo;
import com.npay.npay_pattern_server.enumeration.PayInfoEnum;
import com.npay.npay_pattern_server.mapper.DateMapper;
import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.DateService;
import com.npay.npay_pattern_server.utils.Csv2Json;
import com.npay.npay_pattern_server.utils.ResponseMessage;
import com.npay.npay_pattern_server.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class DateServiceImpl implements DateService {

    private final DateMapper dateMapper;

    public DateServiceImpl(DateMapper dateMapper) {
        this.dateMapper = dateMapper;
    }

    private DefaultRes getProducts(List<AmountSumUp> amountSumUpList) {
        HashMap<String, Integer> productMap = new HashMap<>();

        if (amountSumUpList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);

        String pd = amountSumUpList.get(0).getProductName();
        int sum = 0;
        for (AmountSumUp amountSumUp : amountSumUpList) {
            if (!pd.equals(amountSumUp.getProductName())) {
                productMap.put(pd, sum);
                sum = 0;
                pd = amountSumUp.getProductName();
            }
            if (amountSumUp.getStatus().equals("결제")) sum += amountSumUp.getAmountSum();
            else sum -= amountSumUp.getAmountSum();
        }
        productMap.put(pd, sum);

        productMap = productMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));


        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, productMap);
    }

    @Override
    public DefaultRes getProductsByYear(String store, int year) {
        final List<AmountSumUp> amountSumUpList = dateMapper.getProductByYear(store, year);
        return getProducts(amountSumUpList);
    }

    @Override
    public DefaultRes getProductsByMonth(String store, int year, int month) {
        final List<AmountSumUp> amountSumUpList = dateMapper.getProductByMonth(store, year, month);
        return getProducts(amountSumUpList);
    }

    @Override
    public DefaultRes getProductsByDay(String store, int year, int month, int day) {
        final List<AmountSumUp> amountSumUpList = dateMapper.getProductByDay(store, year, month, day);
        return getProducts(amountSumUpList);
    }

    @Override
    public DefaultRes getAmountByYear(String store) {
        final List<AmountSumUp> amountSumUpList = dateMapper.getByYear(store);
        HashMap<Integer, Integer> amountMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        if (amountSumUpList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);


        cal.setTime(amountSumUpList.get(0).getPaymentDate());
        int year = cal.get(Calendar.YEAR);
        int sum = 0;
        for (AmountSumUp amountSumUp : amountSumUpList) {
            cal.setTime(amountSumUp.getPaymentDate());
            if (year != cal.get(Calendar.YEAR)) {
                amountMap.put(year, sum);
                sum = 0;
                year = cal.get(Calendar.YEAR);
            }
            if (amountSumUp.getStatus().equals("결제")) sum += amountSumUp.getAmountSum();
            else sum -= amountSumUp.getAmountSum();
        }
        amountMap.put(year, sum);

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, amountMap);
    }

    @Override
    public DefaultRes getAmountByMonth(String store, int year) {
        final List<AmountSumUp> amountSumUpList = dateMapper.getByMonth(store, year);
        HashMap<Integer, Integer> amountMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        if (amountSumUpList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);

        cal.setTime(amountSumUpList.get(0).getPaymentDate());
        int month = cal.get(Calendar.MONTH);
        int sum = 0;
        for (AmountSumUp amountSumUp : amountSumUpList) {
            cal.setTime(amountSumUp.getPaymentDate());
            if (month != cal.get(Calendar.MONTH)) {
                amountMap.put(month+1, sum);
                sum = 0;
                month = cal.get(Calendar.MONTH);
            }
            if (amountSumUp.getStatus().equals("결제")) sum += amountSumUp.getAmountSum();
            else sum -= amountSumUp.getAmountSum();
        }
        amountMap.put(month+1, sum);

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, amountMap);
    }

    @Override
    public DefaultRes getAmountByDay(String store, int year, int month) {
        final List<AmountSumUp> amountSumUpList = dateMapper.getByDay(store, year, month);
        HashMap<Integer, Integer> amountMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        if (amountSumUpList.isEmpty())
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_PAYINFO);

        cal.setTime(amountSumUpList.get(0).getPaymentDate());
        int day = cal.get(Calendar.DATE);
        int sum = 0;
        for (AmountSumUp amountSumUp : amountSumUpList) {
            cal.setTime(amountSumUp.getPaymentDate());
            if (day != cal.get(Calendar.DATE)) {
                amountMap.put(day, sum);
                sum = 0;
                day = cal.get(Calendar.DATE);
            }
            if (amountSumUp.getStatus().equals("결제")) sum += amountSumUp.getAmountSum();
            else sum -= amountSumUp.getAmountSum();
        }
        amountMap.put(day, sum);

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_PAYINFO, amountMap);
    }
}
