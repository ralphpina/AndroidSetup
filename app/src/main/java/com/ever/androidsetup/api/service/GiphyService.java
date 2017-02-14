package com.ever.androidsetup.api.service;

import com.ever.androidsetup.api.models.GiphyResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GiphyService {

    @GET("trending")
    Observable<GiphyResponse> trendingGiphys(@Query("api_key") String apiKey);

    @GET("search")
    Observable<GiphyResponse> searchGiphys(@Query("q") String searchQuery, @Query("api_key") String apiKey);
}
