package com.streamstech.droid.mshtb.data;

/**
 * Created by AKASH-LAPTOP on 9/20/2018.
 */

public class FileData {
    private String valueOne;
    private String valueTwo;
    private String valueThree;

    public String getValueOne() {
        return valueOne;
    }

    public void setValueOne(String valueOne) {
        this.valueOne = valueOne;
    }

    public String getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(String valueTwo) {
        this.valueTwo = valueTwo;
    }

    public String getValueThree() {
        return valueThree;
    }

    public void setValueThree(String valueThree) {
        this.valueThree = valueThree;
    }

    public FileData(String valueOne, String valueTwo, String valueThree) {
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
        this.valueThree = valueThree;
    }
}
