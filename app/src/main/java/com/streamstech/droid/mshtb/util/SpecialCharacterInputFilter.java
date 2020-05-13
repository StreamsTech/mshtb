package com.streamstech.droid.mshtb.util;

import com.streamstech.droid.mshtb.data.Constant;

/**
 * Created by AKASH-LAPTOP on 6/3/2018.
 */

public class SpecialCharacterInputFilter extends RegexInputFilter {

    private static final String SPECIAL_CHARACTER_REGEX = Constant.QR_REGEX;

    public SpecialCharacterInputFilter() {
        super(SPECIAL_CHARACTER_REGEX);
    }
}

