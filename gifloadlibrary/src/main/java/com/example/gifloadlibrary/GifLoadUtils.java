package com.example.gifloadlibrary;

import android.content.Context;

/**
 * Created by ziv on 2017/11/28.
 */

public class GifLoadUtils {
    private GifLoadUtils() {
    }

    private static GifLoadUtils instance;

    public static synchronized GifLoadUtils getInstance() {
        if (null == instance) {
            instance = new GifLoadUtils();
        }
        return instance;
    }

    public synchronized ImageLoader getImageLoader(Context context) {
        ImageLoader imageLoader = new ImageLoader(context);
        return imageLoader;
    }

}
