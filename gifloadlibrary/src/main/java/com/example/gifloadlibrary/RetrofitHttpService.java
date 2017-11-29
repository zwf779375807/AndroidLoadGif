package com.example.gifloadlibrary;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Ziv on 2016/6/28.
 */
public interface RetrofitHttpService {

    @Streaming/*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}
