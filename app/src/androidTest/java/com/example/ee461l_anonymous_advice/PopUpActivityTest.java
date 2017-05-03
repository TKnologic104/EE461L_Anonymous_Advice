package com.example.ee461l_anonymous_advice;

/**
 * Created by KarimeSaad on 4/23/17.
 */

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.FileOutputStream;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(AndroidJUnit4.class)

public class PopUpActivityTest {

    @Rule
    public ActivityTestRule<PopUpActivity> mActivityRule = new ActivityTestRule<>(PopUpActivity.class);

    @Test
    public void testIMIntent(){

        Intents.init();
        String channelId = "test";
        mActivityRule.getActivity().gotoIM(channelId);

        Intents.release();

    }

    @Test
    public void testOtherMethod2(){
        int num1 = 5;
        int num2 = 1;
        assertEquals((num1+num2), 6);
    }
}