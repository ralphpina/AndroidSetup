package com.ever.test.common.rules;

import android.content.Context;

import com.ever.androidsetup.App;
import com.ever.androidsetup.api.GiphyClient;
import com.ever.test.common.api.TestGiphyClient;
import com.ever.test.common.injection.component.TestComponent;
import com.ever.test.common.injection.module.ApplicationTestModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

/**
 * Test rule that creates and sets a Dagger TestComponent into the application overriding the
 * existing application component.
 * Use this rule in your test case in order for the app to use mock dependencies.
 * It also exposes some of the dependencies so they can be easily accessed from the tests, e.g. to
 * stub mocks etc.
 */
public class TestComponentRule implements TestRule {

    private ApplicationTestModule module;
    private TestComponent testComponent;

    private TestComponentRule(ApplicationTestModule applicationTestModule) {
        this.module = applicationTestModule;
    }

    private void setupDaggerTestComponentInApplication() {
        App application = App.get();
        if (application == null) { // this will be null in Unit Tests
            application = new App();
        }
        // this needs to be set here, because during the builder process
        // the Application has not been instantiated.
        module.setApplication(application);
        testComponent = DaggerTestComponent.builder()
                .applicationTestModule(module)
                .build();
        application.setComponent(testComponent);
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    testComponent = null;
                }
            }
        };
    }

    // ==== GETTERS ================================================================================

    public TestGiphyClient getClient() {
        return (TestGiphyClient) testComponent.client();
    }

    // ===== BUILDER ===============================================================================

    public static class Builder {
        private Context context;
        private GiphyClient testClient;
        // Schedulers
        private final Scheduler computationScheduler;
        private Scheduler mainThreadScheduler;
        private final Scheduler ioScheduler;
        private final Scheduler singleThreadScheduler;

        // by default we are going to mock everything!
        public Builder() {
            this.testClient = new TestGiphyClient();
            // schedulers
            this.computationScheduler = Schedulers.immediate();
            this.mainThreadScheduler = Schedulers.immediate();
            this.ioScheduler = Schedulers.immediate();
            this.singleThreadScheduler = Schedulers.immediate();

            this.context = mock(Context.class);
        }

        public Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder withMainThreadScheduler() {
            this.mainThreadScheduler = AndroidSchedulers.mainThread();
            return this;
        }

        public TestComponentRule build() {
            ApplicationTestModule module = new ApplicationTestModule(
                    testClient,
                    computationScheduler,
                    mainThreadScheduler,
                    ioScheduler,
                    singleThreadScheduler);
            return new TestComponentRule(module);
        }
    }
}
