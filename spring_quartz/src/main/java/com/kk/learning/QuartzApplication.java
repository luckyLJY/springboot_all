package com.kk.learning;

import javafx.scene.input.DataFormat;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : K k
 * @date : 22:19 2020/11/2
 */
@SpringBootApplication
@EnableScheduling
public class QuartzApplication {
    private final static Logger logger= LoggerFactory.getLogger(QuartzApplication.class);
    private final static SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class,args);
        logger.info("reportCurrentTimeInitialDelay fixedRate The time is start {}", dateFormat.format(new Date()));
    }
}
