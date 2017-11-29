package com.example.ziv.gifplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.gifloadlibrary.GifLoadUtils;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_CALL_PHONE = 100;
    private static final int REQUEST_CODE_ASK_CALL_PHONE2 = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        ImageView gifImageView = (ImageView) findViewById(R.id.gifimageview);
        ImageView gifDrawbleImageView = (ImageView) findViewById(R.id.gifdrawable);
        ImageView gifAssetImageView = (ImageView) findViewById(R.id.gifasset);
        GifLoadUtils.getInstance().getImageLoader(this).loadUrl("http://s9.rr.itc.cn/r/wapChange/20173_31_10/a0rq600946115627303.gif").into(gifImageView);
        GifLoadUtils.getInstance().getImageLoader(this).loadDrawableOrRaw(R.raw.pao).into(gifDrawbleImageView);
        GifLoadUtils.getInstance().getImageLoader(this).loadAsset("maodazan.gif").into(gifAssetImageView);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }
            int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_CALL_PHONE2);
                return;
            }
        }
    }

}
