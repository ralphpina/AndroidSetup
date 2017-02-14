package com.ever.androidsetup;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.ever.androidsetup.injection.component.ApplicationComponent;
import com.ever.androidsetup.injection.component.DaggerApplicationComponent;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.concurrent.atomic.AtomicBoolean;


public class App extends Application {

    private static App instance;

    private ApplicationComponent applicationComponent;

    public App() {
        super();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        // inject stuff
        applicationComponent = DaggerApplicationComponent.builder()
                .build();
        applicationComponent.inject(this); // if we needed to inject anything into here
    }

    // ===== Injection ================================================

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

    // Needed to replace the component with a test specific one
    @VisibleForTesting
    public void setComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        // we need to inject again, otherwise the objects here are the originals
        // not the test ones as one would expect R.Pina 20160105
        this.applicationComponent.inject(this);
    }

    public static App get() {
        return instance;
    }

    // ===== TESTING ===============================================

    private static AtomicBoolean isRunningTest;

    public static synchronized boolean isRunningIntegrationTest() {
        if (null == isRunningTest) {
            boolean istest;

            try {
                Class.forName("android.support.test.espresso.Espresso");
                istest = true;
            } catch (ClassNotFoundException e) {
                istest = false;
            }

            isRunningTest = new AtomicBoolean(istest);
        }

        return isRunningTest.get();
    }
}
