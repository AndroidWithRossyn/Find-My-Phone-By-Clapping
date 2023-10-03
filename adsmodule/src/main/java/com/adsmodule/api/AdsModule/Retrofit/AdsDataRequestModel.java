package com.adsmodule.api.AdsModule.Retrofit;

import androidx.annotation.Nullable;

public class AdsDataRequestModel {
    String package_name;
    String device_id;

    public AdsDataRequestModel(@Nullable String package_name, @Nullable String device_id) {
        this.package_name = package_name;
        this.device_id = device_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
}
