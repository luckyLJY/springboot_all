package com.kk.cache.service.impl;

import com.kk.cache.entity.CacheObject;
import com.kk.cache.mapper.CacheObjectMapper;
import com.kk.cache.service.CacheObjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kk
 * @since 2020-11-23
 */
@Service
@CacheConfig(cacheNames = {"objectCache"}) //统一配置缓存组
public class CacheObjectServiceImpl extends ServiceImpl<CacheObjectMapper, CacheObject> implements CacheObjectService {

    //@CachePut 保证方法被调用，又希望结果被保存

    @Override
    @Cacheable(key = "targetClass + methodName" ,sync = true)
    public List<CacheObject> queryAll() {
        return baseMapper.selectList(null);
    }

    @Override
    @Cacheable(key = "'object'+#id", sync = true)
    public CacheObject getById(int id) {
        return baseMapper.selectById(id);
    }

    @Override
    @CacheEvict(key = "'object'+#id") //删除缓存
    public void deleteById(int id) {
        baseMapper.deleteById(id);
    }

    @Override
    public void saveObject(CacheObject cacheObject) {
        baseMapper.insert(cacheObject);
    }

    //方法调用后清空所有缓存
    @Override
    @CacheEvict(/*value="accountCache",*/allEntries=true)
    public void deleteAllAfter() {
        baseMapper.delete(null);
    }

    //方法调用前清空所有缓存
    @Override
    @CacheEvict(/*value="accountCache",*/beforeInvocation = true)
    public void deleteAllBefore() {
        baseMapper.delete(null);
    }
}
