package com.openclassrooms.realestatemanager;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.ui.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UtilsTest {
    @Test
    public void isInternetAvailableReturnTrue() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        boolean isInternetAvailable = Utils.isInternetAvailable(context);
        assertTrue(isInternetAvailable);
    }
}
