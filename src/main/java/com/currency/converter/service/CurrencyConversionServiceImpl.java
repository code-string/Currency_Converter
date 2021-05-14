package com.currency.converter.service;

import com.currency.converter.annotation.TOUpper;
import com.currency.converter.domain.*;
import com.currency.converter.repository.RateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CurrencyConversionServiceImpl implements CurrencyConversionService{
    private static final Logger logger = LoggerFactory.getLogger(CurrencyConversionServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    private static final String rating_url = "https://api.exchangerate.host/latest/?base=USD";

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

    @Async
    @Scheduled(fixedRateString = "${schedule.rate}")
    protected void fetchRates() throws JsonProcessingException {
        String jsonResponse = restTemplate.getForObject(rating_url, String.class);
        log.info(jsonResponse.concat("   JsonResponse"));
        ObjectMapper mapper = new ObjectMapper();
        List<RateDto> rates = new ArrayList<>();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        ExchangeRateApiResponse rateApiResponse = mapper.readValue(jsonResponse, ExchangeRateApiResponse.class);

        log.info(rateApiResponse.toString().concat("   RateApiResponse"));
        extractRateData(rates, rateApiResponse);

        saveRateObjects(rates);
    }

    private void saveRateObjects(List<RateDto> rates) {
        List<Rate> rateList = rates.stream()
                .map(rate -> modelMapper.map(rate, Rate.class))
                .collect(Collectors.toList());

        repository.saveAll(rateList);
    }

    private void extractRateData(List<RateDto> rates, ExchangeRateApiResponse rateApiResponse) {
        rateApiResponse.getRates().keySet().forEach(key -> rates.add(new RateDto(key, rateApiResponse.getDate())));
        rateApiResponse.getRates().forEach((rateKey, rate) -> rates.stream().sorted()
                .filter(ratedto -> ratedto.getCode().equals(rateKey))
                .forEach(rateObj -> rateObj.setRate(rate)));
    }
}
