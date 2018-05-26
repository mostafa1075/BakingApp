package com.mostafa1075.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mostafa1075.bakingapp.ui.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeSelectTest {

    private static final String RECIPE_NAME = "Nutella Pie";
    private static final String NUTELLA_PIE_STEP2 = "Prep the cookie crust.";

    @Rule
    public final ActivityTestRule<MainActivity> mActivityTestRule  =
            new ActivityTestRule<>(MainActivity.class);
    private CountingIdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void onRun_RecipesLoaded(){
        onView(withText(RECIPE_NAME)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipe_OpensCorrectDetail(){
        onView(withId(R.id.recipes_recyclerview))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText(NUTELLA_PIE_STEP2)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource(){
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }

}
