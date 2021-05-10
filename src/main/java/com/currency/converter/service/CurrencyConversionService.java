package com.currency.converter.service;

import com.currency.converter.annotation.TOUpper;
import com.currency.converter.domain.CurrencyConversion;
import com.currency.converter.domain.Rate;

import java.util.Date;

public interface CurrencyConversionService {
    CurrencyConversion convertFromTo(@TOUpper String base, @TOUpper String code, Float amount) throws Exception;
    Rate[] calculateByCode(@TOUpper String code, Date date) throws Exception;
    void saveRates(Rate[] rates, Date date);
}
