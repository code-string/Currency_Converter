package com.currency.converter.service;

import com.currency.converter.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MetricServiceImpl implements MetricService{

    @Autowired
    private MetricRepository repository;



    public void increment(){}

}
