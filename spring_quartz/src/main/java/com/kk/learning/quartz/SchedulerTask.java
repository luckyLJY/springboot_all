package com.kk.learning.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : K k
 * @date : 8:45 2020/11/3
 * 定时任务
 */
@Component
public class SchedulerTask {
    private static final Logger log = LoggerFactory.getLogger(SchedulerTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    //固定的时间执行 也就是 5秒执行一次

    /**
     * fixedRate ：固定的时间执行 也就是 多少秒执行一次。 这个上面的代码已介绍。
     * fixedDelay：执行完毕后再过5秒后执行。
     * initialDelay：启动后延迟多少秒后执行 不能单独使用。
     */
    //@Scheduled(fixedRate = 5000)
    public void reportCurrentTimeFixedRate() {
        try {
            Thread.sleep(4*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("reportCurrentTimeFixedRate The time is now {}", dateFormat.format(new Date()));
    }

    /**
     * cron表达式指定周期
     */
    //@Scheduled(cron = "0/5 * * * * ?")
    public void reportCurrentTimeWithCron() {
        try {
            Thread.sleep(4*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("reportCurrentTimeFixedRate The time is now {}", dateFormat.format(new Date()));
    }
}
