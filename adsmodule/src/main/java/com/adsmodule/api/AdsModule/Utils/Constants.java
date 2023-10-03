package com.adsmodule.api.AdsModule.Utils;


import com.adsmodule.api.AdsModule.Retrofit.AdsResponseModel;

import java.util.ArrayList;
import java.util.List;
/*http://192.168.0.183:8000/api/*/

public class Constants {
    public static final String IS_FIRST_RUN = "IS_FIRST_RUN";
    public static final String MAIN_BASE_URL = "https://www.api.ideadsdevspanel.com/api/";
    public static final String OPTIONAL_BASE_URL = "http://www.api.ideadsdevspanel.com/api/";
    public static final String TEST_BASE_URL = "http://192.168.0.176:8000/api/";
    public static final String PLAYSTORE_BASE = "http://play.google.com/store/apps/details?id=";
    public static final String ADMOB = "Admob";
    public static final String FACEBOOK = "Facebook";
    public static final String ADX = "Adx";
    public static int BACKPRESS_COUNT = 0;
    public static AdsResponseModel adsResponseModel = new AdsResponseModel();
    public static String IS_APP_OPEN_ADS = "app open ads";
    public static String BACKPRESS_AD_TYPE = "app open ads";
    public static int hitCounter = 0;
    public static List<String> platformList = new ArrayList<>();
}
