package com.jdkgroup.baseclasses;

import android.app.Application;

import com.jdkgroup.weather.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends Application {

    private static BaseApplication baseApplication = null;

    public static BaseApplication getBaseApplication() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/aileron_regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        //FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
