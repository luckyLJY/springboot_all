package com.kk.async.threadpool.core;

import com.kk.async.threadpool.vo.ThreadPoolArgsVO;
import com.kk.async.threadpool.vo.ThreadPoolDetailInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态线程池
 *
 * @author kkmystery
 * @version 1.0 2021/8/30
 * @since 1.0.0
 */
@Slf4j
@Component
public class DynamicThreadPoolManager implements ApplicationRunner, ApplicationContextAware {
    private static ConcurrentHashMap<String, DynamicThreadPoolExecutor> threadPools;
    private ApplicationContext context;

    @Override
    public void run(ApplicationArguments args) {
        Map<String, DynamicThreadPoolExecutor> threadPoolBeans = context.getBeansOfType(DynamicThreadPoolExecutor.class);
        int size = threadPoolBeans.size();
        if (size == 0) {
            return;
        }
        int expectSize = (int) (size / 0.75f + 1.0f);
        threadPools = new ConcurrentHashMap<>(expectSize);
        threadPools.putAll(threadPoolBeans);
        log.warn("初始化线程池管理监控器: {}", threadPoolBeans);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public List<ThreadPoolArgsVO> getAllThreadPoolArgsVO() {
        List<ThreadPoolArgsVO> poolInfos = new ArrayList<>();
        for (Map.Entry<String, DynamicThreadPoolExecutor> entry : threadPools.entrySet()) {
            DynamicThreadPoolExecutor threadPool = entry.getValue();
            ThreadPoolArgsVO threadPoolInfo = mergeArgs(threadPool);
            poolInfos.add(threadPoolInfo);
        }
        return poolInfos;
    }

    public ThreadPoolDetailInfoVO getThreadPoolDetailByName(String name) {
        DynamicThreadPoolExecutor threadPool = threadPools.get(name);
        return mergeDetails(threadPool);
    }

    public boolean updateThreadPool(ThreadPoolArgsVO threadPoolArgsVO) {
        try {
            DynamicThreadPoolExecutor threadPool = threadPools.get(threadPoolArgsVO.getThreadPoolName());
            threadPool.setAppName(threadPoolArgsVO.getAppName());
            threadPool.setCorePoolSize(threadPoolArgsVO.getCorePoolSize());
            threadPool.setMaxPoolSize(threadPoolArgsVO.getMaxPoolSize());
            threadPool.setQueueCapacity(threadPoolArgsVO.getQueueCapacity());
            threadPool.setKeepAliveSeconds(threadPoolArgsVO.getKeepAliveSeconds());
            threadPool.setActiveThreshold(threadPoolArgsVO.getActiveThreshold());
            threadPool.setCapacityThreshold(threadPoolArgsVO.getCapacityThreshold());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private ThreadPoolDetailInfoVO mergeDetails(DynamicThreadPoolExecutor threadPool) {
        return ThreadPoolDetailInfoVO
                .builder()
                .appName(threadPool.getAppName())
                .threadPoolName(threadPool.getThreadPoolName())
                .corePoolSize(threadPool.getCorePoolSize())
                .maxPoolSize(threadPool.getMaxPoolSize())
                .queueCapacity(threadPool.getQueueCapacity())
                .keepAliveSeconds(threadPool.getKeepAliveSeconds())
                .queueRemainingCapacity(threadPool.getQueue().remainingCapacity())
                .activeCount(threadPool.getActiveCount())
                .allowCoreThreadTimeOut(threadPool.allowsCoreThreadTimeOut())
                .completedTaskCount(threadPool.getCompletedTaskCount())
                .largestPoolSize(threadPool.getLargestPoolSize())
                .queueType(threadPool.getQueue().getClass().getSimpleName())
                .taskCount(threadPool.getTaskCount())
                .queueSize(threadPool.getQueue().size())
                .poolSize(threadPool.getPoolSize())
                .rejectCount(threadPool.getRejectCount())
                .currentTime(new Date(System.currentTimeMillis()))
                .threadFactoryType(threadPool.getThreadFactory().getClass().getSimpleName())
                .rejectHandlerType(threadPool.getRejectedExecutionHandler().getClass().getSimpleName())
                .build();
    }

    private ThreadPoolArgsVO mergeArgs(DynamicThreadPoolExecutor threadPool) {
        return new ThreadPoolArgsVO(
                threadPool.getCorePoolSize(),
                threadPool.getMaxPoolSize(),
                threadPool.getKeepAliveSeconds(),
                threadPool.getQueueCapacity(),
                threadPool.getCapacityThreshold(),
                threadPool.getActiveThreshold(),
                threadPool.getQueue().getClass().getSimpleName(),
                threadPool.getAppName(),
                threadPool.getThreadPoolName(),
                threadPool.getThreadFactory().getClass().getSimpleName(),
                threadPool.getRejectedExecutionHandler().getClass().getSimpleName());
    }
}
