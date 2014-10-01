package com.example.testjacoco.test_jacoco;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/**
 * Contains general validation methods, that can be used for all validated forms.
 *
 * @author Ivan_Kobzarev on 5/26/2014.
 */
public class ValidationUtil {

    public static final boolean DEFAULT_SINGLE_VALIDATION_EMPTY_EDIT = false;

    public static void applyValidationFailure(Context context, final View failedView, final String failureMessage) {
        if (failedView instanceof EditText) {
            ((EditText) failedView).setError(failureMessage);
        } else {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets action on loose focus.
     *
     * @param validationView validation view
     * @param action         action on event
     */
    public static void setupValidationOnLostFocus(final View validationView, final Action action) {
        validationView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                if (!hasFocus) {
                    action.act();
                }
            }
        });
    }

    /**
     * Sets action on ime NEXT, DONE.
     *
     * @param validationView validation view
     * @param action         action on event
     */
    public static void setupValidationOnIme(final View validationView, final Action action) {
        if (validationView instanceof EditText) {
            ((EditText) validationView).setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(final TextView v, final int actionId, final KeyEvent event) {
                    if (event == null) {
                        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                            action.act();
                        }
                    }
                    return false;
                }
            });
        }
    }

    /**
     * Clear edit text errors on non empty input, need for specific devices, as htc.
     *
     * @param editText where error will be cleared
     */
    public static void addClearErrorNotEmptyTextWatcher(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0 && editText.getError() != null) {
                    editText.setError(null);
                }
            }
        });
    }

    /**
     * As previous method, but for bulk of edit texts.
     *
     * @param editTexts edit texts
     */
    public static void addClearErrorNotEmptyTextWatcher(final EditText... editTexts) {
        for (final EditText editText : editTexts) {
            ValidationUtil.addClearErrorNotEmptyTextWatcher(editText);
        }
    }

    /**
     * Standalone validation specific view.
     *
     * @param context                      context for applying validation failure
     * @param validator                    validator to validate
     * @param validationView               view
     * @param singleValidationEmptyEditOpt should validation processed on empty view, default is {@link #DEFAULT_SINGLE_VALIDATION_EMPTY_EDIT}
     */
    public static void processSingleViewValidation(final Context context, Validator validator, final View validationView, Boolean... singleValidationEmptyEditOpt) {
        boolean singleValidationEmptyEdit = (singleValidationEmptyEditOpt == null || singleValidationEmptyEditOpt.length == 0) ? DEFAULT_SINGLE_VALIDATION_EMPTY_EDIT : singleValidationEmptyEditOpt[0];

        /**
         * If validation view is edit text empty we stop processing if {@link SINGLE_VALIDATION_EMPTY_EDIT} false.
         */
        if (validationView instanceof EditText) {
            EditText editText = (EditText) validationView;

            if (TextUtils.isEmpty(editText.getText()) && !singleValidationEmptyEdit) {
                return;
            }
        }

        validator.validateSingle(validationView,
                new Validator.ValidationSingleListener() {
                    @Override
                    public void onValidationSucceeded() {
                    }

                    @Override
                    public void onValidationSingleFailed(final Rule rule) {
                        ValidationUtil.applyValidationFailure(context, validationView, rule.getFailureMessage());
                    }
                }
        );
    }

    /**
     * Apply validation failure to validation view.
     *
     * @param context                     context
     * @param validator                   validator
     * @param singleValidationEventsFlags flags to trigger validation
     * @param views                       validation views
     */
    public static void applySingleValidationListeners(final Context context, final Validator validator, int singleValidationEventsFlags, final List<View> views) {

        if (singleValidationEventsFlags == 0) {
            return;
        }

        for (final View view : views) {
            Action validationAction = new ValidationUtil.Action() {
                @Override
                public void act() {
                    ValidationUtil.processSingleViewValidation(context, validator, view);
                }
            };

            if (FlagUtil.hasFlags(singleValidationEventsFlags, SingleValidationEvents.ON_IME)) {
                setupValidationOnIme(view, validationAction);
            }
            if (FlagUtil.hasFlags(singleValidationEventsFlags, SingleValidationEvents.ON_FOCUS_LOST)) {
                setupValidationOnLostFocus(view, validationAction);
            }
        }
    }

    /**
     * Events for validation processing.
     */
    public static class SingleValidationEvents {
        /**
         * Flag for IME Next, Done actions.
         */
        public static final int ON_IME = 1;
        /**
         * Flag for focus lost action.
         */
        public static final int ON_FOCUS_LOST = 2;
    }

    /**
     * Interface defines only as general callback.
     */
    public static interface Action {
        /**
         * General method of action.
         */
        public void act();
    }
}
