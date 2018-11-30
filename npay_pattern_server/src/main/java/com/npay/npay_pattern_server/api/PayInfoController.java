package com.npay.npay_pattern_server.api;

import com.npay.npay_pattern_server.dto.PayInfo;
import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.StoreService;
import com.npay.npay_pattern_server.service.impl.PayInfoServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Optional;

@Slf4j
@RestController
public class PayInfoController {

    private final PayInfoServiceImpl payInfoService;
    private final StoreService storeService;

    public PayInfoController(PayInfoServiceImpl payInfoService, StoreService storeService) {
        this.payInfoService = payInfoService;
        this.storeService = storeService;
    }

    @PostMapping("/real_time")
    public ResponseEntity postPayInfo(@RequestBody final PayInfo payInfo) {
        try {
            return new ResponseEntity<>(payInfoService.tempSave(payInfo), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/batch_csv")
    public ResponseEntity register() {
        try {
            File csvFile = new File("/root/npay.csv");
            return new ResponseEntity<>(payInfoService.batchPayInfoFromFile(csvFile), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pay_info")
    public ResponseEntity getInfo(@RequestParam("id") final Optional<Integer> id) {
        try {
            //name이 null일 경우 false, null이 아닐 경우 true
            if (id.isPresent()) return new ResponseEntity<>(payInfoService.findById(id.get()), HttpStatus.OK);
            return new ResponseEntity<>(payInfoService.getAllpayInfo(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity getStore() {
        try {
            return new ResponseEntity<>(storeService.getStoreList(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

