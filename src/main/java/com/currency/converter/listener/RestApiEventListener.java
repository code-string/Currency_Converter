package com.currency.converter.listener;

import com.currency.converter.annotation.Log;
import com.currency.converter.service.MetricService;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

@Component
public class RestApiEventListener implements ApplicationListener<ApplicationEvent> {
//    private static final String LATEST_URL = "/currency/latest";

    @Autowired
    private MetricService metricService;


    @Log(printParamsValues = true)
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ServletRequestHandledEvent){
            ServletRequestHandledEvent event = ((ServletRequestHandledEvent) applicationEvent);
            metricService.increment(event.getRequestUrl(), event.getStatusCode(), event.getTimestamp());
        }
    }
}
