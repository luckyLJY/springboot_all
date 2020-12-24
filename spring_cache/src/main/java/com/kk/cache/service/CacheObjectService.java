package com.kk.cache.service;

import com.kk.cache.entity.CacheObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kk
 * @since 2020-11-23
 */
public interface CacheObjectService extends IService<CacheObject> {
    public List<CacheObject> queryAll();

    public CacheObject getById(int id);

    public void saveObject(CacheObject cacheObject);

    public void deleteById(int id);

    public void deleteAllAfter();

    public void deleteAllBefore();
}
