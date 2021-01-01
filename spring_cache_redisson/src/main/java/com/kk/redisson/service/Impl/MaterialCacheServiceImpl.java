package com.kk.redisson.service.Impl;

import cn.hutool.socket.nio.Operation;
import com.kk.redisson.constant.RedisConstants;
import com.kk.redisson.entity.Material;
import com.kk.redisson.service.IMaterialCacheService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author luokexiong
 * @version 1.0 2020/12/28
 * @since 1.0.0
 */
@Service
public class MaterialCacheServiceImpl implements IMaterialCacheService {
    @Autowired
    private RedissonClient client;

    @Override
    public boolean addOne(Material material) {
        // 添加了新素材
        RBucket<Material> bucket = client.getBucket(RedisConstants.generateMaterialKey(material.getId()));
        return bucket.trySet(material);
    }

    @Override
    public Optional<Material> findById(String materialId) {
        RBucket<Material> bucket = client.getBucket(RedisConstants.generateMaterialKey(materialId));
        return Optional.of(bucket.get());
    }
}
