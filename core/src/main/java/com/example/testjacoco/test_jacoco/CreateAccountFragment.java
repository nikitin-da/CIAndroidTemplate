/*
 * Copyright (c) 2014. EPAM Systems. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * Neither the name of the EPAM Systems, Inc.  nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * See the NOTICE file and the LICENSE file distributed with this work
 * for additional information regarding copyright ownership and licensing
 */

package com.example.testjacoco.test_jacoco;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.testjacoco.testjacoco.R;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import java.util.Arrays;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * @author Ivan_Kobzarev on 3/26/14.
 */

/**
 * Article details fragment with scroll, bar with '
 * for later' functions, gets article id from
 * activity bundle and applies it to ui.
 */
public class CreateAccountFragment extends BaseFragment {

    /**
     * True if validation processed on soft keyboard ime 'Next', 'Done' events.
     */
    private static final boolean SINGLE_VALIDATION_ON_IME = true;

    /**
     * True if validation processed on validation views loose focus.
     */
    private static final boolean SINGLE_VALIDATION_ON_LOST_FOCUS = true;

    /**
     * True if single validation after ime next, changing focus should be processed on empty edits.
     */
    private static final boolean SINGLE_VALIDATION_EMPTY_EDIT = false;

    @Required(order = 0, messageResId = R.string.createaccount_input_email_required_validation_fail)
    @Email(order = 1, messageResId = R.string.createaccount_input_email_validation_fail)
    @InjectView(R.id.createaccount_input_email_edit)
    private EditText mEmailEdit;

    @Required(order = 2, messageResId = R.string.createaccount_input_name_required_validation_fail)
    @InjectView(R.id.createaccount_input_name_edit)
    private EditText mNameEdit;

    @Required(order = 3, messageResId = R.string.createaccount_input_surname_required_validation_fail)
    @InjectView(R.id.createaccount_input_surname_edit)
    private EditText mSurnameEdit;

    @Password(order = 4, messageResId = R.string.createaccount_input_password_required_validation_fail)
    @TextRule(order = 5, minLength = 6, messageResId = R.string.createaccount_input_password_validation_fail)
    @InjectView(R.id.createaccount_input_password_edit)
    private EditText mPasswordEdit;

    @ConfirmPassword(order = 7, messageResId = R.string.createaccount_input_repassword_confirm_validation_fail)
    @InjectView(R.id.createaccount_input_password_re_edit)
    private EditText mPasswordReEdit;

    @InjectView(R.id.createaccount_input_question_spinner)
    private Spinner mQuestionSpinner;

    @Required(order = 8, messageResId = R.string.createaccount_input_question_answer_required_validation_fail)
    @InjectView(R.id.createaccount_input_question_answer_edit)
    private EditText mQuestionAnswerEdit;

    private Validator mValidator;
    @InjectView(R.id.createaccount_input_save_button)
    private View mSaveButton;

    /**
     * Cache map for listeners on LOST_FOCUS and IME.
     */
    private View[] mValidationViews;
    //    private AuthenticationProvider mAuthenticationProvider;
    private AlertDialog mAlertErrorDialog;
    private DialogUtil mDialogUtil;

    public CreateAccountFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account_create, null);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        mEmailEdit = (EditText) view.findViewById(R.id.createaccount_input_email_edit);
