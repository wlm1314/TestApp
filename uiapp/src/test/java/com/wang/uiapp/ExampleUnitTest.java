package com.wang.uiapp;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        System.out.print(power2(2, 4) + "\r\n");
        System.out.print(power(2, 2) + "\r\n");
    }

    static long power(long a, int n) {
        long r = 1;
        int t = 0;
        while (n >= 1) {
            if ((n & 1) == 1) {
                r *= a;
            }
            a *= a;
            t++;
            n = n >> 1;
        }
        System.out.print(t + "times \r\n");
        return r;
    }

    static long power2(long a, int n) {
        long r = 1;
        int t = 0;
        while (n-- >= 1) {
            r =r *  a;
            t++;
        }
        System.out.print(t + "times \r\n");
        return r;
    }
}