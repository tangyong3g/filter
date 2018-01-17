package com.sny.filter;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.sny.filter", appContext.getPackageName());
//    }

    @Test
    public void random() throws Exception {

        int Atype = 0;
        int BType = 0;

        int count = 10000;
        while (count > 0) {
            if (Math.random() * 100 > 30) {
                BType++;
            } else {
                Atype++;
            }
            count--;
        }
        Log.i("tyler.tang", "Atype:\t" + Atype + "-- Btype:\t" + BType);

        assertTrue(BType > 30);
    }

}
