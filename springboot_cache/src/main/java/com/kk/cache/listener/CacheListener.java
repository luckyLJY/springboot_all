package com.kk.cache.listener;


import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-05 18:42
 **/
@Component
public class CacheListener extends CacheEventListenerAdapter {
    private static final Logger logger= LoggerFactory.getLogger(CacheListener.class);

    @Override
    public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
        super.notifyElementPut(cache,element);
        logger.info("-------存入缓存---------");
    }

    @Override
    public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
        super.notifyElementRemoved(cache,element);
        logger.info("--------删除缓存---------");
    }
}
