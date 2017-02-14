package com.ever.androidsetup.injection.module;

import com.ever.androidsetup.api.GiphyClient;
import com.ever.androidsetup.api.GiphyClientImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    GiphyClient provideClient() {
        return new GiphyClientImpl();
    }
}
