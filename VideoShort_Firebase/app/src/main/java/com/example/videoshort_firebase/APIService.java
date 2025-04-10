package com.example.videoshort_firebase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIService {
    Gson gson = new GsonBuilder() . setDateFormat("yyyy MM dd HH:mm:ss") . create ();
    APIService servieapi = new Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/poudyalanil/ca84582cbeb4fc123a13290a586da925/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("raw")
    Call<List<VideoModel>> getVideos();
}
