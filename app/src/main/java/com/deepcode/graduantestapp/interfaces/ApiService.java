package com.deepcode.graduantestapp.interfaces;

import com.deepcode.graduantestapp.model.FormData;
import com.deepcode.graduantestapp.model.Post;
import com.deepcode.graduantestapp.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Accept: application/json")
    @POST("login")
    Call<ResponseBody> login(@Body FormData formDataBody);

    @Headers("Accept: application/json")
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @POST("user")
    Call<String> updateProfile(@Header("Authorization") String authorization, @Body FormData formData);

    @Headers("Accept: application/json")
    @POST("logout")
    Call<String> logout(@Header("Authorization") String authorization);

    @Headers("Accept: application/json")
    @POST("post")
    Call<ResponseBody> createPost(@Header("Authorization") String authorization,@Body Post formData);

    @Headers("Accept: application/json")
    @GET("post")
    Call<List<Post>> getAllPosts(@Header("Authorization") String authorization);
}
