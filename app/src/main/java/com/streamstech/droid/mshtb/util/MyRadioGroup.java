package com.streamstech.droid.mshtb.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/**
 * Created by AKASH-LAPTOP on 5/15/2018.
 */

public class MyRadioGroup extends RadioGroup {
    public MyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
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
