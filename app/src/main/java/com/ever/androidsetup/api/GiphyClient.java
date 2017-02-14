package com.ever.androidsetup.api;

import com.ever.androidsetup.api.models.GiphyResponse;

import rx.Observable;

public interface GiphyClient {

    Observable<GiphyResponse> getTrendingGiphys();

    Observable<GiphyResponse> searchGiphys(String searchQuery);
}
