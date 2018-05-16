package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class Patient {
    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private String screenerid;
    @NotNull
    private boolean presumtivetb;
    @NotNull
    private boolean tb;
    @NotNull
    private String name;
    @NotNull
    private int age;
    @NotNull
    private String gender;
    @NotNull
    private Date createdtime;
    @NotNull
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 589589155)
    public Patient(Long id, @NotNull String patientid, @NotNull String screenerid,
            boolean presumtivetb, boolean tb, @NotNull String name, int age,
            @NotNull String gender, @NotNull Date createdtime, boolean uploaded,
            double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.screenerid = screenerid;
        this.presumtivetb = presumtivetb;
        this.tb = tb;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1655646460)
    public Patient() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPatientid() {
        return this.patientid;
    }
    public void setPatientid(String patientid) {
        this.patientid = patientid;
    }
    public String getScreenerid() {
        return this.screenerid;
    }
    public void setScreenerid(String screenerid) {
        this.screenerid = screenerid;
    }
    public boolean getPresumtivetb() {
        return this.presumtivetb;
    }
    public void setPresumtivetb(boolean presumtivetb) {
        this.presumtivetb = presumtivetb;
    }
    public boolean getTb() {
        return this.tb;
    }
    public void setTb(boolean tb) {
        this.tb = tb;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Date getCreatedtime() {
        return this.createdtime;
    }
    public void setCreatedtime(Date createdtime) {
        this.createdtime = createdtime;
    }
    public boolean getUploaded() {
        return this.uploaded;
    }
    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
