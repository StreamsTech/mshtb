package com.streamstech.droid.mshtb.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioGroup;

import com.streamstech.droid.mshtb.R;

/**
 * Created by AKASH-LAPTOP on 5/15/2018.
 */

public class MyRadioGroup extends RadioGroup {

    // holds the checked id; the selection is empty by default
//    private int mCheckedId = -1;
//    private int mInitialCheckedId = View.NO_ID;

    public MyRadioGroup(Context context) {
        super(context);
        int selectedIndex = getSelectedIndex();
        System.out.println("Selected Index #: " + selectedIndex);
    }

    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        int selectedIndex = getSelectedIndex();
        int id = getCheckedRadioButtonId();
        System.out.println("Selected Index/ID: " + selectedIndex + ", " + id);
        // retrieve selected radio button as requested by the user in the
        // XML layout file
//        TypedArray attributes = context.obtainStyledAttributes(
//                attrs, com.android.internal.R.styleable.RadioGroup, com.android.internal.R.attr.radioButtonStyle, 0);
//        int value = attributes.getResourceId(R.styleable.RadioGroup_checkedButton, View.NO_ID);
//        if (value != View.NO_ID) {
//            mCheckedId = value;
//            mInitialCheckedId = value;
//        }
//        final int index = attributes.getInt(com.android.internal.R.styleable.RadioGroup_orientation, VERTICAL);
//        setOrientation(index);
//        attributes.recycle();
    }

    public boolean anyoneChecked(){
        if (getCheckedRadioButtonId() == -1)
            return false;
        else
            return true;
    }

    public int getSelectedIndex(){
        return indexOfChild(findViewById(getCheckedRadioButtonId()));
    }
}
