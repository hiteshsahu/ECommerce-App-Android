package com.hitesh_sahu.retailapp;

import com.hitesh_sahu.retailapp.model.entities.Money;
import com.hitesh_sahu.retailapp.util.Utils;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsUnitTest {

    @Test
    public void durationConvertFormat() {
        String formattedDuration = Utils.getDuration(100000);
        assertEquals(formattedDuration, "01:40");
    }

}