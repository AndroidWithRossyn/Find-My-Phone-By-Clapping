package com.adsmodule.api.AdsModule.Utils;

import com.tools.downaloader.adsmodule.AdsModule.Retrofit.AdsResponseModel;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String IS_FIRST_RUN = "IS_FIRST_RUN";
    public static final String MAIN_BASE_URL = "https://www.api.ideadsdevspanel.com/api/";
    public static final String OPTIONAL_BASE_URL = "http://www.api.ideadsdevspanel.com/api/";
    public static final String PLAYSTORE_BASE = "http://play.google.com/store/apps/details?id=";
    public static final String ADX = "adx";
        public static final String FACEBOOK = "FACEBOOK";
    public static AdsResponseModel adsResponseModel = new AdsResponseModel();
    public static String IS_APP_OPEN_ADS = "app open ads";

    /*    public static ArrayList<InterstitialAd> InterstitialList = new ArrayList<>();
        public static ArrayList<AppOpenAd> AppOpenAdsList = new ArrayList<>();
        public static ArrayList<NativeAd> NativeAdsList = new ArrayList<>();
        public static ArrayList<AdView> CollapsibleAdsList = new ArrayList<>();*/
    public static int hitCounter = 0;
    public static List<String> platformList = new ArrayList<>();
}
