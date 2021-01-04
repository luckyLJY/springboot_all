package com.kk.cache.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Kk
 * @create: 2021-01-02 10:04
 **/
public class CacheListener extends Thread {

    private Cache cache;

    private volatile boolean stop = false;
    private volatile long ONE_SECOND = 1000;

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public CacheListener(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void run() {
        long time = cache.getConfigure().getDiskExpiryThreadIntervalSeconds();
        try {
            while (!stop) {
                sleep(time * ONE_SECOND);
                threadCheckElement();
            }
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public void destory() {
        ONE_SECOND = 0;
        stop = true;
    }


    public void threadCheckElement() {
        List<Object> keys = new ArrayList<Object>();
        Map<Object, Element> map = cache.getAll();
        if (map != null && map.size() > 0) {
            for (Map.Entry<Object, Element> e0 : map.entrySet()) {
                Element e = e0.getValue();
                if (e != null && e.isExpired()) {
                    keys.add(e0.getKey());
                }
            }
        }
        cache.removeAll(keys.toArray());
    }

}
