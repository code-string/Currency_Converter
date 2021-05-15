package com.currency.converter.listener;

import com.currency.converter.annotation.Log;
import com.currency.converter.event.CurrencyEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class RateEventListener {

    @TransactionalEventListener
    @Log(printParamsValues = true, callMethodWithNoParamsToString = "getRate")
    public void processEvent(CurrencyEvent event){}
}
