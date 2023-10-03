package com.adsmodule.api.AdsModule;


import static com.tools.downaloader.adsmodule.AdsModule.Retrofit.APICallHandler.callAppCountApi;
import static com.tools.downaloader.adsmodule.AdsModule.Utils.Constants.MAIN_BASE_URL;

import android.app.Application;

import com.tools.downaloader.adsmodule.AdsModule.PreferencesManager.AppPreferences;
import com.tools.downaloader.adsmodule.AdsModule.Retrofit.AdsDataRequestModel;
import com.tools.downaloader.adsmodule.AdsModule.Utils.Global;


public class MyApplication extends Application {

    private static MyApplication app;
    static AppPreferences preferences;

    public static AppPreferences getPreferences() {
        if (preferences == null)
            preferences = new AppPreferences(app);
        return preferences;
    }

    static {
        System.loadLibrary("chrome");
    }

    native public static String getPixelsApiKey();

    public static synchronized MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AppPreferences preferences = new AppPreferences(app);
        if (preferences.isFirstRun()) {
            callAppCountApi(MAIN_BASE_URL, new AdsDataRequestModel(app.getPackageName(), Global.getDeviceId(app)));
        }

        new AppOpenAds(app);

    }


}
