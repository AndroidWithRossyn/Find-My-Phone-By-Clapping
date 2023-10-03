package com.adsmodule.api.AdsModule.Retrofit;

import com.tools.downaloader.adsmodule.AdsModule.Interfaces.AppInterfaces;
import com.tools.downaloader.adsmodule.AdsModule.Utils.Constants;
import com.tools.downaloader.adsmodule.AdsModule.Utils.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APICallHandler {

    // App download count api
    public static void callAppCountApi(String baseURL, AdsDataRequestModel requestModel) {
        PostApiInterface apiInterface = RetroFit_APIClient.getInstance().getClient(baseURL).create(PostApiInterface.class);
        Call<String> call = apiInterface.registerAppCount(requestModel);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Global.sout("Counts api response >>>>>>>>>>> ", response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                /*TODO nothing to handle here*/
            }
        });
    }

    // Ads api calls

    public static void callAdsApi(String baseURL, AdsDataRequestModel requestModel, AppInterfaces.AdDataInterface adDataInterface) {
        PostApiInterface apiInterface = RetroFit_APIClient.getInstance().getClient(baseURL).create(PostApiInterface.class);
        Call<AdsResponseModel> call = apiInterface.getAdsData(requestModel);
        call.enqueue(new Callback<AdsResponseModel>() {
            @Override
            public void onResponse(Call<AdsResponseModel> call, Response<AdsResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adDataInterface.getAdData(response.body());
                } else {
                    adDataInterface.getAdData(null);
                }
            }

            @Override
            public void onFailure(Call<AdsResponseModel> call, Throwable t) {
                callOptionalAdsApi(Constants.OPTIONAL_BASE_URL, requestModel, adDataInterface);
            }
        });
    }

    private static void callOptionalAdsApi(String optionalBaseUrl, AdsDataRequestModel requestModel, AppInterfaces.AdDataInterface adDataInterface) {
        PostApiInterface apiInterface = RetroFit_APIClient.getInstance().getClient(optionalBaseUrl).create(PostApiInterface.class);
        Call<AdsResponseModel> call = apiInterface.getAdsData(requestModel);
        call.enqueue(new Callback<AdsResponseModel>() {
            @Override
            public void onResponse(Call<AdsResponseModel> call, Response<AdsResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adDataInterface.getAdData(response.body());
                }
            }

            @Override
            public void onFailure(Call<AdsResponseModel> call, Throwable t) {
                adDataInterface.getAdData(null);
            }
        });
    }
}
