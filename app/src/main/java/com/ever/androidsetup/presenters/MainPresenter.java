package com.ever.androidsetup.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ever.androidsetup.App;
import com.ever.androidsetup.api.GiphyClient;
import com.ever.androidsetup.api.models.GiphyResponse;
import com.ever.androidsetup.injection.component.DaggerPresenterComponent;
import com.ever.androidsetup.user.UserManager;
import com.ever.androidsetup.views.MainView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Scheduler;
import rx.functions.Action0;
import rx.functions.Action1;

import static com.ever.androidsetup.injection.module.SchedulerModule.IO;
import static com.ever.androidsetup.injection.module.SchedulerModule.MAIN_THREAD;

public class MainPresenter {

    private static final String TAG = "MainPresenter";

    @Inject
    GiphyClient client;
    @Inject
    UserManager userManager;
    @Inject
    @Named(MAIN_THREAD)
    Scheduler observerOn;
    @Inject
    @Named(IO)
    Scheduler subscribeOn;

    private final WeakReference<MainView> viewRef;

    private boolean isSearching;

    public MainPresenter(MainView view) {
        DaggerPresenterComponent.builder()
                .applicationComponent(App.get()
                        .getComponent())
                .build()
                .inject(this);
        viewRef = new WeakReference<>(view);
    }

    public void onResume() {
        if (viewRef.get() != null) {
            viewRef.get().toastName(userManager.getDisplayName());
        }
        if (isSearching) {
            return;
        }
        client.getTrendingGiphys()
                .take(1)
                .observeOn(observerOn)
                .subscribeOn(subscribeOn)
                .subscribe(new Action1<GiphyResponse>() {
                    @Override
                    public void call(GiphyResponse giphyResponse) {
                        if (viewRef.get() != null) {
                            viewRef.get().updateGiphies(giphyResponse.data);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "error getting giphies = " + throwable.getMessage());
                    }
                });
    }

    public void search(@NonNull String query) {
        isSearching = true;
        client.searchGiphys(query)
                .take(1)
                .observeOn(observerOn)
                .subscribeOn(subscribeOn)
                .subscribe(new Action1<GiphyResponse>() {
                    @Override
                    public void call(GiphyResponse giphyResponse) {
                        if (viewRef.get() != null) {
                            viewRef.get().updateGiphies(giphyResponse.data);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "error getting search giphies = " + throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        isSearching = false;
                    }
                });
    }
}
