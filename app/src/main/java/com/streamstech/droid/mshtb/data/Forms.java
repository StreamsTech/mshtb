package com.streamstech.droid.mshtb.data;

/**
 * Created by AKASH-LAPTOP on 5/15/2018.
 */

public class Forms {
    private String name;
    private String type = "FAST";
    private FormsType code;
    private int icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Forms(String name, String type, FormsType code, int icon){
        this.setName(name);
        this.setType(type);
        this.setCode(code);
        this.setIcon(icon);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FormsType getCode() {
        return code;
    }

    public void setCode(FormsType code) {
        this.code = code;
    }
}
