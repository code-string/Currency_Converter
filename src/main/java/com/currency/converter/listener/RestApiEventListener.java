package com.currency.converter.listener;

import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class RestApiEventListener implements ApplicationListener<ApplicationEvent> {
    private static final String LATEST_URL = "/currency/latest";

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ServletRequestHandledEvent){

        }
    }
}
