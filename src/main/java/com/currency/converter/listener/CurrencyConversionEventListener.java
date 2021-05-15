package com.currency.converter.listener;

import com.currency.converter.event.CurrencyConversionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CurrencyConversionEventListener implements ApplicationListener<CurrencyConversionEvent> {

    private static final String PLUS_LINE = "++++++++++++++++++++++++++++++++++++";
    private static final String NEXT_LINE = "\n";


    @Override
    public void onApplicationEvent(CurrencyConversionEvent currencyConversionEvent) {
        Object obj = currencyConversionEvent.getSource();
        StringBuilder str = new StringBuilder(NEXT_LINE);
        str.append(PLUS_LINE);
        str.append(NEXT_LINE);
        str.append(" Class: ").append(obj.getClass().getSimpleName());
        str.append(NEXT_LINE);
        str.append("  Value: ").append(currencyConversionEvent.getConversion());
        str.append(NEXT_LINE);
        str.append("Message: ").append(currencyConversionEvent.getMessage());
        str.append(PLUS_LINE);
        log.error(str.toString());
    }
}
