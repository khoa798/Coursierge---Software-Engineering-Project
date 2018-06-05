package com.jhacks.vrclassroom;

/**
 * Created by PJohn1203 on 6/4/2018.
 */

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;



/* Uses Espresso instrumented unit testing to simulate
   emulator level processes to test the MainActivity
   methods.
*/

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTest {

        //clicks on professor button from main activity
        @Test
        public void change_Activity() {
            onView(withId(R.id.professor)).perform(click());
        }
}
