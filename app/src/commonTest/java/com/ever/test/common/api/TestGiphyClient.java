package com.ever.test.common.api;

import com.ever.androidsetup.api.GiphyClient;
import com.ever.androidsetup.api.models.Giphy;
import com.ever.androidsetup.api.models.GiphyResponse;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;


public class TestGiphyClient implements GiphyClient {

    private boolean shouldFailNextCall;
    private GiphyResponse trending;
    private GiphyResponse search;

    public void setTrending(List<Giphy> giphies) {
        trending = new GiphyResponse();
        trending.data = giphies;
    }

    public void setSearch(List<Giphy> giphies) {
        search = new GiphyResponse();
        search.data = giphies;
    }

    public void setShouldFailNextCall(boolean shouldFailNextCall) {
        this.shouldFailNextCall = shouldFailNextCall;
    }

    @Override
    public Observable<GiphyResponse> getTrendingGiphys() {
        return Observable.just(trending)
                .flatMap(new Func1<GiphyResponse, Observable<GiphyResponse>>() {
                    @Override
                    public Observable<GiphyResponse> call(GiphyResponse response) {
                        assertNextCall();
                        return Observable.just(response);
                    }
                });
    }

    @Override
    public Observable<GiphyResponse> searchGiphys(String searchQuery) {
        return Observable.just(search)
                .flatMap(new Func1<GiphyResponse, Observable<GiphyResponse>>() {
                    @Override
                    public Observable<GiphyResponse> call(GiphyResponse response) {
                        assertNextCall();
                        return Observable.just(response);
                    }
                });
    }

    /**
     * Fake a network error
     */
    private void assertNextCall() {
        if (shouldFailNextCall) {
            shouldFailNextCall = false;
            throw new RuntimeException("some error");
        }
    }
}
