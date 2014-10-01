package com.example.testjacoco.test_jacoco.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.EditText;

import com.example.testjacoco.test_jacoco.CreateAccountActivity;
import com.example.testjacoco.testjacoco.R;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ivan_Kobzarev on 5/14/2014.
 */

/**
 * Test signup screen edit texts validation.
 */
@MediumTest
public class CreateAccountTest extends ActivityInstrumentationTestCase2<CreateAccountActivity> {

    public CreateAccountTest() {
        super(CreateAccountActivity.class);
    }

    public void test1() {
        Assert.assertTrue(true);
    }

    public void test2() {
        Assert.assertTrue(true);
    }

    /** TODO: This test checks full validation cycle:
     * [UI]edits -> validator -> validation callback -> [UI]edits error messages
     *
     * In future will be more appropriate to extract part `validator -> validation callback` to
     * separate unit test (Robolectric), that also will test more variants.
     */

    /**
     * Simple valid email.
     */
    private static final String VALID_EMAIL = "test@test.test";
    /**
     * Simple valid name.
     */
    private static final String VALID_NAME = "name";
    /**
     * Simple valid surname.
     */
    private static final String VALID_SURNAME = "surname";
    /**
     * Simple valid password.
     */
    private static final String VALID_PASSWORD = "password";

    /**
     * Tested texts for email edit text, that should fail email validation.
     */
    private static final String[] TEST_EMAIL_VALIDATION_SHOULD_FAILS = new String[]{
            "testAttest.com", "123test@com", " --l@letru", "test", "@test.com"
    };

    /**
     * Tested texts for email edit text, that should satisfy email validation.
     */
    private static final String[] TEST_EMAIL_VALIDATION_SHOULD_PASS = new String[]{
            "valid@mail.ru", "valid23@name.tt", "Test_Tested@epam.com", "test-test@test.test"
    };

    /**
     * Tested texts for password edit text, that should fail password validation - not less than 6 chars.
     */
    private static final String[] TEST_PASSWORD_VALIDATION_SHOULD_FAILS = new String[]{
            "pass", "qwert", "t  ", "  "
    };

    /**
     * Tested texts for password edit text, that should satisfy password validation.
     */
    private static final String[] TEST_PASSWORD_VALIDATION_SHOULD_PASS = new String[]{
            "password", "p@$$w0rd", "Test_Tested@epam.com", "------"
    };

    private CreateAccountActivity mActivity;
    private EditText mEmailEdit;
    private EditText mNameEdit;
    private EditText mSurnameEdit;
    private EditText mPasswordEdit;
    private EditText mPasswordReEdit;
    private EditText mQuestionAnswerEdit;

    private EditText[] editTexts;

    private View mSaveButton;
    private Instrumentation mInstrumentation;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        mInstrumentation = getInstrumentation();
        mActivity = getActivity();

        mEmailEdit = (EditText) mActivity.findViewById(R.id.createaccount_input_email_edit);
        mNameEdit = (EditText) mActivity.findViewById(R.id.createaccount_input_name_edit);
        mSurnameEdit = (EditText) mActivity.findViewById(R.id.createaccount_input_surname_edit);
        mPasswordEdit = (EditText) mActivity.findViewById(R.id.createaccount_input_password_edit);
        mPasswordReEdit = (EditText) mActivity.findViewById(R.id.createaccount_input_password_re_edit);
        mQuestionAnswerEdit = (EditText) mActivity.findViewById(R.id.createaccount_input_question_answer_edit);
        mSaveButton = mActivity.findViewById(R.id.createaccount_input_save_button);

        editTexts = new EditText[]{mEmailEdit, mNameEdit, mSurnameEdit, mPasswordReEdit, mPasswordReEdit, mQuestionAnswerEdit};
    }

    private void setTextsAndValidate(final EditTexts texts, final ValidateErrorTextCallback validateErrorTextCallback) {
        clearEditTexts();
        clearEditTextsErrors();

        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                applyEditTexts(texts);

                mSaveButton.performClick();

                validateErrorTextCallback.onErrorTexts(
                        new ErrorTexts(
                                mEmailEdit.getError(),
                                mNameEdit.getError(),
                                mSurnameEdit.getError(),
                                mPasswordEdit.getError(),
                                mPasswordReEdit.getError(),
                                mQuestionAnswerEdit.getError()
                        )
                );
            }
        });
    }

