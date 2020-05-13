package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by AKASH-LAPTOP on 5/14/2018.
 */

@Entity
public class TestResultHistopathology {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private long orderdate;
    @NotNull
    private int histopathology;
    private String histopathology_site;

    @NotNull
    private long resultdate;
    @NotNull
    private int result;

    @NotNull
    private long createdtime;
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 2004385245)
    public TestResultHistopathology(Long id, @NotNull String patientid,
            long orderdate, int histopathology, String histopathology_site,
            long resultdate, int result, long createdtime, boolean uploaded,
            double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.orderdate = orderdate;
        this.histopathology = histopathology;
        this.histopathology_site = histopathology_site;
        this.resultdate = resultdate;
        this.result = result;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 513924081)
    public TestResultHistopathology() {
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
    public long getOrderdate() {
        return this.orderdate;
    }
    public void setOrderdate(long orderdate) {
        this.orderdate = orderdate;
    }
    public int getHistopathology() {
        return this.histopathology;
    }
    public void setHistopathology(int histopathology) {
        this.histopathology = histopathology;
    }
    public String getHistopathology_site() {
        return this.histopathology_site;
    }
    public void setHistopathology_site(String histopathology_site) {
        this.histopathology_site = histopathology_site;
    }
    public long getResultdate() {
        return this.resultdate;
    }
    public void setResultdate(long resultdate) {
        this.resultdate = resultdate;
    }
    public int getResult() {
        return this.result;
    }
    public void setResult(int result) {
        this.result = result;
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
