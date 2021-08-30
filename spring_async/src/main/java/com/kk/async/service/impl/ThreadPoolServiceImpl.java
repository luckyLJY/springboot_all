package com.kk.async.service.impl;


import com.kk.async.service.IThreadPoolService;
import com.kk.async.threadpool.core.DynamicThreadPoolManager;
import com.kk.async.threadpool.vo.ThreadPoolArgsVO;
import com.kk.async.threadpool.vo.ThreadPoolDetailInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ThreadPoolServiceImpl
 *
 * @author kkmystery
 * @version 1.0 2021/8/30
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ThreadPoolServiceImpl implements IThreadPoolService {
    private final DynamicThreadPoolManager dynamicThreadPoolManager;

    @Override
    public List<ThreadPoolArgsVO> getAllThreadPoolArgs() {
        return dynamicThreadPoolManager.getAllThreadPoolArgsVO();
    }

    @Override
    public ThreadPoolDetailInfoVO getThreadPoolDetailByName(String name) {
        return dynamicThreadPoolManager.getThreadPoolDetailByName(name);
    }

    @Override
    public boolean updateThreadPool(ThreadPoolArgsVO threadPoolArgs) {
        return dynamicThreadPoolManager.updateThreadPool(threadPoolArgs);
    }
}
