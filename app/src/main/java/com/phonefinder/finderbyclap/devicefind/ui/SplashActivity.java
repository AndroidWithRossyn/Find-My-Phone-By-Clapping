package com.phonefinder.finderbyclap.devicefind.ui;

import static com.adsmodule.api.AdsModule.Retrofit.APICallHandler.callAdsApi;
import static com.adsmodule.api.AdsModule.Utils.Global.checkAppVersion;
import static com.adsmodule.api.AdsModule.Utils.Global.isNull;
import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;
import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.MyApplication.getConnectionStatus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Retrofit.AdsDataRequestModel;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.adsmodule.api.AdsModule.Utils.Global;
import com.phonefinder.finderbyclap.devicefind.R;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getConnectionStatus().isConnectingToInternet()) {
            callAdsApi(Constants.MAIN_BASE_URL, new AdsDataRequestModel(this.getPackageName(), ""), adsResponseModel -> {
                if (adsResponseModel != null) {
                    Constants.adsResponseModel = adsResponseModel;
                    if (!isNull(Constants.adsResponseModel.getMonetize_platform()))
                        Constants.platformList = Arrays.asList(Constants.adsResponseModel.getMonetize_platform().split(","));
                    if (checkAppVersion(adsResponseModel.getVersion_name(), activity)) {
                        Global.showUpdateAppDialog(activity);
                    } else {
                        AdUtils.buildAppOpenAdCache(activity, Constants.adsResponseModel.getApp_open_ads().getAdx());
                        AdUtils.buildNativeCache(Constants.adsResponseModel.getNative_ads().getAdx(), activity);
                        AdUtils.buildInterstitialAdCache(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity);
                        AdUtils.buildRewardAdCache(Constants.adsResponseModel.getRewarded_ads().getAdx(), activity);
                        AdUtils.showAppOpenAds(Constants.adsResponseModel.getApp_open_ads().getAdx(), activity, state_load -> {
                            openActivity();
                        });
                    }
                }

            });
        } else {
            openActivity();
        }

    }

    private void openActivity() {

        new Handler().postDelayed(() -> {
            boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .getBoolean("isFirstRun", true);

            if (isFirstRun) {
                startActivity(new Intent(SplashActivity.this, IntroActivity.class));
            } else {
                gotoMainActivity();
            }

            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();


        }, 2000);


    }

    public void gotoMainActivity() {
        startActivity(new Intent(this, DashBoardActivity.class));
        finish();
    }
}