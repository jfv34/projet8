package com.openclassrooms.realestatemanager.base;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.soloader.SoLoader;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.database.PropertyDataBase;
import com.openclassrooms.realestatemanager.models.Property;

public class BaseApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        BaseApplication.context = getApplicationContext();

        SoLoader.init(this, false);

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            final FlipperClient client = AndroidFlipperClient.getInstance(this);
            client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));
            client.addPlugin(new DatabasesFlipperPlugin(context));
            client.start();

        }

        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {
                PropertyDataBase.getInstance(BaseApplication.this).propertyDao().insertProperty(
                        new Property(
                                1,
                                "Inserted",
                                "NY",
                                17250259,
                                "Description",
                                null
                        )
                );
                return null;
            }
        };
    }

    public static Context getAppContext() {
        return BaseApplication.context;
    }
}