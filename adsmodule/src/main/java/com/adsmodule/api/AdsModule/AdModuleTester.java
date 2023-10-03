package com.adsmodule.api.AdsModule;

import static com.adsmodule.api.AdsModule.Utils.Global.isClassNull;
import static com.adsmodule.api.AdsModule.Utils.StringUtils.isNull;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.adsmodule.api.AdsModule.Interfaces.AppInterfaces;
import com.adsmodule.api.AdsModule.Utils.ConnectionDetector;
import com.adsmodule.api.AdsModule.Utils.Constants;
import com.adsmodule.api.AdsModule.Utils.Global;
import com.adsmodule.api.AdsModule.Utils.StringUtils;
import com.adsmodule.api.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;

import java.util.ArrayList;
import java.util.List;

/*TODO---------------(Please Note this)---------------
 * Use this class to test your ads IDS
 * This is not for production ads and also it has not precache. This is just for testing
 * AdUtils class is for Actual ads implementation
 * ---------------(Please Note this)---------------*/

public class AdModuleTester {
    private static ConnectionDetector cd;

    // TODO INTERSTITIAL AD TEST IMPLEMENTATION--------------------------------------------------------------------------------------------------------------
    public static void showInterstitialAd(String interstitialAd, Activity activity, AppInterfaces.InterstitialADInterface interstitialADInterface) {
        cd = new ConnectionDetector(activity);
        if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads() && !isClassNull(Constants.adsResponseModel.getInterstitial_ads())) {
            if (Constants.hitCounter == Constants.adsResponseModel.getAds_count() && !isNull(interstitialAd)) {
                Constants.hitCounter = 0;
                Global.showAlertProgressDialog(activity);
                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(activity, interstitialAd, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        Global.hideAlertProgressDialog();
                        loadInterstitialAd(activity, interstitialADInterface, interstitialAd);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Global.hideAlertProgressDialog();
                        interstitialADInterface.adLoadState(false);
                    }
                });

            } else {
                Global.hideAlertProgressDialog();
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
                    Global.hideAlertProgressDialog();
                    interStitialADInterface.adLoadState(true);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Global.hideAlertProgressDialog();
                    interStitialADInterface.adLoadState(false);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                }
            });
            iAds.show(activity);
        } else {
            Global.hideAlertProgressDialog();
            interStitialADInterface.adLoadState(false);
        }
    }

    public static void showFacebookInterstitial(Activity activity, String facebookAdId, AppInterfaces.InterstitialADInterface interstitialADInterface) {
        AdSettings.addTestDevice("31a1b612-4a85-4a80-a9be-d02308a35466");
        com.facebook.ads.InterstitialAd facebookInterstitialAd = new com.facebook.ads.InterstitialAd(activity, facebookAdId);
        Global.showAlertProgressDialog(activity);
        facebookInterstitialAd.loadAd(facebookInterstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                if (ad != null) {
                    Global.hideAlertProgressDialog();
                    Global.sout("Face ad displayed >>> ", "");
                }
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Global.hideAlertProgressDialog();
                interstitialADInterface.adLoadState(true);
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Global.sout("Face ad displayed >>> ", adError);
                Global.hideAlertProgressDialog();
                interstitialADInterface.adLoadState(true);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                facebookInterstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        }).build());


    }
    // TODO INTERSTITIAL AD IMPLEMENTATION--------------------------------------------------------------------------------------------------------------


    // TODO NATIVE AD IMPLEMENTATION--------------------------------------------------------------------------------------------------------------

    public static void showNativeAd(Activity activity, String nativeAd, LinearLayout adContainer, int isFullScreenAd, Drawable imageID) {
        cd = new ConnectionDetector(activity);
        if (imageID != null) {
            adContainer.addView(Global.getDefaultImage(activity, imageID));
        }
        if (!isNull(nativeAd)) {
            if (cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
                try {
                    AdLoader adLoader = new AdLoader.Builder(activity, nativeAd).forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {

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
                                populateUnifiedNativeAdView(activity, nativeAd, unifiedNativeAdView, isFullScreenAd);

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
                            Global.sout("Native failed for ad>>>>>>>>>>>>>>>>>", adError.toString());

                        }
                    }).withNativeAdOptions(new NativeAdOptions.Builder().build()).build();
                    adLoader.loadAd(new AdRequest.Builder().build());

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

    public static void showFacebookNativeAd(Activity activity, String facebookAdId, CardView adContainer, AppInterfaces.FacebookInterface facebookInterface) {
        AdSettings.addTestDevice("31a1b612-4a85-4a80-a9be-d02308a35466");
        com.facebook.ads.NativeAd faceBookNativeAd = new com.facebook.ads.NativeAd(activity, facebookAdId);
        faceBookNativeAd.loadAd(faceBookNativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                inflateFaceBookAd(activity, (com.facebook.ads.NativeAd) ad, adContainer);
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {

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

    private static void inflateFaceBookAd(Activity activity, com.facebook.ads.NativeAd facebookAd, CardView adContainer) {
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

    public static void showAppOpenAds(String appopenAd, Activity activity, AppInterfaces.AppOpenADInterface appOpenADInterface) {
        ConnectionDetector cd = new ConnectionDetector(activity);
        if (!isNull(appopenAd) && Constants.adsResponseModel != null && cd.isConnectingToInternet() && Constants.adsResponseModel.isShow_ads()) {
            if (StringUtils.CheckEqualIgnoreCase(Constants.adsResponseModel.getAds_open_type(), Constants.IS_APP_OPEN_ADS)) {
                Global.showAlertProgressDialog(activity);


                AdRequest adRequest = new AdRequest.Builder().build();
                AppOpenAd.load(activity, appopenAd, adRequest, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {

                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        showAdIfAvailable(ad, activity, appOpenADInterface);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Global.hideAlertProgressDialog();
                        appOpenADInterface.appOpenAdState(false);
                    }
                });
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
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                appOpenADInterface.appOpenAdState(false);
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

}
