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

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testjacoco.testjacoco.R;

import roboguice.activity.RoboFragmentActivity;

/**
 * Base activity for sharing general functionality.
 * Now it contains base motion gestures.
 *
 * @author Ivan_Kobzarev on 4/10/2014.
 */
public class BaseActivity extends RoboFragmentActivity {

    /**
     * True if we enable gesture detector callbacks.
     */
    private boolean mGestureDetectorEnabled = false;
    private GestureDetector mGestureDetector;
    private DisplayMetrics mDisplayMetrics;

    private TextView mActionBarTitleText;
    private View mActionBarHomeContent;
    private TextView mActionBarHomeText;
    private View mActionBarRightStubForCenterTitle;

    /**
     * True if assume fling to right offscreen gesture as back button.
     */
    private boolean mOffScreenFlingToRightAsBack = false;

    /**
     * True if we support multi touch motion events.
     * <p/>
     * We should have this option due to defects based on multitouch:
     * <p/>
     * Click tile and multi touch hamburger menu open
     * https://moneysupermarket.mingle.thoughtworks.com/projects/msm_mobile_app/cards/519
     * <p/>
     * Next/Previous on Article screen
     * https://moneysupermarket.mingle.thoughtworks.com/projects/msm_mobile_app/cards/659
     */
    private boolean mMultiTouchDisabled = false;

    /**
     * If this is turned on There will be sync back button and activity transition,
     * It means that if user simultaneously touch hardware 'back' and initiate something like
     * startActivity - only first action will be handled. If back was early - back handled, and no
     * deeper activity started; else deeper activity started and previous will not receive back.
     */
    private boolean mTransitionAndBackSync = false;

    /**
     * True if transition was initiated.
     */
    private boolean mTransitionInitiated = false;

