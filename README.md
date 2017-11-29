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
   
       ImageView gifImageView = (ImageView) findViewById(R.id.gifimageview);
              GifLoadUtils.getInstance().getImageLoader(this).load("http://n1.itc.cn/img8/wb/recom/2016/08/12/147100143815015802.GIF").into(gifImageView);
  
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
