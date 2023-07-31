package com.deepcode.graduantestapp;

import android.content.Context;

import com.deepcode.graduantestapp.interfaces.ApiService;
import com.deepcode.graduantestapp.util.TokenManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://android-test.graduan.info/api/";

    public static Retrofit getClient(Context context) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    String accessToken = TokenManager.getInstance(context).getAccessToken();

                    if (accessToken != null) {
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", "Bearer " + accessToken)
                                .method(original.method(), original.body());
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }

                    return chain.proceed(original);
                })
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}