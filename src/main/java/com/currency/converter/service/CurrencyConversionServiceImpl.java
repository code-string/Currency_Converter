package com.currency.converter.service;

import com.currency.converter.annotation.TOUpper;
import com.currency.converter.controller.CurrencyController;
import com.currency.converter.domain.CurrencyConversion;
import com.currency.converter.domain.CurrencyExchange;
import com.currency.converter.domain.Rate;
import com.currency.converter.repository.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService{
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionServiceImpl.class);

    @Autowired
    RateRepository repository;


    @Override
    public CurrencyConversion convertFromTo(@TOUpper String base, @TOUpper String code, Float amount) throws Exception {
        logger.info(String.format("rate conversion starting at %s ....",CurrencyConversionServiceImpl.class));
        Rate baseRate = new Rate(CurrencyExchange.BASE_CODE, 1.0f, new Date());
        Rate codeRate = new Rate(CurrencyExchange.BASE_CODE, 1.0f, new Date());

        if(!CurrencyExchange.BASE_CODE.equals(base)) {
            baseRate = repository.findByDateAndCode(new Date(), code).orElse(null);
            logger.info("base rate =++++++ gotten... not USD");
        }

        if(!CurrencyExchange.BASE_CODE.equals(code)) {
            codeRate = repository.findByDateAndCode(new Date(), code).orElse(null);
            logger.info("code rate =++++++ gotten in " + codeRate.getCode());
        }


        if(codeRate == null || baseRate == null)
            throw new Exception("Code or Base invalid");

        logger.info(String.format("rate conversion ending at %s ....",getClass().getName()));
        return new CurrencyConversion(base, code, amount, (codeRate.getRate()/ baseRate.getRate()) * amount);
    }

    @Override
    public Rate[] calculateByCode(@TOUpper String code, Date date) throws Exception {
        List<Rate> rates = repository.findByDate(date);
        if(code.equals(CurrencyExchange.BASE_CODE))
            return rates.toArray(new Rate[0]);

        Rate baseRate = rates.stream().filter(rate -> rate.getCode().equals(code)).findFirst().orElse(null);
        if(baseRate == null)
            throw new Exception("Invalid code!");

        return Stream.concat(rates.stream().filter(n -> !n.getCode().equals(code))
                .map(n -> new Rate(n.getCode(),
                        n.getRate()/baseRate.getRate(), date)),
                Stream.of(new Rate(CurrencyExchange.BASE_CODE, 1/baseRate.getRate(),date))).toArray(Rate[]::new);

    }

    @Override
    public void saveRates(Rate[] rates, Date date) {
        Arrays.stream(rates).forEach(rate -> repository.save(new Rate(rate.getCode(), rate.getRate(), date)));
    }
}
