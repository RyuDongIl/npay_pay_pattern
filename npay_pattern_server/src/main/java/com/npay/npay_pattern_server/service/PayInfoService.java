package com.npay.npay_pattern_server.service;

import com.npay.npay_pattern_server.dto.PayInfo;
import com.npay.npay_pattern_server.model.DefaultRes;

import java.io.File;
import java.util.List;

public interface PayInfoService {

    DefaultRes addPayInfo(final PayInfo payInfo) throws Exception;

    DefaultRes batchPayInfoFromFile(final File file) throws Exception;

    DefaultRes getAllpayInfo();
    DefaultRes findById(final int id);

    DefaultRes tempSave(final PayInfo payInfo);
}

