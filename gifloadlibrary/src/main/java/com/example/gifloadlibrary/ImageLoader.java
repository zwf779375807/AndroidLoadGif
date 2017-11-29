package com.example.gifloadlibrary;

import android.content.Context;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by ziv on 2017/11/28.
 */

public class ImageLoader {
    private static final int CACHE_SIZE = 1024 * 1024 * 50;
    private Context mContext;
    private DisklruImageChache mImageCache;
    private ReadImageTask readImageTask;

    public ImageLoader(Context context) {
        mContext = context;
        String disCacheDir = getDisCacheDir(context);
        mImageCache = new DisklruImageChache(disCacheDir, CACHE_SIZE);
    }


    private String getDisCacheDir(Context context) {
        String path = context.getCacheDir().getPath();
        return path + File.separator + "gifCache";
    }

    public ImageLoader load(String url) {
        readImageTask = new ReadImageTask(mContext, mImageCache, url);
        return this;
    }

    public void into(ImageView imageView) {
        readImageTask.setView(imageView);
        readImageTask.execute();
    }
}
