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

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Class extends {@link android.widget.TextView} with supporting typeface specifing attribute.
 *
 * @author Ivan_Kobzarev on 3/25/14.
 *         <p/>
 *         For all used fonts create style like 'font_african' that consist of
 *         <pre>
 *                                 {@code
 *                                 <style name="font_african">
 *                                     <item name="customTypeface">fonts/Gotham-Ultra.ttf</item>
 *                                 </style>
 *                                 }
 *                                 </pre>
 *         So usage will be something like this:
 *         <pre>
 *                                 {@code
 *                                             <com.moneysupermarket.android.widgets.FontTextView
 *                                                    android:layout_width="wrap_content"
 *                                                   android:layout_height="wrap_content"
 *                                                   android:layout_gravity="center"
 *                                                   android:text="Test font african"
 *                                                   android:textSize="24sp"
 *                                                   style="@style/font_gotham_ultra"/>}
 *                                 }
 *                                 </pre>
 */
public class FontTextView extends TextView {

    /**
     * Default constructor from View.
     *
     * @param context context
     */
    public FontTextView(final Context context) {
        this(context, null, 0);
    }

    /**
     * Default constructor from View.
     *
     * @param context context
     * @param attrs   attrs
     */
    public FontTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Default constructor from View.
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public FontTextView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        //isInEditMode()should be used inside the Custom View constructor
        if (isInEditMode())
            return;

    }
}
