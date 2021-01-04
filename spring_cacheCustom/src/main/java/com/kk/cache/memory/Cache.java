package com.kk.cache.memory;

/**
 * @description: 这是对缓存级的控制判断
 * @author: Kk
 * @create: 2021-01-02 10:04
 **/
public class Cache extends MemoryCache{

    private CacheConfiguration configure;
    private   CacheListener listener;

    public Cache(CacheConfiguration configure) {
        super(configure);
        this.configure = configure;
        if(!configure.getEternal()  &&  configure.getIsNeedCacheCheckListener()){
            listener = new CacheListener(this);
            listener.start();
        }
    }
    public CacheConfiguration getConfigure() {
        return configure;
    }

    // 销毁
    public void destory(){
        try{
            super.clear();
            if(listener != null){
                listener.interrupt();
                listener.stop();
                listener = null;
            }
        }catch (Exception e) {
        }

    }
}
