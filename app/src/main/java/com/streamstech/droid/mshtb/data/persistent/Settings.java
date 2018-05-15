package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by shafi on 4/23/2017.
 */
@Entity
public class Settings {
    @Id
    private Long id;
    @NotNull
    private String key;
    @NotNull
    private String value;
    @Generated(hash = 2088717473)
    public Settings(Long id, @NotNull String key, @NotNull String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }
    @Generated(hash = 456090543)
    public Settings() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
