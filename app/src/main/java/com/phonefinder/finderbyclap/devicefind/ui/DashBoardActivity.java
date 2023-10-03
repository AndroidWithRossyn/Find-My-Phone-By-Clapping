package com.phonefinder.finderbyclap.devicefind.ui;

import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.R;
import com.phonefinder.finderbyclap.devicefind.databinding.ActivityDashBoardBinding;
import com.phonefinder.finderbyclap.devicefind.databinding.ExitLayoutBinding;
import com.phonefinder.finderbyclap.devicefind.fragment.FindFragment;
import com.phonefinder.finderbyclap.devicefind.fragment.SettingFragment;

public class DashBoardActivity extends AppCompatActivity {
    ActivityDashBoardBinding binding;
    int PERMISSIONGET = 1;
    String[] GETPERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };
    int valueOpenActivity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd1.findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.nativeAd0.findViewById(com.adsmodule.api.R.id.native_ad), 2, null);

        Drawable defaultBackground = new ColorDrawable(Color.TRANSPARENT);
        Drawable pressedBackground = getResources().getDrawable(R.drawable.back_checked);
        boolean b = isMyServiceRunning(DetectionServiceForeground.class);

        if (!b) {
            setPreference("startButton", "NO");
        }

        if (!checkPermissions(getApplicationContext(), GETPERMISSIONS)) {
            ActivityCompat.requestPermissions(DashBoardActivity.this, GETPERMISSIONS, PERMISSIONGET);
        }
        binding.navigationView.setItemIconTintList(ColorStateList.valueOf(Color.BLACK));
        binding.phoneFinderll.setBackground(defaultBackground);
        binding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawer.openDrawer(Gravity.LEFT);
            }
        });
        binding.phoneFinderll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.phoneFinderll.setBackground(pressedBackground);
                    binding.settingLl.setBackground(defaultBackground);
                    binding.referLl.setBackground(defaultBackground);
                    binding.privacyLl.setBackground(defaultBackground);
                    binding.privacyPolicyLl.setBackground(defaultBackground);
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    navigateToMainActivityWithFragment(FindFragment.class, R.drawable.find_selector);
                });

            }
        });
        binding.settingLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.settingLl.setBackground(pressedBackground);
                    binding.phoneFinderll.setBackground(defaultBackground);
                    binding.referLl.setBackground(defaultBackground);
                    binding.privacyLl.setBackground(defaultBackground);
                    binding.privacyPolicyLl.setBackground(defaultBackground);
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    startActivity(new Intent(DashBoardActivity.this,SettingActivity.class));
                });
            }
        });
        binding.referLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    binding.referLl.setBackground(pressedBackground);
                    binding.phoneFinderll.setBackground(defaultBackground);
                    binding.settingLl.setBackground(defaultBackground);
                    binding.privacyLl.setBackground(defaultBackground);
                    binding.privacyPolicyLl.setBackground(defaultBackground);
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    share();

            }
        });
        binding.privacyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.privacyLl.setBackground(pressedBackground);
                    binding.phoneFinderll.setBackground(defaultBackground);
                    binding.settingLl.setBackground(defaultBackground);
                    binding.referLl.setBackground(defaultBackground);
                    binding.privacyPolicyLl.setBackground(defaultBackground);
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    startActivity(new Intent(activity,NewPrivacyPolicyActivity.class));
                });

            }
        });
        binding.privacyPolicyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.privacyPolicyLl.setBackground(pressedBackground);
                    binding.phoneFinderll.setBackground(defaultBackground);
                    binding.settingLl.setBackground(defaultBackground);
                    binding.referLl.setBackground(defaultBackground);
                    binding.privacyLl.setBackground(defaultBackground);
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    startActivity(new Intent(activity,TermsConditionsActivity.class));
                });

            }
        });
        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(activity, MainActivity.class));
                });


            }
        });
        binding.settingScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(activity, SettingActivity.class));
                });
            }
        });
        binding.privacyScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    startActivity(new Intent(activity, NewPrivacyPolicyActivity.class));
                });
            }
        });
        binding.shareScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });
    }

    public static boolean checkPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void startCLick(View view) {


        if (ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
        ) {

            startActivity(new Intent(DashBoardActivity.this, MainActivity.class));
            finish();

        } else if (ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED
                ||
                ContextCompat.checkSelfPermission(DashBoardActivity.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED
        ) {
            new AlertDialog.Builder(DashBoardActivity.this).setMessage(R.string.permissionenabled).setPositiveButton(R.string.go_to_setting, (dialog, which) -> {
                // navigate to settings
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }).setNegativeButton(R.string.goback, (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.dismiss();
            }).show();
        } else {
            if (!checkPermissions(getApplicationContext(), GETPERMISSIONS)) {
                ActivityCompat.requestPermissions(DashBoardActivity.this, GETPERMISSIONS, PERMISSIONGET);

            }
        }


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private String PREFS_NAME = "PREFS";

    public boolean setPreference(String key, String value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    private void navigateToMainActivityWithFragment(Class<? extends Fragment> fragmentClass, int iconResourceId) {
        Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
        intent.putExtra("fragmentClass", fragmentClass.getName());
        intent.putExtra("iconResourceId", iconResourceId);
        startActivity(intent);
        finish();
    }
    private void share(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        String shareMessage = "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() + "\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Choose one"));
    }
    private void openCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DashBoardActivity.this);
        ExitLayoutBinding bind = ExitLayoutBinding.inflate(LayoutInflater.from(DashBoardActivity.this));
        builder.setView(bind.getRoot());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), bind.nativeAd2.findViewById(com.adsmodule.api.R.id.native_ad1), 1, null);
        bind.btnExit.setOnClickListener(v -> {
            dialog.dismiss();
            finishAffinity();
            System.exit(0);
        });
        bind.btnBack.setOnClickListener(v -> dialog.dismiss());

    }

    @Override
    public void onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START))
            binding.drawer.closeDrawer(GravityCompat.START);
        else {
            openCloseDialog();
        }
    }
}