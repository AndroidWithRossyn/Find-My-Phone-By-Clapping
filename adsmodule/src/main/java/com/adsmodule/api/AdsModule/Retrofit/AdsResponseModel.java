package com.adsmodule.api.AdsModule.Retrofit;

public class AdsResponseModel {

    private String url;
    private AppOpenAdsDTO app_open_ads = new AppOpenAdsDTO();
    private BannerAdsDTO banner_ads = new BannerAdsDTO();
    private NativeAdsDTO native_ads = new NativeAdsDTO();
    private InterstitialAdsDTO interstitial_ads = new InterstitialAdsDTO();
    private RewardedAdsDTO rewarded_ads = new RewardedAdsDTO();
    private MobileStickyAdsDTO mobile_sticky_ads = new MobileStickyAdsDTO();
    private CollapsibleAdsDTO collapsible_ads = new CollapsibleAdsDTO();
    private CustomAdsJsonDTO custom_ads_json = new CustomAdsJsonDTO();
    private ExtraDataFieldDTO extra_data_field = new ExtraDataFieldDTO();
    private String app_name;
    private String package_name;
    private boolean show_ads;
    private String ads_open_type;
    private int ads_count;
    private String version_name;
    private String button_bg;
    private String button_text_color;
    private String common_text_color;
    private String ads_bg;
    private String backpress_ads_type;
    private int backpress_count;

    public String getBackpress_ads_type() {
        return backpress_ads_type;
    }

    public void setBackpress_ads_type(String backpress_ads_type) {
        this.backpress_ads_type = backpress_ads_type;
    }

    public int getBackpress_count() {
        return backpress_count;
    }

    public void setBackpress_count(int backpress_count) {
        this.backpress_count = backpress_count;
    }

    public String getButton_bg() {
        return button_bg;
    }

    public void setButton_bg(String button_bg) {
        this.button_bg = button_bg;
    }

    public String getCommon_text_color() {
        return common_text_color;
    }

    public void setCommon_text_color(String common_text_color) {
        this.common_text_color = common_text_color;
    }

    public String getButton_text_color() {
        return button_text_color;
    }

    public void setButton_text_color(String button_text_color) {
        this.button_text_color = button_text_color;
    }

    public String getAd_bg() {
        return ads_bg;
    }

    public void setAd_bg(String ad_bg) {
        this.ads_bg = ad_bg;
    }


    public String getMonetize_platform() {
        return monetize_platform;
    }

    public void setMonetize_platform(String monetize_platform) {
        this.monetize_platform = monetize_platform;
    }

    String monetize_platform;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AppOpenAdsDTO getApp_open_ads() {
        return app_open_ads;
    }

    public void setApp_open_ads(AppOpenAdsDTO app_open_ads) {
        this.app_open_ads = app_open_ads;
    }

    public BannerAdsDTO getBanner_ads() {
        return banner_ads;
    }

    public void setBanner_ads(BannerAdsDTO banner_ads) {
        this.banner_ads = banner_ads;
    }

    public NativeAdsDTO getNative_ads() {
        return native_ads;
    }

    public void setNative_ads(NativeAdsDTO native_ads) {
        this.native_ads = native_ads;
    }

    public InterstitialAdsDTO getInterstitial_ads() {
        return interstitial_ads;
    }

    public void setInterstitial_ads(InterstitialAdsDTO interstitial_ads) {
        this.interstitial_ads = interstitial_ads;
    }

    public RewardedAdsDTO getRewarded_ads() {
        return rewarded_ads;
    }

    public void setRewarded_ads(RewardedAdsDTO rewarded_ads) {
        this.rewarded_ads = rewarded_ads;
    }

    public MobileStickyAdsDTO getMobile_sticky_ads() {
        return mobile_sticky_ads;
    }

    public void setMobile_sticky_ads(MobileStickyAdsDTO mobile_sticky_ads) {
        this.mobile_sticky_ads = mobile_sticky_ads;
    }

    public CollapsibleAdsDTO getCollapsible_ads() {
        return collapsible_ads;
    }

    public void setCollapsible_ads(CollapsibleAdsDTO collapsible_ads) {
        this.collapsible_ads = collapsible_ads;
    }

    public CustomAdsJsonDTO getCustom_ads_json() {
        return custom_ads_json;
    }

    public void setCustom_ads_json(CustomAdsJsonDTO custom_ads_json) {
        this.custom_ads_json = custom_ads_json;
    }

    public ExtraDataFieldDTO getExtra_data_field() {
        return extra_data_field;
    }

    public void setExtra_data_field(ExtraDataFieldDTO extra_data_field) {
        this.extra_data_field = extra_data_field;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public boolean isShow_ads() {
        return show_ads;
    }

    public void setShow_ads(boolean show_ads) {
        this.show_ads = show_ads;
    }

    public String getAds_open_type() {
        return ads_open_type;
    }

    public void setAds_open_type(String ads_open_type) {
        this.ads_open_type = ads_open_type;
    }

    public int getAds_count() {
        return ads_count;
    }

    public void setAds_count(int ads_count) {
        this.ads_count = ads_count;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public static class AppOpenAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class BannerAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class NativeAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class InterstitialAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class RewardedAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class CollapsibleAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }

    public static class CustomAdsJsonDTO {
    }

    public static class ExtraDataFieldDTO {
    }

    private class MobileStickyAdsDTO {
        private String Admob = "";
        private String Facebook = "";
        private String Adx = "";

        public String getAdmob() {
            return Admob;
        }

        public void setAdmob(String admob) {
            this.Admob = admob;
        }

        public String getFacebook() {
            return Facebook;
        }

        public void setFacebook(String Facebook) {
            this.Facebook = Facebook;
        }

        public String getAdx() {
            return Adx;
        }

        public void setAdx(String adx) {
            this.Adx = adx;
        }
    }
}
