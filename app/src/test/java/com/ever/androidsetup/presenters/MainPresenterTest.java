package com.ever.androidsetup.presenters;

import android.support.annotation.NonNull;

import com.ever.androidsetup.api.models.Giphy;
import com.ever.androidsetup.views.MainView;
import com.ever.test.common.BaseTest;
import com.ever.test.common.api.TestGiphyClient;
import com.ever.test.common.rules.TestComponentRule;
import com.ever.test.common.user.TestUserManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;

public class MainPresenterTest extends BaseTest {

    private MainPresenter presenter;
    private TestMainView view;
    private TestGiphyClient client;
    private TestUserManager userManager;

    @Rule
    public final TestComponentRule component = new TestComponentRule.Builder()
            .withClient()
            .withUserManager(false)
            .build();

    @Before
    public void setUp() throws Exception {
        view = new TestMainView();
        presenter = new MainPresenter(view);
        client = component.getClient();
        userManager = (TestUserManager) component.getUserManager();
    }

    @Test
    public void error_when_getting_trending() throws Exception {
        client.setShouldFailNextCall(true);
        {
            assertThat(view.error).isNull();
        }

        presenter.onResume();
        {
            assertThat(view.error).isEqualTo("some error");
        }
    }

    @Test
    public void error_when_search() throws Exception {
        client.setShouldFailNextCall(true);
        {
            assertThat(view.error).isNull();
        }

        presenter.search("some");
        {
            assertThat(view.error).isEqualTo("some error");
        }
    }

    @Test
    public void populate_trending_on_resume() throws Exception {
        final List<Giphy> giphies = getGiphies(10);
        client.setTrending(giphies);
        {
            assertThat(view.giphies).isNull();
        }

        presenter.onResume();
        {
            assertThat(view.giphies).isEqualTo(giphies);
            assertThat(view.giphies.size()).isEqualTo(10);
        }
    }

    @Test
    public void do_not_populate_trending_when_search() throws Exception {
        final List<Giphy> giphies = getGiphies(20);
        client.setTrending(giphies.subList(0, 10));
        client.setSearch(giphies.subList(10, 20));
        TestScheduler observeOn = Schedulers.test();
        TestScheduler subscribeOn = Schedulers.test();
        presenter.setTestSchedulers(observeOn, subscribeOn);
        {
            assertThat(presenter.isSearching()).isFalse();
        }

        presenter.search("some");
        {
            assertThat(presenter.isSearching()).isTrue();
            assertThat(view.giphies).isNull();
        }

        // switch to immidiate executors, but they should never run
        presenter.setTestSchedulers(Schedulers.immediate(), Schedulers.immediate());
        presenter.onResume();
        {
            assertThat(presenter.isSearching()).isTrue();
            assertThat(view.giphies).isNull();
        }

        subscribeOn.triggerActions();
        observeOn.triggerActions();
        {
            assertThat(presenter.isSearching()).isFalse();
            assertThat(view.giphies).isEqualTo(giphies.subList(10, 20));
        }
    }

    @Test
    public void populate_search() throws Exception {
        final List<Giphy> giphies = getGiphies(10);
        client.setSearch(giphies);
        {
            assertThat(view.giphies).isNull();
        }

        presenter.search("some");
        {
            assertThat(view.giphies).isEqualTo(giphies);
            assertThat(view.giphies.size()).isEqualTo(10);
        }
    }

    @Test
    public void show_name_on_resume() throws Exception {
        {
            assertThat(view.name).isNull();
        }

        presenter.onResume();
        {
            assertThat(view.name).isNull();
        }

        userManager.setDisplayName("some name");
        presenter.onResume();
        {
            assertThat(view.name).isEqualTo("some name");
        }
    }

    private class TestMainView implements MainView {

        List<Giphy> giphies;
        String error;
        String name;

        @Override
        public void updateGiphies(List<Giphy> giphies) {
            this.giphies = giphies;
        }

        @Override
        public void onError(String error) {
            this.error = error;
        }

        @Override
        public void toastName(@NonNull String name) {
            this.name = name;
        }
    }
}