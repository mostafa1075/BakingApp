package com.mostafa1075.bakingapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import  android.support.v7.widget.Toolbar;

import com.mostafa1075.bakingapp.ui.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RecipeSelectTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule  =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecipe_OpensCorrectDetail(){
        onData(anything()).inAdapterView(withId(R.id.recipes_recyclerview)).atPosition(0).perform(click());
    }
}
