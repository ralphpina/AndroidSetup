package com.ever.test.common.injection.module;

import android.app.Application;

import com.ever.androidsetup.App;
import com.ever.androidsetup.api.GiphyClient;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

import static com.ever.androidsetup.injection.module.SchedulerModule.COMPUTATION;
import static com.ever.androidsetup.injection.module.SchedulerModule.IO;
import static com.ever.androidsetup.injection.module.SchedulerModule.MAIN_THREAD;
import static com.ever.androidsetup.injection.module.SchedulerModule.SINGLE_THREAD;

@Module
public class ApplicationTestModule {

    private Application application;
    private final GiphyClient client;
    private final Scheduler computationScheduler;
    private final Scheduler mainThreadScheduler;
    private final Scheduler ioScheduler;
    private final Scheduler singleThreadScheduler;

    public ApplicationTestModule(GiphyClient client,
                                 Scheduler computationScheduler,
                                 Scheduler mainThreadScheduler,
                                 Scheduler ioScheduler,
                                 Scheduler singleThreadScheduler) {
        this.client = client;
        this.computationScheduler = computationScheduler;
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioScheduler = ioScheduler;
        this.singleThreadScheduler = singleThreadScheduler;
    }

    public void setApplication(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    GiphyClient provideClient() {
        return client;
    }

    // ===== SCHEDULERS ============================================================================

    @Provides
    @Named(MAIN_THREAD)
    Scheduler provideMainThread() {
        return mainThreadScheduler;
    }

    @Provides
    @Named(COMPUTATION)
    Scheduler provideComputation() {
        return computationScheduler;
    }

    @Provides
    @Named(IO)
    Scheduler provideIo() {
        return ioScheduler;
    }

    @Provides
    @Named(SINGLE_THREAD)
    Scheduler provideNewThread() {
        return singleThreadScheduler;
    }

}