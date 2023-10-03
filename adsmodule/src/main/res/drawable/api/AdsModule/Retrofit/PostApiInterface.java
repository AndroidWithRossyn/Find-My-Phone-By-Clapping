package com.adsmodule.api.AdsModule.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostApiInterface {
    @POST("app_ads/get-app-ads/")
    Call<AdsResponseModel> getAdsData(@Body AdsDataRequestModel adsDataRequestModel);

    @POST("app_ads/get-app-download/")
    Call<String> registerAppCount(@Body AdsDataRequestModel adsDataRequestModel);
}
