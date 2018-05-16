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
public class TestResultSmear {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private Date orderdate;
    @NotNull
    private int monthof_treatment;
    @NotNull
    private Date resultdate;
    @NotNull
    private int result;

    @NotNull
    private Date createdtime;
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1716173451)
    public TestResultSmear(Long id, @NotNull String patientid,
            @NotNull Date orderdate, int monthof_treatment,
            @NotNull Date resultdate, int result, @NotNull Date createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.orderdate = orderdate;
        this.monthof_treatment = monthof_treatment;
        this.resultdate = resultdate;
        this.result = result;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 95950355)
    public TestResultSmear() {
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
    public Date getOrderdate() {
        return this.orderdate;
    }
    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }
    public int getMonthof_treatment() {
        return this.monthof_treatment;
    }
    public void setMonthof_treatment(int monthof_treatment) {
        this.monthof_treatment = monthof_treatment;
    }
    public Date getResultdate() {
        return this.resultdate;
    }
    public void setResultdate(Date resultdate) {
        this.resultdate = resultdate;
    }
    public int getResult() {
        return this.result;
    }
    public void setResult(int result) {
        this.result = result;
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
