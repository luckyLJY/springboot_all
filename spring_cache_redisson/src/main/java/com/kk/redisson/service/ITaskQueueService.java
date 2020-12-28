package com.kk.redisson.service;

import java.util.List;
import java.util.Optional;

public interface ITaskQueueService {

    boolean addOne(Long taskId);

    Optional<Long> qieruOmdex(long taskId);

    List<Long> pollFirst(int size);
}
