@file:Suppress("RedundantIf")

package org.example.moviesapp

import android.content.Context
import android.net.ConnectivityManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase
import org.example.moviesapp.testutils.RecyclerViewMatcher.withRecyclerView
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class MainActivityTestK : TestCase() {

    @get: Rule
    var rule: ActivityScenarioRule<*> = ActivityScenarioRule(MainActivity::class.java)
    private val context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext

    @Before
    public override fun setUp() {
        super.setUp()
        val scenario = rule.scenario
        MockitoAnnotations.initMocks(scenario)
    }


    /**
     *
     * We need Internet connection.
     * We make a real connection and check after 5 seconds
     * if we have data response in recycler
     *
     */
    @Test
    fun testOnCreateUi() {

        onView(withId(R.id.tv_explanation))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.explanation_msg)))

        onView(withId(R.id.btn_search_by_top_rated))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.show_movies_top_rated)))

        onView(withId(R.id.et_search))
                .check(matches(isDisplayed()))
                .check(matches(withHint(R.string.enter_a_name_to_search)))

        onView(withId(R.id.btn_search_by_title))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.search)))

        onView(withId(R.id.tv_msg_main)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar_main)).check(matches(isDisplayed()))
        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()))


        if (!getConnectivityAndCheck(context)) return

        //wait for web response
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.tv_msg_main))
                .check(matches(withText("")))
                .check(matches(isDisplayed()))

        onView(withId(R.id.progress_bar_main))
                .check(matches(not(isDisplayed())))

        onView(withId(R.id.recycler_view_main))
                .check(matches(isDisplayed()))

        //ITEMS
        onView(withRecyclerView(R.id.recycler_view_main)
                .atPositionOnView(0, R.id.iv_item))
                .check(matches(isDisplayed()))

        onView(withRecyclerView(R.id.recycler_view_main)
                .atPositionOnView(0, R.id.tv_item))
                .check(matches(isDisplayed()))

        onView(allOf(isDisplayed(), withId(R.id.recycler_view_main)))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))


        //DIALOG DETAILS
        onView(withId(R.id.iv_item_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_item_detail)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.linear_container_main)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_search_by_top_rated)).check(matches(isDisplayed()))
        onView(withId(R.id.et_search)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_search_by_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_msg_main)).check(matches(isDisplayed()))
        onView(withId(R.id.progress_bar_main)).check(matches(Matchers.not(isDisplayed())))
        onView(withId(R.id.recycler_view_main)).check(matches(isDisplayed()))
    }

    private fun getConnectivityAndCheck(ctx: Context): Boolean {
        val cm = ctx.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val i = cm.activeNetworkInfo ?: return false
        if (!i.isConnected) return false
        if (!i.isAvailable) return false
        // noinspection RedundantIfStatement
        return if (!i.isConnectedOrConnecting) false else true
    }
}