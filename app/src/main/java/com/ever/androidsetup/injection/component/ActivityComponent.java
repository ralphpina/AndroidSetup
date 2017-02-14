package com.ever.androidsetup.injection.component;

import com.ever.androidsetup.injection.scope.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {
}
