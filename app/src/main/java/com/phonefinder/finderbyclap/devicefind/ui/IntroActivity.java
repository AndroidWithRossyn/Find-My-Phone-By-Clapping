package com.phonefinder.finderbyclap.devicefind.ui;

import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIntroBinding binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(activity,Intro1Activity.class));
                });


            }
        });
    }
}