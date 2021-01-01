package com.kk.redisson.service.Impl;

import com.kk.redisson.common.SignUpResponse;
import com.kk.redisson.service.IUserService;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private IUserService userService;
    @Autowired
    private RedissonClient redissonClient;

    @Test
    void signUp() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                SignUpResponse signUpResponse = userService.signUp("1021123064@qq.com");
                System.out.println(signUpResponse);
            });
            thread.start();
        }
        Thread.sleep(10000);
    }

    @Test
    void add(){
        userService.add(1L);
    }

    @Test
    void pollExpiredUsers(){
        userService.pollExpiredUsers().stream().forEach(System.out::println);
    }

    @Test
    void bloomFilterDemo(){
        RBloomFilter<String> bloom = redissonClient.getBloomFilter("bloom");
        bloom.tryInit(1000L,0.01);
        bloom.add("lkx");
        bloom.add("omg");
        bloom.add("wdf");
        bloom.add("pzx");

        // 个数
        System.out.println(bloom.count());
        System.out.println("============");
        // 过滤器的容量
        System.out.println(bloom.getSize());
        System.out.println("============");
        // 判断是否存在
        System.out.println(bloom.contains("lkx"));
    }

    @Test
    void topicSubscription(){
        RTopic topic1 = redissonClient.getTopic("topic1");
        // 监听消息
        topic1.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                System.out.println(charSequence.toString());
                System.out.println("收到的消息: "+s);
            }
        });

        RTopic topic2= redissonClient.getTopic("topic1");
        long nihao = topic2.publish("nihao");
        System.out.println(nihao);
    }
}