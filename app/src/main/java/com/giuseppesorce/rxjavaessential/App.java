package com.giuseppesorce.rxjavaessential;

import android.app.Application;
import android.content.Context;

/**
 * Created by devandroid on 23/06/15.
 */
public class App extends Application{


    //public static DetailedAndroidLogger L;

    public static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

//        L = new DetailedAndroidLogger("RXJAVA", IAndroidLogger.LoggingLevel.DEBUG);
//
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .showImageOnFail(R.drawable.ic_launcher)
//                .showImageOnLoading(R.drawable.ic_launcher)
//                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .build();
//        ImageLoader.getInstance().init(config);
    }
}
