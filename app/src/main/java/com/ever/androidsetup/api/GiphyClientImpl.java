package com.ever.androidsetup.api;

import android.support.annotation.NonNull;

import com.ever.androidsetup.api.models.GiphyResponse;
import com.ever.androidsetup.api.service.GiphyService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class GiphyClientImpl implements GiphyClient {

    private static final String BASE_API = "http://api.giphy.com/v1/gifs/";

    private final GiphyService giphyService;

    public GiphyClientImpl() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        giphyService = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_API)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GiphyService.class);
    }

    @Override
    public Observable<GiphyResponse> getTrendingGiphys() {
        return giphyService.trendingGiphys();
    }

    @Override
    public Observable<GiphyResponse> searchGiphys(@NonNull String searchQuery) {
        return giphyService.searchGiphys(searchQuery);
    }
}
