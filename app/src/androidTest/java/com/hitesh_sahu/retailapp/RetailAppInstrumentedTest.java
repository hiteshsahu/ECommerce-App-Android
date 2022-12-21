package com.hitesh_sahu.retailapp;

import android.content.Context;

import com.hitesh_sahu.retailapp.util.Utils;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RetailAppInstrumentedTest {

    @Test
    public void checkAppVersion() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("1 1.0", Utils.getVersion(appContext));
    }

}
