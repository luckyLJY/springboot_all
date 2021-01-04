package com.kk.cache.memory;

import java.util.Collection;

/**
 * @description:
 * @author: Kk
 * @create: 2021-01-02 09:55
 **/
public interface Store {
    // 获得缓存名字
    public String getName();
    // 存放元素
    public Element put(Element e);
    public Collection<Element> putAll(Collection<Element> elements);
    // 获取元素
    public Element get(Object key);
    // 清除元素
    public void clear();
    // 移除元素
    public void remove(Object key);
    public void removeAll(Object[] keys);
    // 获得的元素长度
    public Integer size();
}
