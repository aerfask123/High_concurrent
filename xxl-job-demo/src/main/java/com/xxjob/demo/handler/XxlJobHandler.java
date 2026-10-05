package com.xxjob.demo.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XxlJobHandler implements JobHandlerInterface {

    @Override
    @XxlJob("UpdateStatus")
    public void updateStatus() {
        String jobParam = XxlJobHelper.getJobParam();
        log.info("任务执行：jobParam:{}", jobParam);
    }
}
