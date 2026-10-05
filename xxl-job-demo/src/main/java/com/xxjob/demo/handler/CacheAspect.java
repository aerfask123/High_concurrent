package com.xxjob.demo.handler;

import com.xxjob.demo.annotation.Cached;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Order(1)
@Component
public class CacheAspect {

    @Autowired
    private CacheHandler cacheHandler;

    @Pointcut("@annotation(com.xxjob.demo.annotation.Cached)")
    public void save() {

    }

    @Around("save()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getTarget();
        log.info("target:{}", target.getClass().getName());

        String methodName = joinPoint.getSignature().getName();
        log.info("切入的方法名:{}", methodName);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("args:{}", arg);
        }

        if (method.isAnnotationPresent(Cached.class)) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            Object result = joinPoint.proceed(args);
            stopWatch.stop();

            log.info("method:{},result:{},time:{}", methodName, result, stopWatch.getTotalTimeMillis());
            Cached cached = method.getAnnotation(Cached.class);
            String key = cached.key() + args[0];
            log.info("key:{},title:{}", key, cached.title());
            if (cached.isSaveRequestData()) {
                cacheHandler.setCache(key, result);
            }
            return result;
        }
        return null;
    }

    @Before("execution(* com.xxjob.demo.service.UserService.*Customer(..))")
    public void before() {
        log.info("before");
    }
}