//        mNameEdit = (EditText) view.findViewById(R.id.createaccount_input_name_edit);
//        mSurnameEdit = (EditText) view.findViewById(R.id.createaccount_input_surname_edit);
//        mPasswordEdit = (EditText) view.findViewById(R.id.createaccount_input_password_edit);
//        mPasswordReEdit = (EditText) view.findViewById(R.id.createaccount_input_password_re_edit);
//        mQuestionSpinner = (Spinner) view.findViewById(R.id.createaccount_input_question_spinner);
//        mQuestionAnswerEdit = (EditText) view.findViewById(R.id.createaccount_input_question_answer_edit);
//        mSaveButton = view.findViewById(R.id.createaccount_input_save_button);

        init();
    }

    private void init() {
        mValidator = new Validator(this);
        CreateAccountValidationListener fullValidationListener = new CreateAccountValidationListener();
        mValidator.setValidationListenerAllFailures(new Validator.ValidationListenerAllFailures() {

            @Override
            public void onValidationSucceeded() {
                clearValidationErrors();

                processCreateAccount();
            }

            @Override
            public void onValidationFailed(final List<Validator.ViewRulePair> viewRulePairs) {
                for (Validator.ViewRulePair viewRulePair : viewRulePairs) {
                    ValidationUtil.applyValidationFailure(getActivity(), viewRulePair.view, viewRulePair.rule.getFailureMessage());
                }

                viewRulePairs.get(0).view.requestFocus();
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mValidator.validateAllFailures();
            }
        });

        initEditsErrorClearListener();

        initQuestions();

        initValidationViews(mEmailEdit, mNameEdit, mSurnameEdit, mPasswordEdit, mPasswordReEdit, mQuestionSpinner, mQuestionAnswerEdit);

        mDialogUtil = new DialogUtil(getActivity());
    }

    private void initEditsErrorClearListener() {
        /**
         * Clear error listeners due to defects with hack-vendors, in out case HTC not understand noSuggestion inputType.
         */
        ValidationUtil.addClearErrorNotEmptyTextWatcher(
                mEmailEdit, mNameEdit, mSurnameEdit, mPasswordEdit, mPasswordReEdit, mQuestionAnswerEdit
        );
    }

    private void initQuestions() {
        /**
         * Here will be request for some service that will return questions,
         * but it's got from resources string array.
         */
//        List<String> questions = Arrays.asList(getActivity().getResources().getStringArray(R.array.createaccount_questions));
//        applyQuestions(questions);
    }

    /**
     * Apply questions to view.
     *
     * @param questions list of strings
     */
    private void applyQuestions(List<String> questions) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, questions);
        mQuestionSpinner.setAdapter(arrayAdapter);
        if (!questions.isEmpty()) {
            mQuestionSpinner.setSelection(0);
        }
    }

    private void initValidationViews(final View... validationViews) {

        mValidationViews = validationViews;

        int flags = ((SINGLE_VALIDATION_ON_IME) ? ValidationUtil.SingleValidationEvents.ON_IME : 0)
                | ((SINGLE_VALIDATION_ON_LOST_FOCUS) ? ValidationUtil.SingleValidationEvents.ON_FOCUS_LOST : 0);

        ValidationUtil.applySingleValidationListeners(getActivity(), mValidator, flags, Arrays.asList(validationViews));
    }

    //
//    /**
//     * Validation listener for validator on 'Save' button.
//     */
    private class CreateAccountValidationListener implements Validator.ValidationListenerAllFailures {

        @Override
        public void onValidationSucceeded() {
            clearValidationErrors();

            processCreateAccount();
        }

        @Override
        public void onValidationFailed(final List<Validator.ViewRulePair> viewRulePairs) {
            for (Validator.ViewRulePair viewRulePair : viewRulePairs) {
                ValidationUtil.applyValidationFailure(getActivity(), viewRulePair.view, viewRulePair.rule.getFailureMessage());
            }

            viewRulePairs.get(0).view.requestFocus();
        }
    }

    /**
     * Clear validation error messages when full validation succeeded.
     */
    public void clearValidationErrors() {
        for (View validationView : mValidationViews) {
            if (validationView instanceof EditText) {
                ((EditText) validationView).setError(null);
            }
        }
    }

    /**
     * Called on save button click in case all fields validated.
     */
    private void processCreateAccount() {
        String email = mEmailEdit.getText().toString();
        String name = mNameEdit.getText().toString();
        String surname = mSurnameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        long questionId = mQuestionSpinner.getSelectedItemId();
        String questionAnswer = mQuestionAnswerEdit.getText().toString();
//
//        Account account = new Account();
//        account.setFirstName(name);
//        account.setSurname(surname);
//        account.setEmail(email);
//        account.setPassword(password);
//        account.setSecurityQuestionId(questionId);
//        account.setSecurityQuestionAnswer(questionAnswer);
//
//        if (mAuthenticationProvider == null) {
//            mAuthenticationProvider = new AuthenticationProvider(getActivity());
//        }
//
//        mDialogUtil.showWaitDialog(getString(R.string.signup_wait_message));
//
//        mAuthenticationProvider.createAccount(account, new Response.Listener<AccessToken>() {
//            @Override
//            public void onResponse(final AccessToken response) {
//                mDialogUtil.dismissWaitDialog();
//                Toast.makeText(getActivity(), getString(R.string.createaccount_alertdialog_ok_message), Toast.LENGTH_LONG).show();
//                onCreateAccountSuccess();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(final VolleyError error) {
//                mDialogUtil.dismissWaitDialog();
//
//                String message = getString(R.string.createaccount_alertdialog_error_message);
//
//                /**
//                 * 400 response code - Account with such email/name already exist.
//                 */
//                if (error.networkResponse != null && error.networkResponse.statusCode == 400) {
//                    message = getString(R.string.createaccount_alertdialog_error_account_already_exist_message);
//                }
//
//                mAlertErrorDialog = new AlertDialog.Builder(getActivity())
//                        .setCancelable(false)
//                        .setTitle(getString(R.string.createaccount_alertdialog_ok_title))
//                        .setMessage(message)
//                        .setPositiveButton(getActivity().getString(R.string.alertdialog_ok), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(final DialogInterface dialog, final int which) {
//                                mAlertErrorDialog.dismiss();
//                            }
//                        })
//                        .show();
//            }
//        });
    }

//    private void onCreateAccountSuccess() {
//        ActivityUtil.startRootActivity(getActivity(), HomeScreenActivity.class);
//        getActivity().finish();
//    }
}
