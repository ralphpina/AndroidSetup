package com.ever.androidsetup.views;

import android.support.annotation.NonNull;

import com.ever.androidsetup.api.models.Giphy;

import java.util.List;

public interface MainView {

    void updateGiphies(List<Giphy> giphies);

    void onError(String error);

    void toastName(@NonNull String name);
}
