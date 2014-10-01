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

/**
 * @author Ivan_Kobzarev on 4/11/2014.
 */

/**
 * Class for general gesture detecting logic, here we can place gesture constants.
 */
public final class GestureUtil {

    /**
     * One of condition of offscreen gesture, we assume offscreen gesture only if velocity more than this value.
     */
    private static final float OFFSCREEN_GESTURE_VELOCITY_MIN = 18000;

    /**
     * Empty private constructor for utility class.
     */
    private GestureUtil() {
    }

    /**
     * Checker horizontal fling.
     *
     * @param velocityX horizontal velocity
     * @param velocityY vertical velocity
     * @return True if gesture seems to be horizontal fling
     */
    private static boolean isHorizontalFling(final float velocityX, final float velocityY) {
        return Math.abs(velocityX) > Math.abs(velocityY);
    }

    /**
     * Checker fling to left.
     *
     * @param velocityX horizontal velocity
     * @param velocityY vertical velocity
     * @return True if gesture seems to be horizontal left fling
     */
    public static boolean isFlingToLeft(final float velocityX, final float velocityY) {
        return isHorizontalFling(velocityX, velocityY) && velocityX < 0 && isVelocityOffScreen(velocityX);
    }

    private static boolean isVelocityOffScreen(final float velocity) {
        return Math.abs(velocity) > OFFSCREEN_GESTURE_VELOCITY_MIN;
    }

    /**
     * Checker fling to right.
     *
     * @param velocityX horizontal velocity
     * @param velocityY vertical velocity
     * @return True if gesture seems to be horizontal right fling
     */
    public static boolean isFlingToRight(final float velocityX, final float velocityY) {
        return isHorizontalFling(velocityX, velocityY) && velocityX > 0 && isVelocityOffScreen(velocityX);
    }

}
