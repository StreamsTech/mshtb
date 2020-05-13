package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

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
    private int sensivity;
    @NotNull
    private int siteofdisease;
    @NotNull
    private String name;
    @NotNull
    private double age;
    @NotNull
    private String gender;
    private String contantno;
    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;

    @NotNull
    private boolean dirty;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;

    private String address;
    private boolean indexpatient;
    @Generated(hash = 2024417609)
    public Patient(Long id, @NotNull String patientid, @NotNull String screenerid,
            boolean presumtivetb, boolean tb, int sensivity, int siteofdisease,
            @NotNull String name, double age, @NotNull String gender,
            String contantno, long createdtime, boolean uploaded, boolean dirty,
            double longitude, double latitude, String address,
            boolean indexpatient) {
        this.id = id;
        this.patientid = patientid;
        this.screenerid = screenerid;
        this.presumtivetb = presumtivetb;
        this.tb = tb;
        this.sensivity = sensivity;
        this.siteofdisease = siteofdisease;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contantno = contantno;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.dirty = dirty;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.indexpatient = indexpatient;
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
    public int getSensivity() {
        return this.sensivity;
    }
    public void setSensivity(int sensivity) {
        this.sensivity = sensivity;
    }
    public int getSiteofdisease() {
        return this.siteofdisease;
    }
    public void setSiteofdisease(int siteofdisease) {
        this.siteofdisease = siteofdisease;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getAge() {
        return this.age;
    }
    public void setAge(double age) {
        this.age = age;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getContantno() {
        return this.contantno;
    }
    public void setContantno(String contantno) {
        this.contantno = contantno;
    }
    public long getCreatedtime() {
        return this.createdtime;
    }
    public void setCreatedtime(long createdtime) {
        this.createdtime = createdtime;
    }
    public boolean getUploaded() {
        return this.uploaded;
    }
    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
    public boolean getDirty() {
        return this.dirty;
    }
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
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
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public boolean getIndexpatient() {
        return this.indexpatient;
    }
    public void setIndexpatient(boolean indexpatient) {
        this.indexpatient = indexpatient;
    }

}
