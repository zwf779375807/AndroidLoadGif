package com.example.gifloadlibrary;

import android.content.Context;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by Ziv on 2016/6/28.
 */
public class HttpUtil {
    /**
     * 文件下载
     *
     * @param url
     * @param imageView
     */
    public static void downloadFile(final Context context, final String url, final ImageView imageView, final ImageCache imageCache) {
        RetrofitHttpService downLoadService = ServiceGenerator.createDownLoadService(RetrofitHttpService.class);
        Call<ResponseBody> call = downLoadService.downloadFile(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Observable.create(new Observable.OnSubscribe<byte[]>() {
                    @Override
                    public void call(Subscriber<? super byte[]> subscriber) {
                        byte[] bytes = new byte[0];
                        try {
                            bytes = response.body().bytes();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        subscriber.onNext(bytes);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<byte[]>() {
                            @Override
                            public void call(byte[] data) {
                                if (null != data && data.length > 0) {
                                    if (Util.doGif(imageView, data)) {// 是gif播放同时缓存
                                        cacheImage(url, data, imageCache);
                                    }
                                }
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    /**
     * 缓存数据
     *
     * @param requestUrl
     * @param data
     */
    private static void cacheImage(String requestUrl, byte[] data, ImageCache imageCache) {
        String key = Util.generateKey(requestUrl);
        if (key.isEmpty()) return;
        imageCache.putBitmap(key, data);
    }

}
