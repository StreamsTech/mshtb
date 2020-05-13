package com.streamstech.droid.mshtb.data;

/**
 * Created by AKASH-LAPTOP on 10/20/2017.
 */

public class NameValue {
    private String name;
    private Object value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public NameValue(String name, Object value){
        this.name = name;
        this.value = value;
    }
}
