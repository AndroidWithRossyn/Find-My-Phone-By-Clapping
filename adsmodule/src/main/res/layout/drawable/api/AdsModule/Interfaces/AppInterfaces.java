package com.adsmodule.api.AdsModule.Interfaces;

import android.graphics.Bitmap;

import com.tools.downaloader.adsmodule.AdsModule.Retrofit.AdsResponseModel;

public class AppInterfaces {
    public interface AdDataInterface {
        void getAdData(AdsResponseModel adsResponseModel);
    }

    public interface InterstitialADInterface {
        void adLoadState(boolean isLoaded);
    }

    public interface FacebookInterface {
        void facebookAdState(boolean isLoaded);
    }

    public interface AppOpenADInterface {
        void appOpenAdState(boolean state_load);
    }

    public interface WebViewInterface {
        void getClickedURL(String URL);

        void getBitmap(Bitmap imageMap);
    }

    public interface AdapterClickInterface {
        void onClick(int position);
    }

    public interface CollapsibleAdInterface {
        void setAdState(boolean adState); //true=ad_open, false=ad_closed
    }

    public interface AdapterClick {
        void clickedPosition(int position);
    }

    public interface ApiInterface {
        void getEmptyInterface();
    }

    public interface RewardedAd {
        void rewardState(boolean adState);
    }
}
