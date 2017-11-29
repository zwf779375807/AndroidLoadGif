# AndroidLoadGif
Android加载gif图片，使用retrofit+android_gif_drawable结合使用三级缓存方式加载gif动图

  ![img](https://github.com/zwf779375807/AndroidLoadGif/blob/master/app/gifload.gif)
  
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
