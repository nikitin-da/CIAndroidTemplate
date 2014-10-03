package com.mobsandgeeks.saripaar.tests.unit;

import android.test.suitebuilder.annotation.SmallTest;

import com.mobsandgeeks.saripaar.Validator;

import junit.framework.TestCase;

/**
 * ApplicationSessionManager test.
 */
@SmallTest
public class ValidatePropertySetterTest extends TestCase {

    /**
     *  Simple test.
     *  Added, because CI report with JaCoCo failed,
     *  when there is no tests in submodule.
     */
    public void testPropertySetter() {
        Validator validator = new Validator(this);
        String name = "property_name";
        String firstValue = "first_value";
        String secondValue = "second_value";

        validator.setProperty(name, firstValue);
        assertEquals(firstValue, validator.getProperty(name));

        validator.setProperty(name, secondValue);
        assertEquals(secondValue, validator.getProperty(name));
    }
}