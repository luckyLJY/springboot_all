package com.kk.learning.quartz;

import com.kk.learning.websocket.MyWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author : K k
 * @date : 9:52 2020/11/3
 */
@Component
@EnableScheduling
public class TimeTask {
    private static Logger logger = LoggerFactory.getLogger(TimeTask.class);

    @Scheduled(cron = "0/4 * * * * ?")
    public void test(){
        System.err.println("********* 定时任务执行 **************");
        CopyOnWriteArraySet<MyWebSocket> webSocketSet =	MyWebSocket.getWebSocketSet();
        int i = 0 ;
        webSocketSet.forEach(c->{
            try {
                c.sendMessage(" 定时发送 " + new Date().toLocaleString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.err.println("\n 定时任务完成.......");
    }
}

