package com.xxjob.demo.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.UUID;

@Slf4j
@Order(1)
@Aspect
@Component
public class XXlJobAspect {

    //切点
//    @Pointcut("@annotation(com.xxl.job.core.handler.annotation.XxlJob)")
//    public void pointCut() {
//    }

    /**
     * 把切点内置
     */
    @Around("@annotation(com.xxl.job.core.handler.annotation.XxlJob) && @annotation(xxlJob)")
    public Object doAround(ProceedingJoinPoint joinPoint, XxlJob xxlJob) throws Throwable {
        String traceId = UUID.randomUUID().toString();
        log.info("xxl-job-demo-aspect");
        MDC.put("traceId", traceId);
        
        String jobName = xxlJob.value();
        StopWatch sw = new StopWatch();
        sw.start();
        Object result = null;
        try {
            log.info("xxl-job-demo-aspect-start:{}", jobName);
            result = joinPoint.proceed();
            log.info("xxl-job-demo-aspect-end:{}", jobName);

        } catch (Exception e) {
            log.error("xxl-job-demo-aspect-error:{}", jobName, e);
            failure(traceId);
            return null;
        }
        sw.stop();
        log.info("xxl-job-demo-aspect-time:{}", jobName, sw.getTotalTimeMillis());
        success(traceId);

        return result;
    }

    private void success(String traceId) {
        XxlJobHelper.handleSuccess("traceId=" + traceId);
        MDC.remove("traceId");
    }

    private void failure(String traceId) {
        XxlJobHelper.handleFail("traceId=" + traceId);
        MDC.remove("traceId");
    }
}
