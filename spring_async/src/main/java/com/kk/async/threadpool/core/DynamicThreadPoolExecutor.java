package com.kk.async.threadpool.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


public class DynamicThreadPoolExecutor extends ThreadPoolExecutor implements BeanNameAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicThreadPoolExecutor.class);
    private static final AtomicInteger rejectCount = new AtomicInteger();
    private final Object poolMonitor = new Object();
    private String appName;
    private String threadPoolName;
    private double capacityThreshold;
    private double activeThreshold;
    private ThreadLocal<Long> startTime = new ThreadLocal<>();
    private Alert monitor;

    public DynamicThreadPoolExecutor(
            String appName,
            int corePoolSize,
            int maxPoolSize,
            int keepAliveSeconds,
            int queueCapacity,
            ThreadFactory threadFactory,
            RejectedExecutionHandler handler,
            double capacityThreshold,
            double activeThreshold,
            Alert monitor) {
        super(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new AdaptBlockingQueue<>(queueCapacity), threadFactory, handler);
        this.appName = appName;
        this.capacityThreshold = capacityThreshold;
        this.activeThreshold = activeThreshold;
        this.monitor = monitor;
    }

    @Override
    public int getCorePoolSize() {
        synchronized (poolMonitor) {
            return super.getCorePoolSize();
        }
    }

    @Override
    public void setCorePoolSize(int corePoolSize) {
        synchronized (poolMonitor) {
            super.setCorePoolSize(corePoolSize);
        }
    }

    public int getMaxPoolSize() {
        synchronized (poolMonitor) {
            return super.getMaximumPoolSize();
        }
    }

    public void setMaxPoolSize(int maxPoolSize) {
        synchronized (poolMonitor) {
            super.setMaximumPoolSize(maxPoolSize);
        }
    }

    public int getKeepAliveSeconds() {
        synchronized (poolMonitor) {
            return (int) super.getKeepAliveTime(TimeUnit.SECONDS);
        }
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        synchronized (poolMonitor) {
            super.setKeepAliveTime(keepAliveSeconds, TimeUnit.SECONDS);
        }
    }

    public int getQueueCapacity() {
        synchronized (poolMonitor) {
            return ((AdaptBlockingQueue<Runnable>) super.getQueue()).getCapacity();
        }
    }

    public void setQueueCapacity(int queueCapacity) {
        synchronized (poolMonitor) {
            AdaptBlockingQueue<Runnable> adaptBlockingQueue = (AdaptBlockingQueue<Runnable>) super.getQueue();
            adaptBlockingQueue.setCapacity(queueCapacity);
        }
    }

    public boolean isAllowCoreThreadTimeOut() {
        return super.allowsCoreThreadTimeOut();
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        super.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    @Override
    public ThreadFactory getThreadFactory() {
        return super.getThreadFactory();
    }

    @Override
    public void setThreadFactory(ThreadFactory threadFactory) {
        if (threadFactory != null) {
            super.setThreadFactory(threadFactory);
        }
    }

    @Override
    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return super.getRejectedExecutionHandler();
    }

    @Override
    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        if (rejectedExecutionHandler != null) {
            super.setRejectedExecutionHandler(rejectedExecutionHandler);
        }
    }

    public double getCapacityThreshold() {
        return capacityThreshold;
    }

    public void setCapacityThreshold(double capacityThreshold) {
        this.capacityThreshold = capacityThreshold;
    }

    public double getActiveThreshold() {
        return activeThreshold;
    }

    public void setActiveThreshold(double activeThreshold) {
        this.activeThreshold = activeThreshold;
    }

    public int getRejectCount() {
        return rejectCount.get();
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        long start = System.currentTimeMillis();
        startTime.set(start);
        if (monitor != null) {
            if ((double) getActiveCount() / getMaxPoolSize() >= getActiveThreshold()) {
                monitor.alertActive(this);
            }
            if ((double) getQueue().size() / getQueueCapacity() >= getCapacityThreshold()) {
                monitor.alertCapacity(this);
            }
        }
        LOGGER.info("线程池 {} ：活跃线程使用率：{} ，阻塞队列占用率 {}", threadPoolName, (double) getActiveCount() / getMaxPoolSize(), (double) getQueue().size() / getQueueCapacity());
        super.beforeExecute(t, r);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long times = System.currentTimeMillis() - startTime.get();
        startTime.remove();
        LOGGER.info("线程 {} 执行任务 {} 耗时 {} ms", Thread.currentThread().getName(), r, times);
        super.afterExecute(r, t);
    }

    @Override
    public List<Runnable> shutdownNow() {
        //统计已执行任务、正在执行任务、未执行任务数量
        LOGGER.info("{} 线程池即将关闭：{ 已执行任务数: {}, 当前活跃线程数: {}, 未执行任务数: {}}",
                this.threadPoolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        return super.shutdownNow();
    }

    @Override
    public void shutdown() {
        LOGGER.info("{} 线程池即将关闭：{ 已执行任务数: {}, 当前活跃线程数: {}, 未执行任务数: {}}",
                this.threadPoolName, this.getCompletedTaskCount(), this.getActiveCount(), this.getQueue().size());
        super.shutdown();
    }

    @Override
    public void execute(Runnable task) {
        try {
            LOGGER.info("线程 {} 开始执行任务 {}", Thread.currentThread().getName(), task);
            super.execute(task);
        } catch (RejectedExecutionException ex) {
            rejectCount.getAndIncrement();
            LOGGER.error("线程 {} 执行任务 {} 抛出异常：{}", Thread.currentThread().getName(), task, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Future<?> submit(Runnable task) {
        try {
            LOGGER.info("线程 {} 开始执行任务 {}", Thread.currentThread().getName(), task);
            return super.submit(task);
        } catch (RejectedExecutionException ex) {
            rejectCount.getAndIncrement();
            LOGGER.error("线程 {} 执行任务 {} 抛出异常：{}", Thread.currentThread().getName(), task, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        try {
            LOGGER.info("线程 {} 开始执行任务 {}", Thread.currentThread().getName(), task);
            return super.submit(task);
        } catch (RejectedExecutionException ex) {
            rejectCount.getAndIncrement();
            LOGGER.error("线程 {} 执行任务 {} 抛出异常：{}", Thread.currentThread().getName(), task, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void setBeanName(String name) {
        this.threadPoolName = name;
    }
}
