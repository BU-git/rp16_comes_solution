package com.bionic.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger log = LogManager.getLogger();

    @Pointcut("execution(* com.bionic.service.UserService.*(..))")
    private void allMethods() {

    }

    @Before("allMethods()")
    public void logBefore(JoinPoint joinpoint) {
        if (!log.isTraceEnabled()) return;
        Object className = joinpoint.getTarget().getClass().getName();
        String methodName = joinpoint.getSignature().getName();
        log.trace("Entering to Class " + className + " With Method 	Name "  + methodName);
        Object[] obj = joinpoint.getArgs();
        for (Object o : obj){
            String temp = "Parameter = ";
            if (o != null) temp += o.toString();
            log.trace(temp);
        }
    }
    @AfterReturning("allMethods()")
    public void logAfterReturn(JoinPoint joinpoint) {
        if (!log.isTraceEnabled()) return;
        Object className = joinpoint.getTarget().getClass().getName();
        String methodName = joinpoint.getSignature().getName();
        log.trace("After Entering to Class " + className + " With Method Name " + methodName);
    }
    @AfterThrowing(pointcut = "allMethods()" , throwing = "ex")
    public void logAfterThrowing(JoinPoint joinpoint, Exception ex) {
        Object className = joinpoint.getTarget().getClass().getName();
        String methodName = joinpoint.getSignature().getName();
        log.error("After Throwing From Method " + methodName + " in Class " + className, ex);
    }
}