//    /**
//     * Tests checks error text on empty name.
//     */
//    public void testNameRequiredValidation() {
//        setTextsAndValidate(
//                new EditTexts(VALID_EMAIL, ""),
//                new ValidateErrorTextCallback() {
//                    @Override
//                    public void onErrorTexts(final ErrorTexts errorTexts) {
//                        Assert.assertEquals(errorTexts.nameError, mActivity.getString(R.string.createaccount_input_name_required_validation_fail));
//                    }
//                }
//        );
//    }
//
//    /**
//     * Tests checks error text on empty surname.
//     */
//    public void testSurnameRequiredValidation() {
//        setTextsAndValidate(
//                new EditTexts(VALID_EMAIL, VALID_NAME, ""),
//                new ValidateErrorTextCallback() {
//                    @Override
//                    public void onErrorTexts(final ErrorTexts errorTexts) {
//                        Assert.assertEquals(errorTexts.surnameError, mActivity.getString(R.string.createaccount_input_surname_required_validation_fail));
//                    }
//                }
//        );
//    }
//
//    /**
//     * Tests checks error text on empty email.
//     */
//    public void testEmailRequired() {
//        setTextsAndValidate(
//                new EditTexts(""),
//                new ValidateErrorTextCallback() {
//                    @Override
//                    public void onErrorTexts(final ErrorTexts errorTexts) {
//                        Assert.assertEquals(errorTexts.emailError, mActivity.getString(R.string.createaccount_input_email_required_validation_fail));
//                    }
//                }
//        );
//    }
//
//    /**
//     * Tests checks no error text on valid emails {@link #TEST_EMAIL_VALIDATION_SHOULD_PASS}.
//     */
//    public void testEmailValidationPasses() {
//        for (String email : TEST_EMAIL_VALIDATION_SHOULD_PASS) {
//            setTextsAndValidate(
//                    new EditTexts(email, VALID_NAME, ""),
//                    new ValidateErrorTextCallback() {
//                        @Override
//                        public void onErrorTexts(final ErrorTexts errorTexts) {
//                            Assert.assertNull(errorTexts.emailError);
//                        }
//                    }
//            );
//        }
//    }
//
//    /**
//     * Tests checks error text on bad emails {@link #TEST_EMAIL_VALIDATION_SHOULD_FAILS}.
//     */
//    public void testEmailValidationFails() {
//        for (String email : TEST_EMAIL_VALIDATION_SHOULD_FAILS) {
//            setTextsAndValidate(
//                    new EditTexts(email, VALID_NAME, ""),
//                    new ValidateErrorTextCallback() {
//                        @Override
//                        public void onErrorTexts(final ErrorTexts errorTexts) {
//                            Assert.assertEquals(errorTexts.emailError, mActivity.getString(R.string.createaccount_input_email_validation_fail));
//                        }
//                    }
//            );
//        }
//    }
//
//    /**
//     * Tests checks no error text on valid emails {@link #TEST_EMAIL_VALIDATION_SHOULD_PASS}.
//     */
//    public void testPasswordValidationPasses() {
//        for (String pass : TEST_PASSWORD_VALIDATION_SHOULD_PASS) {
//            setTextsAndValidate(
//                    new EditTexts(VALID_EMAIL, VALID_NAME, VALID_SURNAME, pass),
//                    new ValidateErrorTextCallback() {
//                        @Override
//                        public void onErrorTexts(final ErrorTexts errorTexts) {
//                            Assert.assertNull(errorTexts.passwordError);
//                        }
//                    }
//            );
//        }
//    }
//
//    /**
//     * Tests checks error text on bad passwords {@link #TEST_PASSWORD_VALIDATION_SHOULD_FAILS}.
//     */
//    public void testPasswordValidationFails() {
//        for (String pass : TEST_PASSWORD_VALIDATION_SHOULD_FAILS) {
//            setTextsAndValidate(
//                    new EditTexts(VALID_EMAIL, VALID_NAME, VALID_SURNAME, pass),
//                    new ValidateErrorTextCallback() {
//                        @Override
//                        public void onErrorTexts(final ErrorTexts errorTexts) {
//                            Assert.assertEquals(errorTexts.passwordError, mActivity.getString(R.string.createaccount_input_password_validation_fail));
//                        }
//                    }
//            );
//        }
//    }
//
//    /**
//     * Tests checks no error text on equal password and passwordre edits.
//     */
//    public void testPasswordAndConfirmationEqual() {
//        setTextsAndValidate(
//                new EditTexts(VALID_EMAIL, VALID_NAME, VALID_SURNAME, VALID_PASSWORD, VALID_PASSWORD),
//                new ValidateErrorTextCallback() {
//                    @Override
//                    public void onErrorTexts(final ErrorTexts errorTexts) {
//                        Assert.assertNull(errorTexts.passwordError);
//                        Assert.assertNull(errorTexts.passwordReError);
//                    }
//                }
//        );
//    }
//
//    /**
//     * Test checks error message on not equal password and password re edits.
//     */
//    public void testPasswordAndConfirmationNotEqual() {
//        setTextsAndValidate(
//                new EditTexts(VALID_EMAIL, VALID_NAME, VALID_SURNAME, VALID_PASSWORD, "!" + VALID_PASSWORD),
//                new ValidateErrorTextCallback() {
//                    @Override
//                    public void onErrorTexts(final ErrorTexts errorTexts) {
//                        Assert.assertEquals(errorTexts.passwordReError, mActivity.getString(R.string.createaccount_input_repassword_confirm_validation_fail));
//                    }
//                }
//        );
//    }

    public void testAnswerRequiredValidation() {
        setTextsAndValidate(
                new EditTexts(VALID_EMAIL, VALID_NAME, VALID_SURNAME, VALID_PASSWORD, VALID_PASSWORD, ""),
                new ValidateErrorTextCallback() {
                    @Override
                    public void onErrorTexts(final ErrorTexts errorTexts) {
                        Assert.assertEquals(errorTexts.questionAnswerError, mActivity.getString(R.string.createaccount_input_question_answer_required_validation_fail));
                    }
                }
        );
    }

    public static class ErrorTexts {
        public CharSequence emailError;
        public CharSequence nameError;
        public CharSequence surnameError;
        public CharSequence passwordError;
        public CharSequence passwordReError;
        public CharSequence questionAnswerError;

        private ErrorTexts(final CharSequence emailError, final CharSequence nameError, final CharSequence surnameError, final CharSequence passwordError, final CharSequence passwordReError, final CharSequence questionAnswerError) {
            this.emailError = emailError;
            this.nameError = nameError;
            this.surnameError = surnameError;
            this.passwordError = passwordError;
            this.passwordReError = passwordReError;
            this.questionAnswerError = questionAnswerError;
        }
    }

    public static class EditTexts {
        public String emailText;
        public String nameText;
        public String surnameText;
        public String passwordText;
        public String questionAnswerText;
        public String passwordReText;

        public EditTexts(final String... texts) {
            if (texts == null) {
                return;
            }

            for (int i = 0; i < texts.length; i++) {
                String text = texts[i];
                switch (i) {
                    case 0:
                        emailText = text;
                        break;
                    case 1:
                        nameText = text;
                        break;
                    case 2:
                        surnameText = text;
                        break;
                    case 3:
                        passwordText = text;
                        break;
                    case 4:
                        passwordReText = text;
                        break;
                    case 5:
                        questionAnswerText = text;
                }
            }
        }

        public EditTexts(final String emailText, final String nameText, final String surnameText, final String passwordText, final String passwordReText, final String questionAnswerText) {
            this.emailText = emailText;
            this.nameText = nameText;
            this.surnameText = surnameText;
            this.passwordText = passwordText;
            this.passwordReText = passwordReText;
            this.questionAnswerText = questionAnswerText;
        }
    }

    /**
     * Interface definition for a callback to be invoked after validation.
     */
    private interface ValidateErrorTextCallback {
        /**
         * Called after validation.
         *
         * @param errorTexts contains edits all error texts from
         */
        public void onErrorTexts(final ErrorTexts errorTexts);
    }

    private CharSequence[] allCharSequencesExcept(ErrorTexts errorTexts, CharSequence exceptedCharSequence) {
        List<CharSequence> all = Arrays.asList(
                errorTexts.emailError,
                errorTexts.nameError,
                errorTexts.surnameError,
                errorTexts.passwordError,
                errorTexts.passwordReError,
                errorTexts.questionAnswerError
        );

        List<CharSequence> ret = new ArrayList<CharSequence>();
        for (CharSequence e : all) {
            if (e != exceptedCharSequence) {
                ret.add(e);
            }
        }

        CharSequence[] retArray = new CharSequence[ret.size()];
        ret.toArray(retArray);

        return retArray;
    }

    /**
     * Clears all edit texts.
     */
    private void clearEditTexts() {
        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                forEachAct(editTexts, new Action<EditText>() {
                    @Override
                    public void act(final EditText editText) {
                        editText.setText("");
                    }
                });
            }
        });
    }

    /**
     * Clears all edit errors.
     */
    private void clearEditTextsErrors() {
        mInstrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                forEachAct(editTexts, new Action<EditText>() {
                    @Override
                    public void act(final EditText editText) {
                        editText.setError(null);
                    }
                });
            }
        });
    }

    /**
     * Sets appropriate text to each edit.
     *
     * @param texts contains texts to set
     */
    private void applyEditTexts(final EditTexts texts) {
        setTextIfNotNull(mEmailEdit, texts.emailText);
        setTextIfNotNull(mNameEdit, texts.nameText);
        setTextIfNotNull(mSurnameEdit, texts.surnameText);
        setTextIfNotNull(mPasswordEdit, texts.passwordText);
        setTextIfNotNull(mPasswordReEdit, texts.passwordReText);
        setTextIfNotNull(mQuestionAnswerEdit, texts.questionAnswerText);
    }

    private void setTextIfNotNull(EditText editText, String text) {
        if (text != null) {
            editText.setText(text);
        }
    }

    private void assertNullForEach(Object... shouldBeNull) {
        if (shouldBeNull != null) {
            for (Object o : shouldBeNull) {
                Assert.assertNull(o);
            }
        }
    }

    private static interface Action<T> {
        public void act(T o);
    }

    private <T> void forEachAct(T[] arr, Action<T> action) {
        for (T o : arr) {
            action.act(o);
        }
    }

}
