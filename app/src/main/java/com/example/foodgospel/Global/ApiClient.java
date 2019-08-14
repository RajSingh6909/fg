package com.example.foodgospel.Global;

import com.example.foodgospel.Models.AllFarmerData;
import com.example.foodgospel.Models.AllMasterData;
import com.example.foodgospel.Models.GetAllRegionData;
import com.example.foodgospel.Models.GetRegisteredFarmerData;
import com.example.foodgospel.Models.PostResponse;
import com.example.foodgospel.Models.Sector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class ApiClient {
    private static final String url = "http://togglebits.in/food_gospel/";

    public static PostService postService = null;
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static PostService getPostService() {
        if (postService == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("email", "demo@demo.com").addHeader("password", "demo").build();
                    return chain.proceed(request);
                }
            });


            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).client(httpClient.build()).client(httpClient.build()).build();
            postService = retrofit.create(PostService.class);

        }
        return postService;
    }

    public interface PostService {


        @GET("offData")
        Call<GetAllRegionData> getAllRegionData();

        @POST("getAllMaster")
        Call<AllMasterData> getAllMasterData();

        @POST("addFarmer")
        Call<PostResponse> addFarmer(@Body JsonObject data);

        @POST("editFarmer")
        Call<PostResponse> editFarmer(@Body JsonObject data);

        @POST("addDairyFarmer")
        Call<PostResponse> addDairyFarmer(@Body JsonObject data);

        @POST("editDairyFarmer")
        Call<PostResponse> editDairyFarmer(@Body JsonObject data);

        @POST("addPoultryFarmer")
        Call<PostResponse> addPoultryFarmer(@Body JsonObject data);

        @POST("editPoultryFarmer")
        Call<PostResponse> editPoultryFarmer(@Body JsonObject data);

        @FormUrlEncoded
        @POST("viewUserFarmer")
        Call<AllFarmerData> getAllFarmerData(@Field("user_id") String user_id);

        @FormUrlEncoded
        @POST("deleteFarmer")
        Call<PostResponse> deleteFarmer(@Field("id") int id);
    }


}
