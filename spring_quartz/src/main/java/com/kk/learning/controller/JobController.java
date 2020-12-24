package com.kk.learning.controller;


import com.kk.learning.common.R;
import com.kk.learning.entity.QuartzJobModule;
import com.kk.learning.quartz.QuartzJobComponent;
import com.kk.learning.service.impl.TaskJobDetail;
import com.kk.learning.utils.CronUtils;
import com.kk.learning.utils.constant.QuartzConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-13 23:12
 **/
@RestController
@RequestMapping("/job")
public class JobController {
    private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private QuartzJobComponent quartzJobComponent;

    @PostMapping("/add")
    public R save() {
        LOGGER.info("新增任务");
        try {
            QuartzJobModule quartzJobModule = new QuartzJobModule();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2020);
            cal.set(Calendar.MONTH, 12);
            cal.set(Calendar.DATE, 14);
            cal.set(Calendar.HOUR_OF_DAY, 11);
            cal.set(Calendar.MINUTE, 26);
            cal.set(Calendar.SECOND, 00);
            Date startDate = cal.getTime();// 任务开始日期为2020年12月14日11点20分

            Calendar endCal = Calendar.getInstance();
            endCal.set(Calendar.YEAR, 2020);
            endCal.set(Calendar.MONTH, 12);
            endCal.set(Calendar.DATE, 20);
            endCal.set(Calendar.HOUR_OF_DAY, 12);
            endCal.set(Calendar.MINUTE, 30);
            endCal.set(Calendar.SECOND, 00);
            Date endDate = endCal.getTime();// 任务结束日期为2020年12月14日12点30分

            quartzJobModule.setStartTime(CronUtils.getStartDate(startDate));
            quartzJobModule.setEndTime(CronUtils.getEndDate(endDate));
            // 注意：在后面的任务中需要通过这个JobName来获取你要处理的数据，因此您可以讲这个设置为你要处理的数据的主键，比如id
            quartzJobModule.setJobName("testJobId");
            quartzJobModule.setTriggerName("tesTriggerNmae");
            quartzJobModule.setJobGroupName(QuartzConstant.QZ_JOB_GROUP_NAME);
            quartzJobModule.setTriggerGroupName(QuartzConstant.QZ_TRIGGER_GROUP_NAME);

            String weeks = "1,2,3,5";// 该处模拟每周1,2,3,5执行任务
            String cronExpression = CronUtils
                    .convertCronExpression(startDate,
                            endDate, weeks.split(","));
            quartzJobModule.setCron(cronExpression);
            quartzJobModule.setJobClass(TaskJobDetail.class);
            quartzJobComponent.addJob(quartzJobModule);
        }
        catch (Exception e) {
            e.printStackTrace();
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("/edit")
    public R edit() {
        LOGGER.info("编辑任务");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 12);
        cal.set(Calendar.DATE, 14);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 00);
        Date startDate = cal.getTime();// 任务开始日期为2020年12月14日12点30分

        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.YEAR, 2020);
        endCal.set(Calendar.MONTH, 12);
        endCal.set(Calendar.DATE, 14);
        endCal.set(Calendar.HOUR_OF_DAY, 13);
        endCal.set(Calendar.MINUTE, 30);
        endCal.set(Calendar.SECOND, 00);
        Date endDate = endCal.getTime();// 任务结束日期为2020年12月14日13点30分
        // "testJobId"为add方法添加的job的name
        quartzJobComponent.modifyJobTime("testJobId", "/10 *  * ? * *", startDate, endDate);
        return R.ok();
    }

    @PostMapping("/pause")
    public R pause(String jobName, String jobGroup) {
        LOGGER.info("停止任务");
        quartzJobComponent.pauseJob("testJobId");
        return R.ok();
    }

    @PostMapping("/resume")
    public R resume(String jobName, String jobGroup) {
        LOGGER.info("恢复任务");
        quartzJobComponent.removeJob("testJobId");
        return R.ok();

    }

    @PostMapping("/remove")
    public R remove(String jobName, String jobGroup) {
        LOGGER.info("移除任务");
        quartzJobComponent.removeJob("testJobId");
        return R.ok();
    }
}
