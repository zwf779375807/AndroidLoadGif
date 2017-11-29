package com.example.gifloadlibrary;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ziv on 2017/11/28.
 * desc 文件存储，内存存储二级存储
 */

public class DisklruImageChache implements ImageCache {


    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);  //运行期最大内存的1/8
    private LruImageCache mMemoryCache;
    private DiskLruCache mDiskCache;

    public DisklruImageChache(String cachePath, int disCacheSize) {
        try {
            File diskCacheDir = new File(cachePath);
            mMemoryCache = new LruImageCache(maxMemory);  //初始化内存存储
            mDiskCache = DiskLruCache.open(diskCacheDir, 1, 1, disCacheSize); //初始化缓存文件存储
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 相关信息获取
     *
     * @param url
     * @return
     */
    @Override
    public ImageEntity getBitmap(String url) {
        ImageEntity imageEntity = mMemoryCache.getMemoryCache(url);
        if (null != imageEntity) {
            return imageEntity;
        }
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(url);
            if (null == snapshot) {
                return null;
            }
            InputStream in = snapshot.getInputStream(0);
            if (null != in) {
                BufferedInputStream bufferedInputStream = new BufferedInputStream(in, 8 * 1024);
                int size = bufferedInputStream.available();
                byte[] bytes = new byte[size];
                if (bufferedInputStream.read(bytes) == -1) { //没东西
                    return null;
                }
                imageEntity = saveToMemory(url, bytes);//文件存储有，存储一份到内存
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != snapshot) {
                snapshot.close();
            }
        }
        return imageEntity;
    }

    /**
     * 相关信息存储
     *
     * @param url
     * @param data
     */
    @Override
    public void putBitmap(String url, byte[] data) {
        //首先存储到内存
        saveToMemory(url, data);
        //保存到cache
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskCache.edit(url);
            if (null == editor) {
                return;
            }
            writeBitmapToFile(data, editor);
            mDiskCache.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != editor) {
                try {
                    editor.abort();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 存储到文件
     *
     * @param data
     * @param editor
     */
    private void writeBitmapToFile(byte[] data, DiskLruCache.Editor editor) throws IOException {
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(editor.newOutputStream(0), 8 * 1024);
            out.write(data);
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 存储到内存
     *
     * @param url
     * @param data
     */
    private ImageEntity saveToMemory(String url, byte[] data) {
        ImageEntity imageEntity = null;
        if (Util.isGif(data)) {
            imageEntity = new ImageEntity(data);
        }
        mMemoryCache.putMemoryCache(url, imageEntity);
        return imageEntity;
    }
}
