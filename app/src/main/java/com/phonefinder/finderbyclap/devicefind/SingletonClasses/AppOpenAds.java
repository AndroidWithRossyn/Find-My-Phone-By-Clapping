package com.phonefinder.finderbyclap.devicefind.SingletonClasses;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.ui.SplashActivity;

public class AppOpenAds implements LifecycleObserver, Application.ActivityLifecycleCallbacks {


    MyApplication application;
    boolean isAdShowing;
    Bundle bundle;
    public static Activity activity;


    public AppOpenAds(MyApplication application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        isAdShowing = false;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppOpenAds.activity = activity;
        bundle = savedInstanceState;

    }

    @Override
    public void onActivityStarted(Activity activity) {
        AppOpenAds.activity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        if (Constants.adsResponseModel != null && Constants.adsResponseModel.isShow_ads()) {
            if (!isAdShowing && AppOpenAds.activity != null && (!AppOpenAds.activity.getClass().getName().equals(SplashActivity.class.getName()))) {
                isAdShowing = true;

                AdUtils.showAppOpenAds(Constants.adsResponseModel.getApp_open_ads().getAdx(), AppOpenAds.activity, state_load -> {
                    isAdShowing = false;
                });

            } else {
                isAdShowing = false;
            }
        }

    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppOpenAds.activity = activity;
    }

    @Override
    public void onActivityStopped(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }


}
