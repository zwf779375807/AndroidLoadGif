package com.example.gifloadlibrary;

import android.util.LruCache;

/**
 * Created by ziv on 2017/11/28.
 */

public class LruImageCache extends LruCache<String, ImageEntity> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    public LruImageCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, ImageEntity value) {
        return value.getSize() + 1024;
    }

    /**
     * 根据url获取图片等相关信息
     *
     * @param url
     * @return
     */
    ImageEntity getMemoryCache(String url) {
        return get(url);
    }

    /**
     * 在内存中存储信息
     *
     * @param url
     * @param imageEntity
     */
    void putMemoryCache(String url, ImageEntity imageEntity) {
        put(url, imageEntity);
    }
}
