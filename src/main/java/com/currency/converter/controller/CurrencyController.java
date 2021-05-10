package com.currency.converter.controller;

import com.currency.converter.domain.CurrencyConversion;
import com.currency.converter.domain.CurrencyExchange;
import com.currency.converter.domain.Rate;
import com.currency.converter.service.CurrencyConversionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    CurrencyConversionServiceImpl service;

    @GetMapping("/latest")
    public ResponseEntity<CurrencyExchange> getLatest(@RequestParam(name="base",defaultValue = CurrencyExchange.BASE_CODE)String base) throws Exception {
        return new ResponseEntity<>(new CurrencyExchange(base, new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                service.calculateByCode(base, new Date())), HttpStatus.OK);
    }

    @GetMapping("/{date}")
    public ResponseEntity<CurrencyExchange> getByDate(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date, @RequestParam(name="base",defaultValue=CurrencyExchange.BASE_CODE)String base) throws Exception {
        return new ResponseEntity<>(new CurrencyExchange(base, new SimpleDateFormat("yyyy-MM-dd").format(date),
                service.calculateByCode(base, date)), HttpStatus.OK);
    }

    @GetMapping("/convert/{amount}/{base}/to/{code}")
    public ResponseEntity<CurrencyConversion> conversion(@PathVariable("amount") Float amount,@PathVariable("base")String base, @PathVariable("code")String code) throws Exception {
        CurrencyConversion conversionResult = service.convertFromTo(base, code, amount);
        return new ResponseEntity<>(conversionResult, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<CurrencyExchange> addNewRates(@RequestBody CurrencyExchange currencyExchange) throws Exception {
        try{
            final Date date = new SimpleDateFormat("yyyy-MM-dd").parse(currencyExchange.getDate());
            final Rate[] rates = currencyExchange.getRates();
            service.saveRates(rates, date);
        }catch(Exception ex){
            logger.error(ex.getMessage());
            throw ex;
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
