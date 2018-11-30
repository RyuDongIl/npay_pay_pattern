package com.npay.npay_pattern_server.utils;

import com.npay.npay_pattern_server.dto.AmountSumUp;
import com.npay.npay_pattern_server.dto.PayInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BatchData {
    public static List<AmountSumUp> rollUp(List<PayInfo> payInfoList) throws ParseException {
        List<AmountSumUp> amountSumUpList = new ArrayList<>();
        HashMap<List<String>, List<Integer>> amountHashMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        for(PayInfo payInfo : payInfoList) {
            List<String> columns = new ArrayList<>();
            List<Integer> amounts = new ArrayList<>();

            cal.setTime(sdf.parse(payInfo.getDate()));

            columns.add(String.valueOf(cal.get(Calendar.YEAR)));
            columns.add(String.valueOf(cal.get(Calendar.MONTH)));
            columns.add(String.valueOf(cal.get(Calendar.DATE)));
            columns.add(payInfo.getMerchantName());
            columns.add(payInfo.getProductName());
            columns.add(payInfo.getPaymentState());
            if(payInfo.getAdId().equals("")) columns.add("0");
            else columns.add("1");

            if(amountHashMap.containsKey(columns)) {
                 amountHashMap.replace(columns, updateAmounts(amountHashMap.get(columns), payInfo));
            } else {
                amounts.add(payInfo.getPaymentAmount());
                amounts.add(payInfo.getPaymentAmount());
                amounts.add(payInfo.getPaymentAmount());
                amounts.add(1);
                amountHashMap.put(columns, amounts);
            }
        }

        Set<Map.Entry<List<String>, List<Integer>>> entrySet = amountHashMap.entrySet();
        Iterator it = entrySet.iterator();
        while(it.hasNext()){
            AmountSumUp amountSumUp = null;

            Map.Entry<List<String>, List<Integer>> entry = (Map.Entry<List<String>, List<Integer>>) it.next();
            List<String> columns = entry.getKey();
            List<Integer> amounts = entry.getValue();
            cal.set(Integer.parseInt(columns.get(0)), Integer.parseInt(columns.get(1)), Integer.parseInt(columns.get(2)));

            amountSumUp =  amountSumUp.builder()
                    .paymentDate(cal.getTime())
                    .storeName(columns.get(3))
                    .productName(columns.get(4))
                    .status(columns.get(5))
                    .isAd(Integer.parseInt(columns.get(6)))
                    .amountSum(amounts.get(0))
                    .amountMin(amounts.get(1))
                    .amountMax(amounts.get(2))
                    .amountCnt(amounts.get(3))
                    .build();

            amountSumUpList.add(amountSumUp);
        }
        return amountSumUpList;
    }

    private static List<Integer> updateAmounts(List<Integer> amounts, PayInfo payInfo) {
        int amount = payInfo.getPaymentAmount();

        amounts.set(0, amounts.get(0) + amount);
        if (amounts.get(1) > amount)
            amounts.set(1, amount);
        if (amounts.get(2) < amount)
            amounts.set(2, amount);
        amounts.set(3, amounts.get(3) + 1);

        return amounts;
    }
}
