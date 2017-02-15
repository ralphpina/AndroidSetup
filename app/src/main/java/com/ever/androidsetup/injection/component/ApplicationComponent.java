package com.ever.androidsetup.injection.component;

import com.ever.androidsetup.App;
import com.ever.androidsetup.api.GiphyClient;
import com.ever.androidsetup.injection.module.ApplicationModule;
import com.ever.androidsetup.injection.module.NetworkModule;
import com.ever.androidsetup.injection.module.SchedulerModule;
import com.ever.androidsetup.user.UserManager;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import rx.Scheduler;

import static com.ever.androidsetup.injection.module.SchedulerModule.COMPUTATION;
import static com.ever.androidsetup.injection.module.SchedulerModule.IO;
import static com.ever.androidsetup.injection.module.SchedulerModule.MAIN_THREAD;
import static com.ever.androidsetup.injection.module.SchedulerModule.SINGLE_THREAD;

@Singleton
@Component(modules = {ApplicationModule.class,
        NetworkModule.class, SchedulerModule.class})
public interface ApplicationComponent {

    void inject(App app);

    GiphyClient client();
    UserManager userManager();

    // Schedulers
    @Named(COMPUTATION)
    Scheduler computationScheduler();
    @Named(MAIN_THREAD)
    Scheduler mainThreadScheduler();
    @Named(IO)
    Scheduler ioScheduler();
    @Named(SINGLE_THREAD)
    Scheduler singleThreadScheduler();
}
