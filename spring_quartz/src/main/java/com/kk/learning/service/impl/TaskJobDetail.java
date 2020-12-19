package com.kk.learning.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-13 23:11
 **/
@DisallowConcurrentExecution
@Slf4j
public class TaskJobDetail extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("begin delwith batch task >>>>>>>>>>>>>>>>>>>>>>>");
        String batchId = context.getJobDetail().getKey().getName();
        log.info("执行的任务id为：[{}]", batchId);
    }
}
