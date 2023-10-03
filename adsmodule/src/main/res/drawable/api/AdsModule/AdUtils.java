package com.adsmodule.api.AdsModule;

import static com.tools.downaloader.adsmodule.AdsModule.Utils.Global.isClassNull;
import static com.tools.downaloader.adsmodule.AdsModule.Utils.Global.isListNull;
import static com.tools.downaloader.adsmodule.AdsModule.Utils.Global.isNull;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.tools.downaloader.adsmodule.AdsModule.Interfaces.AppInterfaces;
import com.tools.downaloader.adsmodule.AdsModule.Utils.ConnectionDetector;
import com.tools.downaloader.adsmodule.AdsModule.Utils.Constants;
import com.tools.downaloader.adsmodule.AdsModule.Utils.Global;
import com.tools.downaloader.adsmodule.AdsModule.Utils.StringUtils;
import com.tools.downaloader.adsmodule.R;

import java.util.ArrayList;
import java.util.List;


public class AdUtils {

    private static ConnectionDetector cd;
    private static AppOpenAd appOpenAd;
    private static NativeAd google_unifiedNativeAd;
    /*TODO AD COUNTERS----------------------------------------------*/
    private static int interstitialHitCounter = 0;
    private static int nativeHitCounter = 0;
    private static int appOpenHitCounter = 0;
    private static int rewardAdHitCounter = 0;
    private static int bannerAdHitCounter = 0;
    private static int collapsibleAdHitCounter = 0;
    /*TODO AD COUNTERS---------------------------------------------- */

    /*TODO PRECACHE ADS---------------------------------------------- */
    private static InterstitialAd precacheInterstitialAd;
    private static NativeAd precacheNativeAd;
    private static AppOpenAd precacheAppOpenAd;
    private static RewardedAd precacheRewardAd;
    /*TODO PRECACHE ADS---------------------------------------------- */

    //Previous ads list loader ----------------------------------------------------------------------------------------------------------------------------------

    /*  public static void loadInitialNativeList(Activity activity) {
        int i = 0;
        while (i < 1) {
            cd = new ConnectionDetector(activity);
            if (Constants.adsResponseModel != null && cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
                if (Constants.NativeAdsList != null && !Global.isNull(Constants.adsResponseModel.getNative_ads()) && Constants.NativeAdsList.size() < 10) {
                    try {
                        AdLoader adLoader = new AdLoader.Builder(activity, Constants.adsResponseModel.getNative_ads()).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                Constants.NativeAdsList.add(nativeAd);
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {

                            }
                        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
                        adLoader.loadAd(new AdRequest.Builder().build());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            i++;
        }

    }*/

    /*   private static void loadInterstitialAd(Activity activity, AppInterfaces.InterstitialADInterface interstitialADInterface) {
        if (Constants.adsResponseModel.isShow_ads()) {
            if (!Global.isArrayListNull(Constants.InterstitialList) && Constants.InterstitialList.size() > 0) {
                Constants.InterstitialList.get(0).setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialADInterface.adLoadState(true);
                        Constants.InterstitialList.remove(0);
                        loadInitialInterstitialAds(activity);

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        interstitialADInterface.adLoadState(false);
                        Constants.InterstitialList.remove(0);
                        loadInitialInterstitialAds(activity);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }
                });
                Constants.InterstitialList.get(0).show(activity);
            } else {
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(activity, Constants.adsResponseModel.getInterstitial_ads(), adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Constants.InterstitialList.add(interstitialAd);
                        showInterstitialAd(activity, interstitialADInterface);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialADInterface.adLoadState(false);
                        loadInitialInterstitialAds(activity);
                    }
                });
            }
        } else {
            interstitialADInterface.adLoadState(false);
        }
    }*/

    /*  public static void loadAppOpenAds(Context context) {
        try {
            int i = 0;
            while (i < 3) {
                Global.sout("App open ads list size before >>>>", Constants.AppOpenAdsList.size());
                if (Constants.AppOpenAdsList.size() <= 4 && Constants.adsResponseModel != null && !Global.isNull(Constants.adsResponseModel.getApp_open_ads())) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    AppOpenAd.load(context, Constants.adsResponseModel.getApp_open_ads(), adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {

                        @Override
                        public void onAdLoaded(@NonNull AppOpenAd ad) {
                            Constants.AppOpenAdsList.add(ad);
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        }

                    });
                } else {
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            Global.sout("AppOpenAds list exception", e.getLocalizedMessage());
        }
    }*/

