package com.example.testjacoco.test_jacoco;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Class extends {@link android.widget.EditText} with supporting typeface specifing attribute.
 *
 * @author Anton_Popov on 20.05.2014.
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
 *                                             <com.moneysupermarket.android.widgets.EditText
 *                                                    android:layout_width="wrap_content"
 *                                                   android:layout_height="wrap_content"
 *                                                   android:layout_gravity="center"
 *                                                   android:text="Test font african"
 *                                                   android:textSize="24sp"
 *                                                   style="@style/font_gotham_ultra"/>}
 *                                 }
 *                                 </pre>
 */
public class FontEditText extends EditText {
    /**
     * Default constructor.
     *
     * @param context context
     */
    public FontEditText(Context context) {
        this(context, null, 0);
    }

    /**
     * Default constructor.
     *
     * @param context context
     * @param attrs   attributes
     */
    public FontEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Designated constructor.
     *
     * @param context  context
     * @param attrs    attributes
     * @param defStyle defStyle
     */
    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        //isInEditMode()should be used inside the Custom View constructor
        if (isInEditMode())
            return;

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setFocusableInTouchMode(true);
                return false;
            }
        });
    }
}
