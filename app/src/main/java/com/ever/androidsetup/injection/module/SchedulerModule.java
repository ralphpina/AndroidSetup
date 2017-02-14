package com.ever.androidsetup.injection.module;

import java.util.concurrent.Executors;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class SchedulerModule {
    public final static String MAIN_THREAD   = "scheduler_main_thread";
    public final static String COMPUTATION   = "scheduler_computation";
    public final static String IO            = "scheduler_io";
    public final static String SINGLE_THREAD = "scheduler_single_thread";

    @Provides
    @Named(COMPUTATION)
    Scheduler provideComputationThread() {
        return Schedulers.computation();
    }

    @Provides
    @Named(MAIN_THREAD)
    Scheduler provideMainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named(IO)
    Scheduler provideIo() {
        return Schedulers.io();
    }

    @Provides
    @Named(SINGLE_THREAD)
    Scheduler provideNewThread() {
        return Schedulers.from(Executors.newSingleThreadExecutor());
    }
}
