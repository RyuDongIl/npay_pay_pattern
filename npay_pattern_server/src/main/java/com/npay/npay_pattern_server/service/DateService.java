package com.npay.npay_pattern_server.service;

import com.npay.npay_pattern_server.model.DefaultRes;

public interface DateService {
    DefaultRes getProductsByYear(final String store, final int year);
    DefaultRes getProductsByMonth(final String store, final int year, final int month);
    DefaultRes getProductsByDay(final String store, final int year, final int month, final int day);

    DefaultRes getAmountByYear(final String store);
    DefaultRes getAmountByMonth(final String store, final int year);
    DefaultRes getAmountByDay(final String store, final int year, final int month);
}
