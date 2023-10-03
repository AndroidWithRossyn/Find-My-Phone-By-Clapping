package com.phonefinder.finderbyclap.devicefind.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.R;
import com.phonefinder.finderbyclap.devicefind.ui.DetectionServiceForeground;
import com.phonefinder.finderbyclap.devicefind.ui.UseActivity;

public class FindFragment extends Fragment {
    TextView use, tap;
    ImageView checkbox;
    private String PREFS_NAME = "PREFS";
    LinearLayout native_ad_large;

    public boolean setPreference(String key, String value) {
        SharedPreferences settings = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getPreference(String key) {
        SharedPreferences settings = requireActivity().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return settings.getString(key, "true");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        native_ad_large=view.findViewById(R.id.native_ad_large);
        AdUtils.showNativeAd(requireActivity(), Constants.adsResponseModel.getNative_ads().getAdx(),native_ad_large.findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        use = view.findViewById(R.id.use);
        tap = view.findViewById(R.id.tap);
        checkbox = view.findViewById(R.id.enable_disable);
        checkbox.setBackground(getResources().getDrawable(R.drawable.tap_unchecked));

        use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(requireActivity(), UseActivity.class));
                });
            }
        });

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPreference("startButton").equals("NO")) {
                    tap.setText("Tap to Inactive");
                    checkbox.setBackground(getResources().getDrawable(R.drawable.tap_checked));
                    setPreference("startButton", "YES");

                    if (Build.VERSION.SDK_INT >= 26) {
                        requireActivity().startForegroundService(new Intent(getContext(), DetectionServiceForeground.class));
                    } else {
                        requireActivity().startService(new Intent(getContext(), DetectionServiceForeground.class));
                    }
                } else {
                    tap.setText("Tap to Active");
                    setPreference("startButton", "NO");
                    checkbox.setBackground(getResources().getDrawable(R.drawable.tap_unchecked));
                    requireActivity().stopService(new Intent(getContext(), DetectionServiceForeground.class));
                }
            }
        });

        // Set UI state based on the 'startButton' preference value
        boolean isServiceActive = getPreference("startButton").equals("YES");
        if (isServiceActive) {
            tap.setText("Tap to Inactive");
            checkbox.setBackground(getResources().getDrawable(R.drawable.tap_checked));
        } else {
            tap.setText("Tap to Active");
            checkbox.setBackground(getResources().getDrawable(R.drawable.tap_unchecked));
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Update UI state based on the current service status
        boolean isServiceActive = getPreference("startButton").equals("YES");
        if (isServiceActive) {
            tap.setText("Tap to Inactive");
            checkbox.setBackground(getResources().getDrawable(R.drawable.tap_checked));
        } else {
            tap.setText("Tap to Active");
            checkbox.setBackground(getResources().getDrawable(R.drawable.tap_unchecked));
        }
    }
}
