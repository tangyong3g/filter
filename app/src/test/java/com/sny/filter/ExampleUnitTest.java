package com.sny.filter;

import android.icu.text.MessagePattern;
import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void randomone() throws Exception {

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