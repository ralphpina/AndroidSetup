package com.ever.test.common.api;

import com.ever.androidsetup.api.GiphyClient;
import com.ever.androidsetup.api.models.GiphyResponse;

import rx.Observable;


public class TestGiphyClient implements GiphyClient {

    @Override
    public Observable<GiphyResponse> getTrendingGiphys() {
        return null;
    }

    @Override
    public Observable<GiphyResponse> searchGiphys(String searchQuery) {
        return null;
    }
}
