package com.currency.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MetricServiceImpl implements MetricService{

    @Autowired
    private ApplicationEventPublisher publisher;


//    public Map<String, Object>
    public void increment(){}

}
