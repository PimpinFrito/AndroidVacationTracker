package com.example.d308mobileapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.d308mobileapplication.Model.DateValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.text.DateFormat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
@PrepareForTest({DateFormat.class})
public class ExampleUnitTest {

    @Test
    public void testValidDates() {
        //Valid dates
        assertTrue(DateValidator.validDate("01/01/2022"));
        assertTrue(DateValidator.validDate("12/31/2022"));
    }

    @Test
    public void testInvalidDates() {
        //Bad dates
        assertFalse(DateValidator.validDate("00/01/2022")); // Bad month
        assertFalse(DateValidator.validDate("13/01/2022")); // Bad month
        assertFalse(DateValidator.validDate("01/00/2022")); // Bad day
    }

}