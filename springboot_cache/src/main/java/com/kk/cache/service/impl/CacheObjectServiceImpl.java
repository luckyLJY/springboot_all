package com.kk.cache.service.impl;

import com.kk.cache.entity.CacheObject;
import com.kk.cache.mapper.CacheObjectMapper;
import com.kk.cache.service.CacheObjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
@CacheConfig(cacheNames = {"myCache"})
public class CacheObjectServiceImpl extends ServiceImpl<CacheObjectMapper, CacheObject> implements CacheObjectService {

    @Override
    @Cacheable(key = "targetClass + methodName+ #p0")
    public List<CacheObject> queryAll() {
        return baseMapper.selectList(null);
    }

    @Override
    @Cacheable(value = "cache",key = "#p0")
    public CacheObject getOneObject(int id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void saveObject(CacheObject cacheObject) {
        baseMapper.insert(cacheObject);
    }

    @Override
    @CacheEvict(value = "cache",key = "#p0")
    public void deleteObject(int id) {
        baseMapper.deleteById(id);
    }

    //方法调用后清空所有缓存
    @Override
    @CacheEvict(value="accountCache",allEntries=true)
    public void deleteAllAfter() {
        baseMapper.delete(null);
    }

    //方法调用前清空所有缓存
    @Override
    @CacheEvict(value="accountCache",beforeInvocation = true)
    public void deleteAllBefore() {
        baseMapper.delete(null);
    }
}
