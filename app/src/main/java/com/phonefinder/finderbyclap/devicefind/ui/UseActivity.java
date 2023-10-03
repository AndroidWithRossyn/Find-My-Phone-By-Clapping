package com.phonefinder.finderbyclap.devicefind.ui;

import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.databinding.ActivityUseBinding;

public class UseActivity extends AppCompatActivity {
    ActivityUseBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAdSmall.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAdBig.findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);

        binding.view1.setBackground(new DashedLineDrawable(this));
        binding.view2.setBackground(new DashedLineDrawable(this));
        binding.view3.setBackground(new DashedLineDrawable(this));
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
                    @Override
                    public void appOpenAdState(boolean state_load) {
                        UseActivity.super.onBackPressed();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                UseActivity.super.onBackPressed();
            }
        });
    }
}