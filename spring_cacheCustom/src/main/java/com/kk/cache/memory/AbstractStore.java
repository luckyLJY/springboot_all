package com.kk.cache.memory;

import java.util.Map;

/**
 * @description: 抽象类实现
 * @author: Kk
 * @create: 2021-01-02 10:02
 **/
public abstract class AbstractStore implements Store{

    protected Map<Object, Element> map;

    public AbstractStore(){}
    public AbstractStore(Map<Object, Element> map){
        this.map = map;
    }

    @Override
    public Element get(Object key) {
        Element e = map.get(key);
        return e;
    }

    public Map<Object, Element> getAll(){
        return map;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Element put(Element e) {
        return map.put(e.getKey(), e);
    }

    @Override
    public void remove(Object key) {
        map.remove(key);
    }

    @Override
    public Integer size() {
        return map.size();
    }

    @Override
    public void removeAll(Object[] keys) {
        for(int i =0;i<keys.length;i++){
            remove(keys[i]);
        }
    }
}
