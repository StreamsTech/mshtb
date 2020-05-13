package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class User {
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String loginname;
    @NotNull
    private String password;
    @NotNull
    private String mobile;
    @NotNull
    private boolean loggedin;
    @NotNull
    private String screenerid;
    @NotNull
    private String locationid;
    @NotNull
    private String locationname;
    @Generated(hash = 1746914183)
    public User(Long id, @NotNull String name, @NotNull String loginname,
            @NotNull String password, @NotNull String mobile, boolean loggedin,
            @NotNull String screenerid, @NotNull String locationid,
            @NotNull String locationname) {
        this.id = id;
        this.name = name;
        this.loginname = loginname;
        this.password = password;
        this.mobile = mobile;
        this.loggedin = loggedin;
        this.screenerid = screenerid;
        this.locationid = locationid;
        this.locationname = locationname;
    }
    @Generated(hash = 586692638)
    public User() {
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
    public String getLoginname() {
        return this.loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public boolean getLoggedin() {
        return this.loggedin;
    }
    public void setLoggedin(boolean loggedin) {
        this.loggedin = loggedin;
    }
    public String getScreenerid() {
        return this.screenerid;
    }
    public void setScreenerid(String screenerid) {
        this.screenerid = screenerid;
    }
    public String getLocationid() {
        return this.locationid;
    }
    public void setLocationid(String locationid) {
        this.locationid = locationid;
    }
    public String getLocationname() {
        return this.locationname;
    }
    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }
}
