package com.currency.converter.mapper;

import com.currency.converter.domain.ExchangeRateApiResponse;
import com.currency.converter.domain.Rate;
import org.modelmapper.AbstractConverter;

public class RateModelConverter extends AbstractConverter<ExchangeRateApiResponse, Rate> {

    @Override
    protected Rate convert(ExchangeRateApiResponse exchangeRateApiResponse) {

        return null;
    }
}
