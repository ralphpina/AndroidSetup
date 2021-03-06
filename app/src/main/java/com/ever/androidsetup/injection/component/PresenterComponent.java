package com.ever.androidsetup.injection.component;

import com.ever.androidsetup.injection.scope.PerActivity;
import com.ever.androidsetup.presenters.MainPresenter;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@SuppressWarnings("WeakerAccess")
@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface PresenterComponent {

    void inject(MainPresenter mainPresenter);
}
