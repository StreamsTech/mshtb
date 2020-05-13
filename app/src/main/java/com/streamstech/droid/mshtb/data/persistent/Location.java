package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by AKASH-LAPTOP on 5/20/2018.
 */

@Entity
public class Location {

    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String generatedid;

    @NotNull
    private long createdtime;

    @Generated(hash = 1573008594)
    public Location(Long id, @NotNull String name, @NotNull String generatedid,
            long createdtime) {
        this.id = id;
        this.name = name;
        this.generatedid = generatedid;
        this.createdtime = createdtime;
    }

    @Generated(hash = 375979639)
    public Location() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneratedid() {
        return this.generatedid;
    }

    public void setGeneratedid(String generatedid) {
        this.generatedid = generatedid;
    }

    public long getCreatedtime() {
        return this.createdtime;
    }

    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }
}
