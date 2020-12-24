package com.kk.learning.config;

import com.kk.learning.entity.SimpleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author : K k
 * @date : 22:21 2020/11/2
 */
@Configuration
public class SimpleJobConfig  {
    //@Bean
    public JobDetail simpleJobDetail() {
        return JobBuilder.newJob(SimpleJob.class).withIdentity("myJob").storeDurably()
                .usingJobData("serviceCode","delete overdue orders")
                .build();
    }
    //@Bean
    public Trigger simpleJobTrigger() {
        //定义每三秒执行一次
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3).repeatForever();
        //定义触发器
        return TriggerBuilder.newTrigger().forJob(simpleJobDetail()).withIdentity("myJobTrigger").withSchedule(simpleScheduleBuilder).build();
    }
}
