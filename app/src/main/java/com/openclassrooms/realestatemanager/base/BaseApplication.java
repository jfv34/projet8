package com.openclassrooms.realestatemanager.base;

import android.app.Application;
import android.content.Context;

import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.soloader.SoLoader;
import com.openclassrooms.realestatemanager.BuildConfig;

public class BaseApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        BaseApplication.context = getApplicationContext();

        SoLoader.init(this, false);

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            final FlipperClient client = AndroidFlipperClient.getInstance(this);
            client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));
            client.addPlugin(new DatabasesFlipperPlugin(this));
            client.start();
        }
    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }
}