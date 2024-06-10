package com.dafrizz.svedata.request;

public class UtilsAPI {
    public static final String BASE_URL_API = "http://10.0.2.2:9194/";
    public static BaseAPIService getApiService() {
        return
                RetrofitClient.getClient(BASE_URL_API).create(BaseAPIService.class);
    }
}
