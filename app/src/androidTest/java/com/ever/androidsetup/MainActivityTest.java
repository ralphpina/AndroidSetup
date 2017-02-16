package com.ever.androidsetup;

import android.support.test.rule.ActivityTestRule;

import com.ever.test.common.BaseTest;
import com.ever.test.common.api.TestGiphyClient;
import com.ever.test.common.rules.TestComponentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class MainActivityTest extends BaseTest {

    private TestGiphyClient client;

    public final TestComponentRule component = new TestComponentRule.Builder()
            .withClient()
            .withUserManager(true)
            .build();

    public final ActivityTestRule<MainActivity> activity = new ActivityTestRule<>(
            MainActivity.class,
            false,
            false);

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public TestRule chain = RuleChain.outerRule(component)
            .around(activity);

    @Before
    public void setUp() throws Exception {
        client = component.getClient();
    }

    @Test
    public void spinner_shown_until_data() throws Exception {
        this.activity.launchActivity(null);
        {
            onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())));
        }
        activity.getActivity().finish();
    }

    @Test
    public void with_data_no_spinner() throws Exception {
        client.setTrending(getGiphies(10));
        this.activity.launchActivity(null);
        {
            onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
            assertThat(activity.getActivity().getAdapter().getItemCount()).isEqualTo(10);
        }
        activity.getActivity().finish();
    }

    @Test
    public void name_shown_when_start() throws Exception {
        this.activity.launchActivity(null);
        {
            onView(withText("Hey man/woman!. Welcome back!"))
                    .inRoot(withDecorView(not(is(activity.getActivity().getWindow().getDecorView()))))
                    .check(matches(isDisplayed()));
        }
    }
}