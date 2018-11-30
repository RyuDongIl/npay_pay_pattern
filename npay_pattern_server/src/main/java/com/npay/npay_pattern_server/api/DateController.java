package com.npay.npay_pattern_server.api;

import com.npay.npay_pattern_server.model.DefaultRes;
import com.npay.npay_pattern_server.service.impl.DateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class DateController {

    private final DateServiceImpl dateService;

    public DateController(DateServiceImpl dateService) {
        this.dateService = dateService;
    }

    @GetMapping("/date/amount")
    public ResponseEntity getInfo(
            @RequestParam("store") final String store,
            @RequestParam("year") final Optional<Integer> year,
            @RequestParam("month") final Optional<Integer> month) {

        try {
            if(month.isPresent())
                return new ResponseEntity<>(dateService.getAmountByDay(store, year.get(), month.get()), HttpStatus.OK);
            if(year.isPresent())
                return new ResponseEntity<>(dateService.getAmountByMonth(store, year.get()), HttpStatus.OK);
            return new ResponseEntity<>(dateService.getAmountByYear(store), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/date/product")
    public ResponseEntity getInfo(
            @RequestParam("store") final String store,
            @RequestParam("year") final int year,
            @RequestParam("month") final Optional<Integer> month,
            @RequestParam("day") final Optional<Integer> day) {

        try {
            if(day.isPresent())
                return new ResponseEntity<>(dateService.getProductsByDay(store, year, month.get(), day.get()), HttpStatus.OK);
            if(month.isPresent())
                return new ResponseEntity<>(dateService.getProductsByMonth(store, year, month.get()), HttpStatus.OK);
            return new ResponseEntity<>(dateService.getProductsByYear(store, year), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(DefaultRes.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
