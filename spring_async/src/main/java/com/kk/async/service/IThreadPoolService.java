package com.kk.async.service;


import com.kk.async.threadpool.vo.ThreadPoolArgsVO;
import com.kk.async.threadpool.vo.ThreadPoolDetailInfoVO;

import java.util.List;

/**
 * IThreadPoolService
 *
 * @author kkmystery
 * @version 1.0 2021/8/30
 * @since 1.0.0
 */
public interface IThreadPoolService {

    public List<ThreadPoolArgsVO> getAllThreadPoolArgs();

    public ThreadPoolDetailInfoVO getThreadPoolDetailByName(String name);

    public boolean updateThreadPool(ThreadPoolArgsVO threadPoolArgs);

}
