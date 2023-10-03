package com.phonefinder.finderbyclap.devicefind.ui;

import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.R;
import com.phonefinder.finderbyclap.devicefind.databinding.ActivityTermsOfUseBinding;

public class TermsOfUseActivity extends AppCompatActivity {
    ActivityTermsOfUseBinding binding;
    int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_of_use);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        type = getIntent().getIntExtra("type", 0);
//        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0, 2, null);
        binding.privacyPolicyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(activity, NewPrivacyPolicyActivity.class));
                });

            }
        });

        binding.termsConditionsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(activity, TermsConditionsActivity.class));
                });
            }
        });

        binding.tvText.setOnClickListener(view -> mStartAct());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void mStartAct() {
        AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
            startActivity(new Intent(activity, DashBoardActivity.class));
            finish();
        });
    }
}