package com.currency.converter.aop;

import com.currency.converter.annotation.Log;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CodeLogger {
    private static final String DASH_LINE = "============================================";
    private static final String NEXT_LINE = "\n";

    @Pointcut("execution(@com.currency.converter.annotation.Log * com.currency.converter..*.*(..)) && @annotation(codeLog)")
    public void codeLogger(Log codeLog){}

    @Before("codeLogger(codeLog)")
    public void doCodeLogger(JoinPoint jp, Log codeLog){
        StringBuilder str = new StringBuilder(NEXT_LINE);
        str.append(DASH_LINE);
        str.append(NEXT_LINE);
        str.append(" Class: ").append(jp.getTarget().getClass().getSimpleName());
        str.append(NEXT_LINE);
        str.append("Method: ").append(jp.getSignature().getName());
        str.append(NEXT_LINE);
        if (codeLog.printParamsValues()){
            Object[] args = jp.getArgs();
            str.append(NEXT_LINE);
            for(Object obj : args){
                str.append(" Param: ").append(obj.getClass().getSimpleName());
                str.append(NEXT_LINE);

                try{
                    String methodToCall = codeLog.callMethodWithNoParamsToString();

                    if("toString".equals(methodToCall))
                        str.append("Value: ").append(obj);
                    else
                        str.append(" Value: ").append(obj.getClass()
                                .getDeclaredMethod(methodToCall, new Class[]{})
                                .invoke(obj, new Object[]{}));

                }catch(Exception e){
                    str.append(" Value: [ERROR]> ").append(e.getMessage());
                }
                str.append(NEXT_LINE);
            }
        }
        str.append(DASH_LINE);
        log.info(str.toString());
    }
}
