package com.ever.androidsetup;

import android.support.v7.app.AppCompatActivity;

import com.ever.androidsetup.injection.component.ActivityComponent;
import com.ever.androidsetup.injection.component.DaggerActivityComponent;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    public final ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(App.get()
                            .getComponent())
                    .build();
        }
        return activityComponent;
    }
}
