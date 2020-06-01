package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
//import static androidx.test.espresso.matcher.ViewMatchers.assertThat;

public class UtilsTest {
    @Test
    public void isInsternetAvailableReturnTrue() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        boolean isInternetAvailable = Utils.isInternetAvailable(context);
        assertTrue(isInternetAvailable);
    }
}
