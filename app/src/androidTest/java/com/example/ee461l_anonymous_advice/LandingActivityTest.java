package com.example.ee461l_anonymous_advice;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by KarimeSaad on 4/24/17.
 */
public class LandingActivityTest {

    @Rule
    public ActivityTestRule<LandingActivity> mActivityRule = new ActivityTestRule<>(LandingActivity.class);


    @Test
    public void profileIntent() throws Exception {

        Intents.init();
        mActivityRule.getActivity().profileIntent();

        Intents.release();

    }
//  TODO
//    @Test
//    public void gotoIM() throws Exception {
//        Intents.init();
//        Intent gotoChat = new Intent(mActivityRule.getActivity(), IM_Activity.class);
//        mActivityRule.getActivity().gotoIM(gotoChat);
//
//        Intents.release();
//
//    }

}