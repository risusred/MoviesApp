package org.example.moviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.example.moviesapp.testutils.RecyclerViewMatcher.withRecyclerView;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

@RunWith(AndroidJUnit4.class)
public class MainActivityTestJ {

    @SuppressWarnings("rawtypes")
    @Rule
    public final ActivityScenarioRule rule = new ActivityScenarioRule<>(MainActivity.class);
    private final Context ctx = getInstrumentation().getTargetContext().getApplicationContext();

    @Before public void setUp() {
        @SuppressWarnings("rawtypes") ActivityScenario scenario = rule.getScenario();
        MockitoAnnotations.initMocks(scenario);
    }


    /**
     *
     * We need Internet connection.
     * We make a real connection and check after 5 seconds
     * if we have data response in recycler
     *
     * **/
    @Test
    public void testOnCreateUi(){
        onView(withId(R.id.linear_container_main)).check(matches(isDisplayed()));

        onView(withId(R.id.tv_explanation))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.explanation_msg)));

        onView(withId(R.id.btn_search_by_top_rated))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.show_movies_top_rated)));

        onView(withId(R.id.et_search))
                .check(matches(isDisplayed()))
                .check(matches(withHint(R.string.enter_a_name_to_search)));

        onView(withId(R.id.btn_search_by_title))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.search)));
        onView(withId(R.id.tv_msg_main)).check(matches(isDisplayed()));

        if(getConnectivityAndCheck(ctx)) {
            onView(withId(R.id.tv_msg_main))
                    .check(matches(withText(R.string.loading_ui_state_)));
            onView(withId(R.id.progress_bar_main)).check(matches(isDisplayed()));
        } else {
            onView(withId(R.id.progress_bar_main)).check(matches(not(isDisplayed())));
        }

        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()));

        //wait for web response
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.tv_msg_main))
                .check(matches(withText("")))
                .check(matches(isDisplayed()));

        onView(withId(R.id.progress_bar_main))
                .check(matches(not(isDisplayed())));


        onView(withId(R.id.recycler_view_main))
                .check(matches(isDisplayed()));


        //ITEMS
        onView(withRecyclerView(R.id.recycler_view_main)
                .atPositionOnView(0, R.id.iv_item))
                .check(matches(isDisplayed()));

        onView(withRecyclerView(R.id.recycler_view_main)
                .atPositionOnView(0, R.id.tv_item))
                .check(matches(isDisplayed()));

        onView(allOf(isDisplayed(), withId(R.id.recycler_view_main)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        //DIALOG DETAILS
        onView(withId(R.id.iv_item_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_item_detail)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.linear_container_main)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_search_by_top_rated)).check(matches(isDisplayed()));
        onView(withId(R.id.et_search)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_search_by_title)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_msg_main)).check(matches(isDisplayed()));
        onView(withId(R.id.progress_bar_main)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()));
    }

    private static boolean getConnectivityAndCheck(@NonNull Context ctx) {
        ConnectivityManager cm =
                (ConnectivityManager) ctx.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
        if(cm==null) return false;
        NetworkInfo i = cm.getActiveNetworkInfo();
        if (i==null) return false;
        if (!i.isConnected()) return false;
        if (!i.isAvailable()) return false;
        // noinspection RedundantIfStatement
        if (!i.isConnectedOrConnecting()) return false;
        return true;
    }
}