package com.example.gifloadlibrary;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;

import static com.example.gifloadlibrary.ImageLoader.ASSETS;
import static com.example.gifloadlibrary.ImageLoader.DRAWABLE;
import static com.example.gifloadlibrary.ImageLoader.URL;

/**
 * Created by ziv on 2017/11/28.
 * 这里做请求网路还是加载数据的操作
 */

class ReadImageTask {
    private String name;
    private int drawable;
    private DisklruImageChache mImageCache;
    private Context mContext;
    private String mUrl;
    private ImageView imageView;

    public ReadImageTask(Context mContext, DisklruImageChache mImageCache, String url) {
        mUrl = url;
        this.mContext = mContext;
        this.mImageCache = mImageCache;
    }

    /**
     * 加载drawable下gif
     *
     * @param mContext
     * @param drawable
     */
    public ReadImageTask(Context mContext, int drawable) {
        this.mContext = mContext;
        this.drawable = drawable;
    }

    /**
     * 从assert从加载gif
     *
     * @param mContext
     * @param name
     */
    public ReadImageTask(Context mContext, String name) {
        this.mContext = mContext;
        this.name = name;
    }

    public void setView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void execute(String CURRENTLOADFROM) {
        if (TextUtils.isEmpty(CURRENTLOADFROM)) {
            return;
        }
        switch (CURRENTLOADFROM) {
            case URL:
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
                    HttpUtil.downloadFile(mUrl, imageView, mImageCache);
                }
                break;
            case DRAWABLE:
                //从drawable中加载
                if (drawable <= 0) {
                    return;
                }
                try {
                    GifDrawable gifFromResource = new GifDrawable(mContext.getResources(), drawable);
                    imageView.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case ASSETS:
                //从drawable中加载
                if (TextUtils.isEmpty(name)) {
                    return;
                }
                try {
                    GifDrawable gifFromResource = new GifDrawable(mContext.getAssets(), name);
                    imageView.setImageDrawable(gifFromResource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

}
