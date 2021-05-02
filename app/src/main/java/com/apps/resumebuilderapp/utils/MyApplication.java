package com.apps.resumebuilderapp.utils;

import android.app.Application;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AudienceNetworkAds.initialize(this);
        AdSettings.addTestDevice("074b428e-28b1-4e98-a712-743f078a3079");
    }
}
