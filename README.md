# AndroidLoadGif
Android加载网络gif图片，使用retrofit+android_gif_drawable结合使用三级缓存方式加载gif动图

  ![img](https://github.com/zwf779375807/AndroidLoadGif/blob/master/app/gifload.gif)
  
# xml中使用

    <ImageView
        android:id="@+id/gifimageview"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintDimensionRatio="1:1"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        />
	
# java代码调用

  1 从网络加载
  
       ImageView gifImageView = (ImageView) findViewById(R.id.gifimageview);
          GifLoadUtils.getInstance().getImageLoader(this).loadUrl("http://n1.itc.cn/img8/wb/recom/2016/08/12/147100143815015802.GIF").into(gifImageView);

 2 从drawable或者raw加载
 
        GifLoadUtils.getInstance().getImageLoader(this).loadDrawableOrRaw(R.raw.pao).into(gifDrawbleImageView);
	
 3 从assets加载gif
        
	GifLoadUtils.getInstance().getImageLoader(this).loadAsset("maodazan.gif").into(gifAssetImageView);
     
  
# 如何使用

    allprojects {
		  repositories {
		  	maven { url 'https://www.jitpack.io' }
	  	}
  	}
    
    dependencies {
        compile 'com.github.zwf779375807:AndroidLoadGif:v1.0'
    }
    
    
 不要忘记添加权限，6.0以上版本动态申请权限
 
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
