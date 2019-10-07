package com.deb.demo;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    final static String BANNER_URL = "http://47.56.177.143/ADMApp/1234/";
    public static String getBannerJson() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BANNER_URL)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return  response.body().string();
    }
}
