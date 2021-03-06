package com.ever.androidsetup.injection.module;

import android.app.Application;
import android.content.Context;

import com.ever.androidsetup.user.UserManager;
import com.ever.androidsetup.user.UserManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context provideContext() { return application; }

    @Provides
    @Singleton
    UserManager userManager() { return new UserManagerImpl(); }
}