    /*  public static void loadInitialInterstitialAds(Context context) {
        try {
            int i = 0;
            while (i < 3) {
                Global.sout("Interstitial ads list size before >>>>", Constants.InterstitialList.size());
                if (Constants.InterstitialList.size() <= 4 && Constants.adsResponseModel != null && !Global.isNull(Constants.adsResponseModel.getInterstitial_ads())) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    InterstitialAd.load(context, Constants.adsResponseModel.getInterstitial_ads(), adRequest, new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            Constants.InterstitialList.add(interstitialAd);
                            Global.sout("Interstitial ads list size after >>>>", Constants.InterstitialList.size());
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        }
                    });
                } else {
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            Global.sout("interstitial list exception", e.getLocalizedMessage());
        }

    }*/

    /*public static void loadCollapsibleAdsList(Activity activity) {
        ConnectionDetector cd = new ConnectionDetector(activity);
        if (Constants.adsResponseModel != null && !Global.isNull(Constants.adsResponseModel.getCollapsible_ads()) && Constants.adsResponseModel.isShow_ads() && cd.isConnectingToInternet()) {
            int i = 0;
            while (i < 3) {
                AdView adView = new AdView(activity);
                AdSize customAdSize2 = new AdSize(AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT);
                adView.setAdSize(customAdSize2);
                adView.setAdUnitId(Constants.adsResponseModel.getCollapsible_ads());
                Bundle extras = new Bundle();
                extras.putString("collapsible", "top");
                AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
                adView.loadAd(adRequest);
                i++;

            }
        }
    }
*/

    //Previous ads list loader ----------------------------------------------------------------------------------------------------------------------------------


