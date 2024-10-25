package com.example.ReceiptProcessor.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Log information before method execution
    @Before("execution(* com.example.ReceiptProcessor..*(..))")
    @Profile("dev")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering method: {}", joinPoint.getSignature().getName());
    }

    // Log information after method execution
    @After("execution(* com.example.ReceiptProcessor..*(..))")
    @Profile("dev")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("Exiting method: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "execution(* com.example.ReceiptProcessor..*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: " + joinPoint.getSignature().getName() + " with cause = " + (exception.getCause() != null ? exception.getCause() : "NULL"));
        logger.error("Exception message: " + exception.getMessage());
    }

}
