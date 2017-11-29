package com.example.gifloadlibrary;

import android.widget.ImageView;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    public static void downloadFile(final String url, final ImageView imageView, final ImageCache imageCache) {
        RetrofitHttpService downLoadService = ServiceGenerator.createDownLoadService(RetrofitHttpService.class);
        Call<ResponseBody> call = downLoadService.downloadFile(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Observable.create(new ObservableOnSubscribe<byte[]>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<byte[]> e) throws Exception {
                        byte[] bytes = new byte[0];
                        try {
                            bytes = response.body().bytes();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        e.onNext(bytes);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<byte[]>() {
                            @Override
                            public void accept(@NonNull byte[] data) throws Exception {
                                if (null != data && data.length > 0) {
                                    if (Util.doGif(imageView, data)) {// 是gif播放同时缓存
                                        cacheImage(url, data, imageCache);
                                    }
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
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
