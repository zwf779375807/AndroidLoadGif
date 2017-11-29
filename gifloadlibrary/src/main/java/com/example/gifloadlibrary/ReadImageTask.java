package com.example.gifloadlibrary;

import android.content.Context;
import android.widget.ImageView;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by ziv on 2017/11/28.
 * 这里做请求网路还是加载数据的操作
 */

class ReadImageTask {
    private DisklruImageChache mImageCache;
    private Context mContext;
    private String mUrl;
    private ImageView imageView;

    public ReadImageTask(Context mContext, DisklruImageChache mImageCache, String url) {
        mUrl = url;
        this.mContext = mContext;
        this.mImageCache = mImageCache;
    }

    public void setView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void execute() {
        if (null == mUrl || mUrl.isEmpty() || Util.generateKey(mUrl).isEmpty()) {
            return;
        }
        ImageEntity imageEntity = mImageCache.getBitmap(Util.generateKey(mUrl));
        if (null != imageEntity) {//缓存加载
            if (imageEntity.isGif()) {
                try {
                    GifDrawable gifDrawable = new GifDrawable(imageEntity.getBytes());
                    imageView.setImageDrawable(gifDrawable);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (mUrl.startsWith("http")) {
            //没有缓存请求网络并且缓存
            HttpUtil.downloadFile(mContext, mUrl, imageView, mImageCache);
        }
    }
}
