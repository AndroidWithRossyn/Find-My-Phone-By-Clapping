package com.phonefinder.finderbyclap.devicefind.SingletonClasses;


import static com.adsmodule.api.AdsModule.Retrofit.APICallHandler.callAppCountApi;
import static com.adsmodule.api.AdsModule.Utils.Constants.MAIN_BASE_URL;

import android.app.Application;

import com.adsmodule.api.AdsModule.PreferencesManager.AppPreferences;
import com.adsmodule.api.AdsModule.Retrofit.AdsDataRequestModel;
import com.adsmodule.api.AdsModule.Utils.ConnectionDetector;
import com.adsmodule.api.AdsModule.Utils.Global;


public class MyApplication extends Application {

    private static MyApplication app;
    private static ConnectionDetector cd;
    static AppPreferences preferences;

    public static AppPreferences getPreferences() {
        if (preferences == null)
            preferences = new AppPreferences(app);
        return preferences;
    }

    public static ConnectionDetector getConnectionStatus(){
        if (cd == null){
            cd=new ConnectionDetector(app);
        }
        return cd;
    }


    public static synchronized MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        AppPreferences preferences = new AppPreferences(app);
        if (preferences.isFirstRun()) {
            callAppCountApi(MAIN_BASE_URL, new AdsDataRequestModel(app.getPackageName(), Global.getDeviceId(app)), () -> {
                preferences.setFirstRun(false);
            });
        }

        new AppOpenAds(app);

    }


}