    // TODO INTERSTITIAL AD IMPLEMENTATION--------------------------------------------------------------------------------------------------------------
    public static void buildInterstitialAdCache(String interstitialAdId, Activity activity) {
        precacheInterstitialAd = null;
        cd = new ConnectionDetector(activity);
        if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads() && !isClassNull(Constants.adsResponseModel.getInterstitial_ads())) {
            if (Constants.hitCounter == Constants.adsResponseModel.getAds_count()) {
                Constants.hitCounter = 0;
                AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(activity, interstitialAdId, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        precacheInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        if (!isListNull(Constants.platformList)) {
                            switch (interstitialHitCounter) {
                                case 0:
                                    interstitialHitCounter++;
                                    if (Constants.platformList.contains(Constants.ADX))
                                        buildInterstitialAdCache(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity);
                                    break;
                                case 1:
                                    interstitialHitCounter++;
                                    if (Constants.platformList.contains(Constants.FACEBOOK))
                                        buildInterstitialAdCache(Constants.adsResponseModel.getInterstitial_ads().getFacebook(), activity);
                                    break;
                                case 2:
                                    interstitialHitCounter = 0;
                                    break;
                            }
                        } else {
                            interstitialHitCounter = 0;
                        }

                    }
                });
            }
        }
    }

    public static void showInterstitialAd(String interstitialAd, Activity activity, AppInterfaces.InterstitialADInterface interstitialADInterface) {
        cd = new ConnectionDetector(activity);
        if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads() && !isClassNull(Constants.adsResponseModel.getInterstitial_ads())) {
            if (Constants.hitCounter == Constants.adsResponseModel.getAds_count() && isNull(interstitialAd)) {
                Constants.hitCounter = 0;
                Global.showAlertProgressDialog(activity);
                AdRequest adRequest = new AdRequest.Builder().build();
                if (precacheInterstitialAd != null) {
                    InterstitialAd.load(activity, interstitialAd, adRequest, new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            Global.hideAlertProgressDialog();
                            loadInterstitialAd(activity, interstitialADInterface, interstitialAd);
                        }

                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            if (isListNull(Constants.platformList)) {
                                switch (interstitialHitCounter) {
                                    case 0:
                                        interstitialHitCounter++;
                                        if (Constants.platformList.contains(Constants.ADX))
                                            showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, interstitialADInterface);
                                        break;
                                    case 1:
                                        interstitialHitCounter++;
                                        if (Constants.platformList.contains(Constants.FACEBOOK))
                                            showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getFacebook(), activity, interstitialADInterface);
                                        break;
                                    case 2:
                                        interstitialHitCounter = 0;
                                        Global.hideAlertProgressDialog();
                                        interstitialADInterface.adLoadState(false);
                                        break;
                                }
                            } else {
                                interstitialHitCounter = 0;
                                Global.hideAlertProgressDialog();
                                interstitialADInterface.adLoadState(false);
                            }
                        }
                    });
                } else {
                    loadInterstitialAd(activity, interstitialADInterface, precacheInterstitialAd);
                }

            } else {
                interstitialADInterface.adLoadState(false);
                Constants.hitCounter++;
            }
        } else {
            Global.hideAlertProgressDialog();
            interstitialADInterface.adLoadState(false);
        }
    }

    private static void loadInterstitialAd(Activity activity, AppInterfaces.InterstitialADInterface interStitialADInterface, InterstitialAd interstitialAd) {
        if (interstitialAd != null) {
            InterstitialAd iAds = interstitialAd;
            iAds.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    interStitialADInterface.adLoadState(true);
                    buildInterstitialAdCache(Constants.adsResponseModel.getInterstitial_ads().getAdmob(), activity);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    interStitialADInterface.adLoadState(false);
                    buildInterstitialAdCache(Constants.adsResponseModel.getInterstitial_ads().getAdmob(), activity);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                }
            });
            iAds.show(activity);
        } else {
            interStitialADInterface.adLoadState(false);
        }
    }
    // TODO INTERSTITIAL AD IMPLEMENTATION--------------------------------------------------------------------------------------------------------------


    // TODO NATIVE AD IMPLEMENTATION--------------------------------------------------------------------------------------------------------------
    public static void buildNativeCache(String nativeAd, Activity activity) {
        precacheNativeAd = null;
        if (!isNull(nativeAd)) {
            if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
                try {
                    AdLoader adLoader = new AdLoader.Builder(activity, nativeAd).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            precacheNativeAd = nativeAd;
                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Global.sout("Native failed for ad>>>>>>>>>>>>>>>>>" + nativeHitCounter, adError.toString());
                            if (isListNull(Constants.platformList)) {
                                switch (nativeHitCounter) {
                                    case 0:
                                        nativeHitCounter++;
                                        if (Constants.platformList.contains(Constants.ADX))
                                            buildNativeCache(Constants.adsResponseModel.getNative_ads().getAdx(), activity);
                                        break;
                                    case 1:
                                        nativeHitCounter++;
                                        break;
                                }
                            }

                        }
                    }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
                    adLoader.loadAd(new AdRequest.Builder().build());
                } catch (Exception e) {
                    Global.sout("native ads exception>>", e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }


    }

    public static void showNativeAd(Activity activity, String nativeAd, LinearLayout adContainer, int isFullScreenAd, Drawable imageID) {
        cd = new ConnectionDetector(activity);
        if (imageID != null) {
            adContainer.addView(Global.getDefaultImage(activity, imageID));
        }
        if (!isNull(nativeAd)) {
            if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
                try {
                    if (precacheNativeAd != null) {
                        try {
                            NativeAdView unifiedNativeAdView;
                            if (isFullScreenAd == 1) {
                                unifiedNativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.full_native_ad_param, null);
                            } else if (isFullScreenAd == 2) {
                                unifiedNativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.small_native_ad, null);
                            } else {
                                unifiedNativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.small_native_ad1, null);
                            }
                            unifiedNativeAdView.isHardwareAccelerated();
                            populateUnifiedNativeAdView(activity, google_unifiedNativeAd, unifiedNativeAdView, isFullScreenAd);
                            adContainer.removeAllViews();
                            adContainer.addView(unifiedNativeAdView);
                            adContainer.setVisibility(View.VISIBLE);
                            buildNativeCache(Constants.adsResponseModel.getNative_ads().getAdmob(), activity);
                        } catch (Exception e2) {
                            buildNativeCache(Constants.adsResponseModel.getNative_ads().getAdmob(), activity);
                            e2.printStackTrace();
                        }

                    } else {
                        AdLoader adLoader = new AdLoader.Builder(activity, nativeAd).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                            @Override
                            public void onNativeAdLoaded(NativeAd nativeAd) {
                                google_unifiedNativeAd = nativeAd;
                                NativeAdView unifiedNativeAdView;
                                try {
                                    if (isFullScreenAd == 1) {
                                        unifiedNativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.full_native_ad_param, null);
                                    } else if (isFullScreenAd == 2) {
                                        unifiedNativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.small_native_ad, null);
                                    } else {
                                        unifiedNativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.small_native_ad1, null);
                                    }
                                    unifiedNativeAdView.isHardwareAccelerated();
                                    populateUnifiedNativeAdView(activity, google_unifiedNativeAd, unifiedNativeAdView, isFullScreenAd);

                                    adContainer.removeAllViews();
                                    adContainer.addView(unifiedNativeAdView);
                                    adContainer.setVisibility(View.VISIBLE);

                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            }
                        }).withAdListener(new AdListener() {
                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {
                                Global.sout("Native failed for ad>>>>>>>>>>>>>>>>>" + nativeHitCounter, adError.toString());
                                if (isListNull(Constants.platformList)) {
                                    switch (nativeHitCounter) {
                                        case 0:
                                            nativeHitCounter++;
                                            if (Constants.platformList.contains(Constants.ADX))
                                                showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), adContainer, isFullScreenAd, imageID);
                                            break;
                                        case 1:
                                            nativeHitCounter++;
                                            if (Constants.platformList.contains(Constants.FACEBOOK))
                                                showFacebookNativeAd(activity, Constants.adsResponseModel.getNative_ads().getFacebook(), adContainer, new AppInterfaces.FacebookInterface() {
                                                    @Override
                                                    public void facebookAdState(boolean isLoaded) {
                                                        if (!isLoaded) {
                                                            try {
                                                                if (imageID != null) {
                                                                    adContainer.removeAllViews();
                                                                    adContainer.addView(Global.getDefaultImage(activity, imageID));
                                                                }
                                                                return;
                                                            } catch (Exception e) {
                                                                Global.sout("facebook native ads exception>>", e.getLocalizedMessage());
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                });
                                            break;
                                    }
                                }

                            }
                        }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
                        adLoader.loadAd(new AdRequest.Builder().build());
                    }
                } catch (Exception e) {
                    Global.sout("native ads exception>>", e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        } else {
            adContainer.setVisibility(View.GONE);
        }
    }

    private static void populateUnifiedNativeAdView(Activity activity, NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView, int flag) {
        if (flag == 1) {
            unifiedNativeAdView.setMediaView((MediaView) unifiedNativeAdView.findViewById(R.id.ad_media));
        }
        unifiedNativeAdView.setHeadlineView(unifiedNativeAdView.findViewById(R.id.ad_headline));
        unifiedNativeAdView.setBodyView(unifiedNativeAdView.findViewById(R.id.ad_body));
        unifiedNativeAdView.setCallToActionView(unifiedNativeAdView.findViewById(R.id.ad_call_to_action));
        unifiedNativeAdView.setIconView(unifiedNativeAdView.findViewById(R.id.ad_app_icon));
        unifiedNativeAdView.setPriceView(unifiedNativeAdView.findViewById(R.id.ad_price));
        unifiedNativeAdView.setStarRatingView(unifiedNativeAdView.findViewById(R.id.ad_stars));
        unifiedNativeAdView.setStoreView(unifiedNativeAdView.findViewById(R.id.ad_store));
        unifiedNativeAdView.setAdvertiserView(unifiedNativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) unifiedNativeAdView.getHeadlineView()).setText(unifiedNativeAd.getHeadline());
        if (unifiedNativeAd.getBody() == null) {
            unifiedNativeAdView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getBodyView()).setText(unifiedNativeAd.getBody());
        }
        if (unifiedNativeAd.getCallToAction() == null) {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) unifiedNativeAdView.getCallToActionView()).setText(unifiedNativeAd.getCallToAction());
        }
        if (unifiedNativeAd.getIcon() == null) {
            unifiedNativeAdView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) unifiedNativeAdView.getIconView()).setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            unifiedNativeAdView.getIconView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getPrice() == null) {
            unifiedNativeAdView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getPriceView()).setText(unifiedNativeAd.getPrice());
        }
        if (unifiedNativeAd.getStore() == null) {
            unifiedNativeAdView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            unifiedNativeAdView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) unifiedNativeAdView.getStoreView()).setText(unifiedNativeAd.getStore());
        }
        if (unifiedNativeAd.getStarRating() == null) {
            unifiedNativeAdView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) unifiedNativeAdView.getStarRatingView()).setRating(unifiedNativeAd.getStarRating().floatValue());
            unifiedNativeAdView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (unifiedNativeAd.getAdvertiser() == null) {
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) unifiedNativeAdView.getAdvertiserView()).setText(unifiedNativeAd.getAdvertiser());
            unifiedNativeAdView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        unifiedNativeAdView.setNativeAd(unifiedNativeAd);

    }

    private static void showFacebookNativeAd(Activity activity, String facebookAdId, LinearLayout adContainer, AppInterfaces.FacebookInterface facebookInterface) {
        NativeBannerAd facebookAd = new NativeBannerAd(activity, facebookAdId);
        facebookAd.loadAd(facebookAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                inflateFaceBookAd(activity, facebookAd, adContainer);
                facebookInterface.facebookAdState(true);
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                facebookInterface.facebookAdState(false);
            }

            @Override
            public void onAdLoaded(Ad ad) {

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());
    }

    private static void inflateFaceBookAd(Activity activity, NativeBannerAd facebookAd, LinearLayout adContainer) {
        adContainer.removeAllViews();
        adContainer.setVisibility(View.VISIBLE);
        facebookAd.unregisterView();

        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.ads_nb_fb, null);
        adContainer.addView(view);
        NativeAdLayout nativeAdLayout = view.findViewById(R.id.nativview);
        LinearLayout adChoicesContainer = view.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, facebookAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        TextView nativeAdCallToAction = view.findViewById(R.id.nb_ad_call_to_action);

        TextView nativeAdTitle = view.findViewById(R.id.native_ad_title);
        TextView nativeAdSocialContext = view.findViewById(R.id.native_ad_social_context);
        com.facebook.ads.MediaView nativeAdIconView = view.findViewById(R.id.native_icon_view);
        nativeAdCallToAction.setText(facebookAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(facebookAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdTitle.setText(facebookAd.getAdvertiserName());
        nativeAdSocialContext.setText(facebookAd.getAdBodyText());

        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        clickableViews.add(nativeAdIconView);
        clickableViews.add(nativeAdSocialContext);
        facebookAd.registerViewForInteraction(view, nativeAdIconView, clickableViews);

    }
    // TODO NATIVE AD IMPLEMENTATION--------------------------------------------------------------------------------------------------------------

    //TODO APPOPEN ADS--------------------------------------------------------------------------------------------------------------
    public static void buildAppOpenAdCache(Activity activity, String appOpenAdId) {
        precacheAppOpenAd = null;
        ConnectionDetector cd = new ConnectionDetector(activity);
        if (Constants.adsResponseModel != null && cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
            if (StringUtils.CheckEqualIgnoreCase(Constants.adsResponseModel.getAds_open_type(), Constants.IS_APP_OPEN_ADS)) {
                AdRequest adRequest = new AdRequest.Builder().build();
                AppOpenAd.load(activity, appOpenAdId, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {

                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        precacheAppOpenAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        if (isListNull(Constants.platformList)) {
                            switch (appOpenHitCounter) {
                                case 0:
                                    appOpenHitCounter++;
                                    if (Constants.platformList.contains(Constants.ADX))
                                        buildAppOpenAdCache(activity, Constants.adsResponseModel.getApp_open_ads().getAdx());
                                    break;
                                case 1:
                                    appOpenHitCounter++;
                                    if (Constants.platformList.contains(Constants.FACEBOOK))
                                        buildAppOpenAdCache(activity, Constants.adsResponseModel.getApp_open_ads().getFacebook());
                                    break;
                                case 2:
                                    appOpenHitCounter = 0;
                                    break;
                            }
                        }

                        Global.printLog("adsLoading Failed ==", loadAdError.getMessage());
                        // Handle the error.
                    }

                });
            } else {
                buildInterstitialAdCache(Constants.adsResponseModel.getInterstitial_ads().getAdmob(), activity);
            }
        }

    }

    public static void showAppOpenAds(String appopenAd, Activity activity, AppInterfaces.AppOpenADInterface appOpenADInterface) {
        ConnectionDetector cd = new ConnectionDetector(activity);
        if (Constants.adsResponseModel != null && cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
            if (StringUtils.CheckEqualIgnoreCase(Constants.adsResponseModel.getAds_open_type(), Constants.IS_APP_OPEN_ADS)) {
                Global.showAlertProgressDialog(activity);

                if (precacheAppOpenAd != null) {
                    showAdIfAvailable(precacheAppOpenAd, activity, appOpenADInterface);
                } else {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    AppOpenAd.load(activity, appopenAd, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {

                        @Override
                        public void onAdLoaded(AppOpenAd ad) {
                            showAdIfAvailable(ad, activity, appOpenADInterface);
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError loadAdError) {
                            if (isListNull(Constants.platformList)) {
                                switch (appOpenHitCounter) {
                                    case 0:
                                        appOpenHitCounter++;
                                        if (Constants.platformList.contains(Constants.ADX))
                                            showAppOpenAds(Constants.adsResponseModel.getApp_open_ads().getAdx(), activity, appOpenADInterface);
                                        break;
                                    case 1:
                                        appOpenHitCounter++;
                                        if (Constants.platformList.contains(Constants.FACEBOOK))
                                            showAppOpenAds(Constants.adsResponseModel.getApp_open_ads().getFacebook(), activity, appOpenADInterface);
                                        break;
                                    case 2:
                                        appOpenHitCounter = 0;
                                        Global.hideAlertProgressDialog();
                                        appOpenADInterface.appOpenAdState(false);
                                        break;
                                }
                            } else {
                                appOpenHitCounter = 0;
                                Global.hideAlertProgressDialog();
                                appOpenADInterface.appOpenAdState(false);
                            }

                            Global.printLog("adsLoading Failed ==", loadAdError.getMessage());
                            // Handle the error.
                        }

                    });
                }


            } else {
                Global.hideAlertProgressDialog();
                showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
                    appOpenADInterface.appOpenAdState(isLoaded);
                });
            }
        } else {
            Global.hideAlertProgressDialog();
            appOpenADInterface.appOpenAdState(false);
        }

    }

    public static void showAdIfAvailable(AppOpenAd appopenAd, Activity activity, AppInterfaces.AppOpenADInterface appOpenADInterface) {
        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                // Set the reference to null so isAdAvailable() returns false.
                appOpenADInterface.appOpenAdState(true);
                Global.hideAlertProgressDialog();
                buildAppOpenAdCache(activity, Constants.adsResponseModel.getInterstitial_ads().getAdmob());
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                appOpenADInterface.appOpenAdState(false);
                buildAppOpenAdCache(activity, Constants.adsResponseModel.getInterstitial_ads().getAdmob());
                Global.hideAlertProgressDialog();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                /*appOpenADInterface.appOpenAdState(true);*/
            }
        };

        appopenAd.setFullScreenContentCallback(fullScreenContentCallback);
        appopenAd.show(activity);

    }
    //TODO APPOPEN ADS--------------------------------------------------------------------------------------------------------------


    //TODO REWARD ADS--------------------------------------------------------------------------------------------------------------
    public static void buildRewardAdCache(String rewardAd, Activity activity) {
        cd = new ConnectionDetector(activity);
        if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads() && !isNull(rewardAd)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(activity, rewardAd, adRequest, new RewardedAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    if (isListNull(Constants.platformList)) {
                        switch (rewardAdHitCounter) {
                            case 0:
                                rewardAdHitCounter++;
                                if (Constants.platformList.contains(Constants.ADX))
                                    buildRewardAdCache(Constants.adsResponseModel.getRewarded_ads().getAdx(), activity);
                                break;
                            case 1:
                                rewardAdHitCounter++;
                                if (Constants.platformList.contains(Constants.FACEBOOK))
                                    buildRewardAdCache(Constants.adsResponseModel.getRewarded_ads().getFacebook(), activity);
                                break;
                            case 2:
                                rewardAdHitCounter = 0;
                                break;

                        }
                    } else {
                        rewardAdHitCounter = 0;
                    }
                    Global.sout("RewardedAdLoadCallback: ", loadAdError.toString());

                }

                @Override
                public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                    precacheRewardAd = rewardedAd;

                }

            });
        }
    }

    public static void showRewardAd(String rewardAd, Activity activity, AppInterfaces.RewardedAd rewardedAdInterface) {
        cd = new ConnectionDetector(activity);

        if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads() && !isNull(rewardAd)) {
            if (precacheRewardAd != null) {
                AdRequest adRequest = new AdRequest.Builder().build();
                precacheRewardAd.load(activity, rewardAd, adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Global.sout("RewardedAdLoadCallback: ", loadAdError.toString());
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        rewardedAd.show(activity, new OnUserEarnedRewardListener() {

                            @Override
                            public void onUserEarnedReward(RewardItem rewardItem) {
                                //TODO user earned reward --Not to implement now--
                            }
                        });
                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Global.sout("onAdFailedToShowFullScreenContent: ", adError.toString());
                                if (isListNull(Constants.platformList)) {
                                    switch (rewardAdHitCounter) {
                                        case 0:
                                            rewardAdHitCounter++;
                                            if (Constants.platformList.contains(Constants.ADX))
                                                showRewardAd(Constants.adsResponseModel.getRewarded_ads().getAdx(), activity, rewardedAdInterface);
                                            break;
                                        case 1:
                                            rewardAdHitCounter++;
                                            if (Constants.platformList.contains(Constants.FACEBOOK))
                                                showRewardAd(Constants.adsResponseModel.getRewarded_ads().getFacebook(), activity, rewardedAdInterface);
                                            break;
                                        case 2:
                                            rewardedAdInterface.rewardState(false);
                                            break;

                                    }
                                } else {
                                    rewardedAdInterface.rewardState(false);
                                }


                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                rewardedAdInterface.rewardState(false);
                            }
                        });
                    }

                });
            } else {

                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, rewardAd, adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Global.sout("RewardedAdLoadCallback: ", loadAdError.toString());
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        rewardedAd.show(activity, new OnUserEarnedRewardListener() {

                            @Override
                            public void onUserEarnedReward(RewardItem rewardItem) {
                                //TODO user earned reward --Not to implement now--
                            }
                        });
                        rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                Global.sout("onAdFailedToShowFullScreenContent: ", adError.toString());
                                if (isListNull(Constants.platformList)) {
                                    switch (rewardAdHitCounter) {
                                        case 0:
                                            rewardAdHitCounter++;
                                            if (Constants.platformList.contains(Constants.ADX))
                                                showRewardAd(Constants.adsResponseModel.getRewarded_ads().getAdx(), activity, rewardedAdInterface);
                                            break;
                                        case 1:
                                            rewardAdHitCounter++;
                                            if (Constants.platformList.contains(Constants.FACEBOOK))
                                                showRewardAd(Constants.adsResponseModel.getRewarded_ads().getFacebook(), activity, rewardedAdInterface);
                                            break;
                                        case 2:
                                            rewardedAdInterface.rewardState(false);
                                            break;

                                    }
                                } else {
                                    rewardedAdInterface.rewardState(false);
                                }


                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                rewardedAdInterface.rewardState(false);
                            }
                        });
                    }

                });
            }
        } else {
            rewardedAdInterface.rewardState(false);
        }
    }
    //TODO REWARD ADS--------------------------------------------------------------------------------------------------------------

    //TODO BANNER ADS--------------------------------------------------------------------------------------------------------------
    public static void showBannerAd(String bannerAdID, LinearLayout adContainer, Activity activity) {
        cd = new ConnectionDetector(activity);
        if (!isNull(bannerAdID)) {
            if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
                AdView mAdView = new AdView(activity);
                if (cd.isConnectingToInternet()) {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    try {
                        mAdView.setVisibility(View.VISIBLE);
                        mAdView.setEnabled(true);
                        mAdView.setAdSize(AdSize.BANNER);
                        mAdView.setAdUnitId(bannerAdID);
                        adContainer.setVisibility(View.VISIBLE);

                        mAdView.setAdListener(new AdListener() {
                            public void onAdFailedToLoad(int i) {
                                if (isListNull(Constants.platformList)) {
                                    switch (bannerAdHitCounter) {
                                        case 0:
                                            bannerAdHitCounter++;
                                            if (Constants.platformList.contains(Constants.ADX))
                                                showBannerAd(Constants.adsResponseModel.getBanner_ads().getAdx(), adContainer, activity);
                                            break;
                                        case 1:
                                            bannerAdHitCounter++;
                                            if (Constants.platformList.contains(Constants.FACEBOOK))
                                                showBannerAd(Constants.adsResponseModel.getBanner_ads().getFacebook(), adContainer, activity);
                                            break;
                                        case 2:
                                            adContainer.setVisibility(View.GONE);
                                            break;
                                    }
                                } else {
                                    adContainer.setVisibility(View.GONE);
                                }

                                Global.sout("Banner ad failed >>>>>>>>>>>>>>> ", i + "");
                            }

                            @Override
                            public void onAdClicked() {
                                super.onAdClicked();
                                //                        sAppOpenadsFLAG = false;
                            }

                            public void onAdLoaded() {
                            }

                            public void onAdOpened() {
                            }

                            public void onAdLeftApplication() {
                            }

                            public void onAdClosed() {
                            }
                        });

                        mAdView.loadAd(adRequest);
                        adContainer.removeAllViews();
                        adContainer.setPadding(5, 5, 5, 5);
                        adContainer.addView(mAdView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                mAdView.setVisibility(View.GONE);
                adContainer.setVisibility(View.GONE);
                adContainer.removeAllViews();
            }
        } else {
            adContainer.setVisibility(View.GONE);
            adContainer.removeAllViews();
        }
    }
    //TODO BANNER ADS--------------------------------------------------------------------------------------------------------------


    //TODO COLLAPSIBLE ADS--------------------------------------------------------------------------------------------------------------
    public static void showCollapsibleAd(String collapsibleAdId, Activity activity, LinearLayout adContainer, AppInterfaces.CollapsibleAdInterface collapsibleAdInterface) {
        try {
            AdView adView = new AdView(activity);
            AdSize customAdSize2 = new AdSize(AdSize.FULL_WIDTH, AdSize.AUTO_HEIGHT);
            adView.setAdSize(customAdSize2);
            adView.setAdUnitId(collapsibleAdId);
            Bundle extras = new Bundle();
            extras.putString("collapsible", "top");
            AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    if (isListNull(Constants.platformList)) {
                        switch (collapsibleAdHitCounter) {
                            case 0:
                                collapsibleAdHitCounter++;
                                if (Constants.platformList.contains(Constants.ADX))
                                    showCollapsibleAd(Constants.adsResponseModel.getBanner_ads().getAdx(), activity, adContainer, collapsibleAdInterface);
                                break;
                            case 1:
                                collapsibleAdHitCounter++;
                                if (Constants.platformList.contains(Constants.FACEBOOK))
                                    showCollapsibleAd(Constants.adsResponseModel.getBanner_ads().getFacebook(), activity, adContainer, collapsibleAdInterface);
                                break;
                            case 2:
                                collapsibleAdInterface.setAdState(false);
                                adContainer.setVisibility(View.GONE);
                                break;
                        }
                    } else {
                        collapsibleAdHitCounter = 0;
                        adContainer.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    adContainer.setGravity(Gravity.TOP);
                    collapsibleAdInterface.setAdState(true);
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    adContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    adContainer.setGravity(Gravity.BOTTOM);
                    collapsibleAdInterface.setAdState(false);
                }
            });

            adView.loadAd(adRequest);
            adContainer.addView(adView);

            adContainer.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Global.sout("Collapsible ad exception >>>>>>>>>>>>>> ", e.getLocalizedMessage());
        }
    }
    //TODO COLLAPSIBLE ADS--------------------------------------------------------------------------------------------------------------
}