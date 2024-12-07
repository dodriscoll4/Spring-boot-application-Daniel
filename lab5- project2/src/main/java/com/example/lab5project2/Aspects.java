//check properly

package com.example.lab5project2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class Aspects {

    // Pointcut definition for all methods in service layer
    @Pointcut("execution(* com.example.lab5project2.services.*.*(..))")
    public void serviceLayer() {}

    // Before advice - runs before method execution
    @Before("serviceLayer()")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        log.info("Before executing method: {} with arguments: {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    // After advice - runs after method execution (success or failure)
    @After("serviceLayer()")
    public void afterMethodExecution(JoinPoint joinPoint) {
        log.info("After executing method: {}",
                joinPoint.getSignature().getName());
    }

    // AfterReturning advice - runs after successful execution
    @AfterReturning(
            pointcut = "serviceLayer()",
            returning = "result"
    )
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("Method: {} completed successfully with result: {}",
                joinPoint.getSignature().getName(),
                result);
    }

    // AfterThrowing advice - runs after exception is thrown
    @AfterThrowing(
            pointcut = "serviceLayer()",
            throwing = "exception"
    )
    public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) {
        log.error("Method: {} threw exception: {}",
                joinPoint.getSignature().getName(),
                exception.getMessage());
    }

    // Around advice - wraps method execution
    @Around("serviceLayer()")
    public Object aroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            // Execute the actual method
            Object result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            log.info("Method: {} executed in {} ms",
                    joinPoint.getSignature().getName(),
                    (endTime - startTime));

            return result;

        } catch (Exception e) {
            log.error("Error executing method: {}",
                    joinPoint.getSignature().getName(),
                    e);
            throw e;
        }
    }
}