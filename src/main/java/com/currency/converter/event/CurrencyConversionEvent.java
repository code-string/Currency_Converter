package com.currency.converter.event;

import com.currency.converter.domain.CurrencyConversion;
import org.springframework.context.ApplicationEvent;

public class CurrencyConversionEvent extends ApplicationEvent {

    private static final long serialVersionUID = -448149396335055188L;
    private CurrencyConversion conversion;
    private String message;


    public CurrencyConversionEvent(Object source, CurrencyConversion conversion) {
        super(source);
        this.conversion = conversion;
    }

    public CurrencyConversionEvent(Object source, CurrencyConversion conversion, String message) {
        super(source);
        this.conversion = conversion;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public CurrencyConversion getConversion() {
        return conversion;
    }
}
