package com.phonefinder.finderbyclap.devicefind.ui;

import static com.phonefinder.finderbyclap.devicefind.SingletonClasses.AppOpenAds.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.phonefinder.finderbyclap.devicefind.R;
import com.phonefinder.finderbyclap.devicefind.databinding.ActivityMainBinding;
import com.phonefinder.finderbyclap.devicefind.databinding.ExitLayoutBinding;
import com.phonefinder.finderbyclap.devicefind.fragment.FindFragment;
import com.phonefinder.finderbyclap.devicefind.fragment.SettingFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public Intent intent;
    List<Intent> POWERMANAGER_INTENTS = new ArrayList<Intent>();
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;
    public static String ID = "001";
    public static Notification notification;
    public static NotificationManager notificationManager;

    String b, b2;
    private String PREFS_NAME = "PREFS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Drawable defaultBackground = new ColorDrawable(Color.TRANSPARENT);
        Drawable pressedBackground = getResources().getDrawable(R.drawable.back_checked);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(
                R.id.fragment_container, new FindFragment()
        ).commit();
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
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new FindFragment());
                    fragmentTransaction.commit();
                  /*  binding.find.setImageDrawable(getResources().getDrawable(R.drawable.find_icon));
                    binding.findTxt.setTextColor(getResources().getColor(R.color.app_color));
                    binding.settings.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                    binding.settingTxt.setTextColor(getResources().getColor(R.color.gray_light));*/
                    binding.drawer.closeDrawer(Gravity.LEFT);
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
                   /* binding.settings.setImageDrawable(getResources().getDrawable(R.drawable.settings_checked));
                    binding.settingTxt.setTextColor(getResources().getColor(R.color.app_color));
                    binding.find.setImageDrawable(getResources().getDrawable(R.drawable.find_unchecked));
                    binding.findTxt.setTextColor(getResources().getColor(R.color.gray_light));*/
                    binding.drawer.closeDrawer(Gravity.LEFT);
                    startActivity(new Intent(MainActivity.this,SettingActivity.class));
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
                binding.privacyLl.setBackground(pressedBackground);
                binding.phoneFinderll.setBackground(defaultBackground);
                binding.settingLl.setBackground(defaultBackground);
                binding.referLl.setBackground(defaultBackground);
                binding.privacyPolicyLl.setBackground(defaultBackground);
                binding.drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(MainActivity.this,NewPrivacyPolicyActivity.class));
            }
        });
        binding.privacyPolicyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.privacyPolicyLl.setBackground(pressedBackground);
                binding.phoneFinderll.setBackground(defaultBackground);
                binding.settingLl.setBackground(defaultBackground);
                binding.referLl.setBackground(defaultBackground);
                binding.privacyLl.setBackground(defaultBackground);
                binding.drawer.closeDrawer(Gravity.LEFT);
                startActivity(new Intent(MainActivity.this,TermsConditionsActivity.class));
            }
        });
        binding.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.find.setImageDrawable(getResources().getDrawable(R.drawable.find_icon));
                    binding.settings.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new FindFragment());
                    fragmentTransaction.commit();
                });

            }
        });
        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    binding.find.setImageDrawable(getResources().getDrawable(R.drawable.find_unchecked));
                    binding.settings.setImageDrawable(getResources().getDrawable(R.drawable.settings_checked));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new SettingFragment());
                    fragmentTransaction.commit();
                });

            }
        });

        createChannel();
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.letv.android.letvsafe",
                        "com.letv.android.letvsafe.AutobootManageActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.process.ProtectActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                )
        ));

        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.startupapp.StartupAppListActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.oppo.safe",
                        "com.oppo.safe.permission.startup.StartupAppListActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.samsung.android.lool",
                        "com.samsung.android.sm.ui.battery.BatteryActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.htc.pitroad",
                        "com.htc.pitroad.landingpage.activity.LandingPageActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.asus.mobilemanager",
                        "com.asus.mobilemanager.MainActivity"
                )
        ));
        POWERMANAGER_INTENTS.add(new Intent().setComponent(
                new ComponentName(
                        "com.transsion.phonemanager",
                        "com.itel.autobootmanager.activity.AutoBootMgrActivity"
                )
        ));

        b = getPreference("battery");

        if (!b.equals("NO")) {
            checkPermission();
        }

        if (!Build.BRAND.equalsIgnoreCase("oppo")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);

                b2 = getPreference("switchBattery");


                if (!b2.equals("YES")) {

                    if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())) {


                        for (Intent intent : POWERMANAGER_INTENTS) {
                            if (getPackageManager().resolveActivity(
                                    intent, PackageManager.MATCH_DEFAULT_ONLY
                            ) != null
                            ) {
                                startActivity(intent);
                                setPreference("switchBattery", "YES");
                                break;

                            }

                        }
                    }
                }

            }

        }
        intent = new Intent(this, DetectionServiceForeground.class);

    }





    private void createChannel() {
        if (Build.VERSION.SDK_INT >= 26) {

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(ID, "Test Channel 1", NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setDescription("xyz");
            notificationChannel.enableLights(true);
            notificationChannel.setShowBadge(true);
            notificationManager.createNotificationChannel(notificationChannel);
            return;
        }
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.Q)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission();
            } else {
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putBoolean("switch", true);
                editor.apply();
                setPreference("battery", "NO");
            }
        }

    }


    @Override
    public void onBackPressed(){
        AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), new AppInterfaces.AppOpenADInterface() {
            @Override
            public void appOpenAdState(boolean state_load) {
                MainActivity.super.onBackPressed();
            }
        });
    }

    public  boolean setPreference( String key, String value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public String getPreference(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return settings.getString(key, "true");
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Check for touch event (ACTION_DOWN) and send broadcast to stop functionalities
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Intent stopIntent = new Intent(DetectionServiceForeground.ACTION_STOP_FUNCTIONALITIES);
            sendBroadcast(stopIntent);
        }
        return super.onTouchEvent(event);
    }


}