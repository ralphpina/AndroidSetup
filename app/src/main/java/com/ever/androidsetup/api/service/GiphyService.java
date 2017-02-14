package com.ever.androidsetup.api.service;

import com.ever.androidsetup.api.models.GiphyResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GiphyService {

    @GET("trending?api_key=dc6zaTOxFJmzC")
    Observable<GiphyResponse> trendingGiphys();

    @GET("search?q={query}&api_key=dc6zaTOxFJmzC")
    Observable<GiphyResponse> searchGiphys(@Path("query") String searchQuery);
}
