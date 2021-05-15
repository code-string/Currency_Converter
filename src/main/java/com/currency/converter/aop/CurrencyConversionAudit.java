package com.currency.converter.aop;

import com.currency.converter.event.CurrencyConversionEvent;
import com.currency.converter.exception.BadCodeRuntimeException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CurrencyConversionAudit {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Pointcut("execution(* com.currency.converter.service.*Service.*(..))")
    public void exceptionPointCut(){}

    @AfterThrowing(pointcut="exceptionPointCut()", throwing = "ex")
    public void badCodeException(JoinPoint jp, BadCodeRuntimeException ex){
        if(ex.getConversion() != null){
            publisher.publishEvent(new CurrencyConversionEvent(jp.getTarget(), ex.getConversion(), ex.getMessage()));
        }
    }
}
