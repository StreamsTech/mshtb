package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class PETSymptom {
    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;
    @NotNull
    private int q1;
    @NotNull
    private int q2;
    @NotNull
    private int q3;
    @NotNull
    private int q4;
    @NotNull
    private int q5;
    @NotNull
    private int q6;
    @NotNull
    private int q7;
    @NotNull
    private int q8;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1850844476)
    public PETSymptom(Long id, @NotNull String contactqr, int screenerid, int q1,
            int q2, int q3, int q4, int q5, int q6, int q7, int q8,
            long createdtime, boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 796289418)
    public PETSymptom() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContactqr() {
        return this.contactqr;
    }
    public void setContactqr(String contactqr) {
        this.contactqr = contactqr;
    }
    public int getScreenerid() {
        return this.screenerid;
    }
    public void setScreenerid(int screenerid) {
        this.screenerid = screenerid;
    }
    public int getQ1() {
        return this.q1;
    }
    public void setQ1(int q1) {
        this.q1 = q1;
    }
    public int getQ2() {
        return this.q2;
    }
    public void setQ2(int q2) {
        this.q2 = q2;
    }
    public int getQ3() {
        return this.q3;
    }
    public void setQ3(int q3) {
        this.q3 = q3;
    }
    public int getQ4() {
        return this.q4;
    }
    public void setQ4(int q4) {
        this.q4 = q4;
    }
    public int getQ5() {
        return this.q5;
    }
    public void setQ5(int q5) {
        this.q5 = q5;
    }
    public int getQ6() {
        return this.q6;
    }
    public void setQ6(int q6) {
        this.q6 = q6;
    }
    public int getQ7() {
        return this.q7;
    }
    public void setQ7(int q7) {
        this.q7 = q7;
    }
    public int getQ8() {
        return this.q8;
    }
    public void setQ8(int q8) {
        this.q8 = q8;
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
