package com.currency.converter.aop;

import com.currency.converter.annotation.TOUpper;
import com.currency.converter.controller.CurrencyController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.stream.IntStream;

@Aspect
@Component
public class CurrencyCodeAudit {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyCodeAudit.class);

    @Pointcut("execution(* com.currency.converter.service.*Service.*(.., @com.currency.converter.annotation.TOUpper (*),..))")
    public void methodPointcut(){}

    @Around("methodPointcut()")
    public Object codeAudit(ProceedingJoinPoint pjp) throws Throwable{
        logger.info(String.format("string conversion starting by %s class...",getClass().getName()));
        Object[] args = pjp.getArgs();
        Parameter[] parameters = ((MethodSignature)pjp.getSignature()).getMethod().getParameters();

        logger.info(String.format("string conversion ending by %s class...",getClass().getName()));

        return pjp.proceed(IntStream.range(0, args.length)
                .mapToObj(index -> (parameters[index].isAnnotationPresent(TOUpper.class))
                        ? (new String(args[index].toString().toUpperCase()))
                        : args[index]).toArray());
    }
}
