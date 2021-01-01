package com.kk.redisson.service;

import com.kk.redisson.entity.Material;

import java.util.Optional;

public interface IMaterialCacheService {

    // 添加到缓存
    boolean addOne(Material material);

    // 通过materialId查找
    Optional<Material> findById(String materialId);
}
