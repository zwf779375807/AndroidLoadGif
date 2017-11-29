package com.example.gifloadlibrary;

/**
 * Created by ziv on 2017/11/28.
 * desc 做信息的存储获取
 */

public interface ImageCache {
    //根据url获取相关图片之类的信息
    ImageEntity getBitmap(String url);

    //以url为key存储图片相关信息
    void putBitmap(String url, byte[] data);

}