    /**
     * For transition and back sync storage field about if back was pressed.
     */
    private boolean mBackPressed = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);

        initActionBar();

        if (getActionBarTitle() != null) {
            setTitle(getActionBarTitle());
        }

        mGestureDetector = new GestureDetector(this, new GestureDetectorListener());
    }

    /**
     * Default pending transition animations.
     */
    public void overridePendingTransitionOut() {
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    /**
     * Override this to set action bar title, overrided due to using custom action bar layout.
     * By default it returns {@link android.app.Activity#getTitle()} that is android:label
     * attribute from manifest file.
     *
     * @return action bar title string
     */
    private String getActionBarTitle() {
        CharSequence title = getTitle();
        if (title != null) {
            return title.toString();
        }

        return null;
    }

    /**
     * Inits action bar with custom layout.
     */
    private void initActionBar() {
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.action_bar_custom, null, false);
        mActionBarTitleText = (TextView) actionBarView.findViewById(R.id.action_bar_title);
        mActionBarHomeContent = actionBarView.findViewById(R.id.actionbar_home_content);
        mActionBarHomeText = (TextView) actionBarView.findViewById(R.id.actionbar_home_text);
        mActionBarRightStubForCenterTitle = actionBarView.findViewById(R.id.action_bar_space_stub);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

        getActionBar().setCustomView(actionBarView, layoutParams);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
    }

    /**
     * Enables view stub for centering title, in case no 'action' menu items.
     */
    protected void initActionBarNoActionMenu() {
        ViewUtil.setVisibility(mActionBarRightStubForCenterTitle, true);
    }

    /**
     * Set action bar left side custom view enabled, for 'Home', 'Back' etc.
     *
     * @param text          caption
     * @param clickListener listener
     */
    protected void setActionBarCustomHome(String text, View.OnClickListener clickListener) {
        getActionBar().setDisplayShowHomeEnabled(false);
        mActionBarHomeText.setText(text);
        mActionBarHomeContent.setOnClickListener(clickListener);
        ViewUtil.setVisibility(mActionBarHomeContent, true);
    }

    /**
     * Return action bar home content, left side, to default - logo, no 'Home' or 'Back'.
     */
    protected void setActionBarHomeDefault() {
        getActionBar().setDisplayShowHomeEnabled(true);
        mActionBarHomeText.setText("");
        mActionBarHomeContent.setOnClickListener(null);
        ViewUtil.setVisibility(mActionBarHomeContent, false);
    }

    /**
     * All events on activity firstly goes here, so we can mediate it.
     *
     * @param event event that passes
     * @return True if we consume this event
     */
    @Override
    public boolean dispatchTouchEvent(final MotionEvent event) {

        /**
         * If we should not support multi touch event, we check if motionevent index
         * not primary we not handle this event.
         */
        if (mMultiTouchDisabled && MotionEventCompat.getActionIndex(event) > 0) {
            return false;
        }

        if (mGestureDetectorEnabled && mGestureDetector.onTouchEvent(event)) {
            return true;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * Child activity can override it for handling fling to left gesture.
     *
     * @return True if consume this event
     */
    protected boolean onFlingToLeft() {
        return false;
    }

    /**
     * Override it for handling fling to right gesture.
     *
     * @return True if consume this event
     */
    protected boolean onFlingToRight() {
        return false;
    }

    /**
     * Override it for off screen fling to right gesture.
     *
     * @return True if consume this event
     */
    protected boolean onOffScreenFlingToRight() {
        if (mOffScreenFlingToRightAsBack) {
            super.onBackPressed();
            return true;
        }

        return false;
    }

    /**
     * Override it for off screen fling to left gesture.
     *
     * @return True if consume this event
     */
    @SuppressWarnings("SameReturnValue")
    private boolean onOffScreenFlingToLeft() {
        return false;
    }

    /**
     * Child activity can override it for handling scroll to left.
     *
     * @param fullDistance     full scroll distance from ACTION_DOWN event, negative value
     * @param lastDistanceStep last scroll move step, from last call
     * @return True if consume this event
     */
    @SuppressWarnings("SameReturnValue")
    private boolean onScrollToLeft(final float fullDistance, final float lastDistanceStep) {
        return false;
    }

    /**
     * Child activity can override it for handling scroll to right.
     *
     * @param fullDistance     full scroll distance from ACTION_DOWN event, positive value
     * @param lastDistanceStep last scroll move step, from last call
     * @return True if consume this event
     */
    @SuppressWarnings("SameReturnValue")
    private boolean onScrollToRight(final float fullDistance, final float lastDistanceStep) {
        return false;
    }

    /**
     * Gesture detector for general gesture handling.
     */
    private class GestureDetectorListener implements GestureDetector.OnGestureListener {

        /**
         * Margin screen ration, that we assume as screen edge.
         * So if touch has coordinates that is closer than {@link #OFFSCREEN_GESTURE_EDGE_RATIO} - we
         * assume it's edge touch.
         */
        public static final float OFFSCREEN_GESTURE_EDGE_RATIO = 0.10f;

        /**
         * Ration of screen width, used to determine ofscreen gesture - if distance exceed it.
         */
        public static final float OFFSCREEN_GESTURE_FULLDISTANCE_RATIO = 0.75f;

        @Override
        public boolean onDown(final MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(final MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(final MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(final MotionEvent e1,
                                final MotionEvent e2,
                                final float distanceX,
                                final float distanceY) {
            if (!mGestureDetectorEnabled) {
                return false;
            }

            float fullDistance = e1.getX() - e2.getX();

            if (fullDistance > 0 && onScrollToRight(fullDistance, distanceX)) {
                return true;
            }

            if (fullDistance < 0 && onScrollToLeft(fullDistance, distanceX)) {
                return true;
            }

            return false;
        }

        @Override
        public void onLongPress(final MotionEvent e) {
        }

        @Override
        public boolean onFling(final MotionEvent e1,
                               final MotionEvent e2,
                               final float velocityX,
                               final float velocityY) {

            if (!mGestureDetectorEnabled) {
                return false;
            }

            float lastTouchX = e2.getX();
            float fullDistance = e1.getX() - e2.getX();

            if (GestureUtil.isFlingToLeft(velocityX, velocityY)) {
                if (isFlingToLeftIsOffScreen(lastTouchX, fullDistance) && onOffScreenFlingToLeft()) {
                    return true;
                }

                if (onFlingToLeft()) {
                    return true;
                }
            }

            if (GestureUtil.isFlingToRight(velocityX, velocityY)) {
                if (isFlingRightIsOffScreen(lastTouchX, fullDistance) && onOffScreenFlingToRight()) {
                    return true;
                }

                if (onFlingToRight()) {
                    return true;
                }
            }

            return false;
        }

        /**
         * We assume off-screen gesture condition:
         * - it's left edge fling
         * - full distance of touch is more than part of screen width
         * that specified by {@link #OFFSCREEN_GESTURE_FULLDISTANCE_RATIO}.
         *
         * @param lastX              x of last touch
         * @param fullTouchXDistance full x touch distance
         * @return True if we consume event
         */
        private boolean isFlingToLeftIsOffScreen(float lastX, float fullTouchXDistance) {
            return isLeftEdgeX(lastX)
                    && fullTouchXDistance < getScreenWidthPixels() * OFFSCREEN_GESTURE_FULLDISTANCE_RATIO;
        }

        /**
         * We assume off-screen gesture condition:
         * - it's right edge fling
         * - full distance of touch is more than part of screen width
         * that specified by {@link #OFFSCREEN_GESTURE_FULLDISTANCE_RATIO}.
         *
         * @param lastX              x of last touch
         * @param fullTouchXDistance full x touch distance
         * @return True if we consume event
         */
        private boolean isFlingRightIsOffScreen(float lastX, float fullTouchXDistance) {
            return isRightEdgeX(lastX)
                    && fullTouchXDistance < getScreenWidthPixels() * OFFSCREEN_GESTURE_FULLDISTANCE_RATIO;
        }

        /**
         * Checks for x coordinate is seems to be left edge touch with precision ration
         * {@link #OFFSCREEN_GESTURE_EDGE_RATIO}.
         *
         * @param x coordinate
         * @return True if it near edge with consequent error
         */
        private boolean isLeftEdgeX(final float x) {
            return x < getScreenWidthPixels() * OFFSCREEN_GESTURE_EDGE_RATIO;
        }

        /**
         * Checks for x coordinate is seems to be right edge touch with precision ration
         * {@link #OFFSCREEN_GESTURE_EDGE_RATIO}.
         *
         * @param x cooridnate
         * @return True if it near edge with consequent error
         */
        private boolean isRightEdgeX(final float x) {
            return x > getScreenWidthPixels() * (1 - OFFSCREEN_GESTURE_EDGE_RATIO);
        }
    }

    /**
     * Getter for screen width in pixels, lazy inited display metrics.
     *
     * @return screen width in pixels
     */
    private int getScreenWidthPixels() {
        if (mDisplayMetrics == null) {
            mDisplayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        }

        return mDisplayMetrics.widthPixels;
    }

    /**
     * Getter for gesture detector enabled. If enabled callbacks executed.
     *
     * @return gesture detector enabled
     */
    public boolean isGestureDetectorEnabled() {
        return mGestureDetectorEnabled;
    }

    /**
     * Setter for gesture detector enabled.
     *
     * @param gestureDetectorEnabled gesture detector enabled
     */
    protected void setGestureDetectorEnabled(final boolean gestureDetectorEnabled) {
        mGestureDetectorEnabled = gestureDetectorEnabled;
    }

    /**
     * Sets title to custom action bar layout.
     *
     * @param title title string
     */
    @Override
    public void setTitle(final CharSequence title) {
        mActionBarTitleText.setText(title.toString());
    }

    /**
     * Getter for mOffScreenFlingToRightAsBack.
     *
     * @return mOffScreenFlingToRightAsBack
     */
    public boolean isOffScreenFlingToRightAsBack() {
        return mOffScreenFlingToRightAsBack;
    }

    /**
     * Setter for mOffScreenFlingToRightAsBack.
     *
     * @param offScreenFlingToRightAsBack value
     */
    protected void setOffScreenFlingToRightAsBack(final boolean offScreenFlingToRightAsBack) {
        mOffScreenFlingToRightAsBack = offScreenFlingToRightAsBack;
    }

    /**
     * Getter for multitouch disabled.
     *
     * @return True if multi touch events disabled
     */
    public boolean isMultiTouchDisabled() {
        return mMultiTouchDisabled;
    }

    /**
     * Setter for multitouch disabled.
     *
     * @param multiTouchDisabled value
     */
    public void setMultiTouchDisabled(final boolean multiTouchDisabled) {
        mMultiTouchDisabled = multiTouchDisabled;
    }

    @Override
    protected void onStart() {
        /**
         * If transition and back button synchronised => init state of this.
         */
        if (mTransitionAndBackSync) {
            initBackAndTransitionSync();
        }

        super.onStart();
    }

    private void initBackAndTransitionSync() {
        mTransitionInitiated = false;
        mBackPressed = false;
    }

    @Override
    public void startActivity(Intent intent) {
        /**
         * Handle this transition to block back if it was first.
         */
        if (mTransitionAndBackSync && transitionAndBackSyncHandle()) {
            return;
        }
        super.startActivity(intent);

        overridePendingTransitionOut();
    }

    /**
     * If transition initiated, but back button has been already pressed => not process transition.
     * Else mark that transition was initiated.
     *
     * @return True if transition should not be processed
     */
    private boolean transitionAndBackSyncHandle() {
        if (mBackPressed) {
            return true;
        }

        mTransitionInitiated = true;
        return false;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        /**
         * Handle this transition to block back if it was first.
         */
        if (mTransitionAndBackSync && transitionAndBackSyncHandle()) {
            return;
        }
        super.startActivityForResult(intent, requestCode);

        overridePendingTransitionOut();
    }

    @Override
    public void startActivityFromFragment(final Fragment fragment, final Intent intent, final int requestCode) {
        /**
         * Handle this transition to block back if it was first.
         */
        if (mTransitionAndBackSync && transitionAndBackSyncHandle()) {
            return;
        }
        super.startActivityFromFragment(fragment, intent, requestCode);

        overridePendingTransitionOut();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivityFromFragment(final android.app.Fragment fragment, final Intent intent, final int requestCode, final Bundle options) {
        /**
         * Handle this transition to block back if it was first.
         */
        if (mTransitionAndBackSync && transitionAndBackSyncHandle()) {
            return;
        }

        super.startActivityFromFragment(fragment, intent, requestCode, options);

        overridePendingTransitionOut();
    }

    @Override
    public void onBackPressed() {
        /**
         * If transition was initiated we not handle back.
         */
        if (mTransitionAndBackSync && mTransitionInitiated) {
            return;
        }

        /**
         * For transition and back sync - indicator that back was pressed.
         */
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            mBackPressed = true;
            finish();

            overridePendingTransitionOut();
        }
    }

    /**
     * Getter activity transition and back button sync.
     *
     * @return value
     */
    public boolean isTransitionAndBackSync() {
        return mTransitionAndBackSync;
    }

    /**
     * Sets activity transition and back button sync.
     *
     * @param transitionAndBackSync value
     */
    public void setTransitionAndBackSync(final boolean transitionAndBackSync) {
        mTransitionAndBackSync = transitionAndBackSync;
    }
}
