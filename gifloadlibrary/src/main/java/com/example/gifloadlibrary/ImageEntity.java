package com.example.gifloadlibrary;

/**
 * Created by Ziv on 2016/7/23.
 */
public class ImageEntity {
    private byte[] bytes;
    private boolean isGif;
    private int size;

    public ImageEntity(byte[] bytes) {
        this.bytes = bytes;
        this.isGif = true;
        this.size = bytes.length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public boolean isGif() {
        return isGif;
    }

    public int getSize() {
        return size;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();

    }
}
