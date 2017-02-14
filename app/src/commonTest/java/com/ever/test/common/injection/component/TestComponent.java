package com.ever.test.common.injection.component;

import com.ever.androidsetup.injection.component.ApplicationComponent;
import com.ever.test.common.injection.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}