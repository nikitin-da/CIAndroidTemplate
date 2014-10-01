package com.example.testjacoco.test_jacoco.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;

import com.example.testjacoco.test_jacoco.MainActivity;
import com.example.testjacoco.testjacoco.R;

import junit.framework.Assert;

/**
 * @author Ivan_Kobzarev on 5/14/2014.
 */

/**
 * Test signup screen edit texts validation.
 */
@MediumTest
public class SignupValidationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mActivity;
    private Instrumentation mInstrumentation;
    private View mContainer;

    public SignupValidationTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mInstrumentation = getInstrumentation();
        mActivity = getActivity();
        mContainer = mActivity.findViewById(R.id.container);
    }


    public void test1() {
        Assert.assertTrue(true);
    }

    public void test2() {
        Assert.assertTrue(true);

        mContainer.performClick();
        Assert.assertEquals(mContainer.getVisibility(), View.VISIBLE);
    }

}
