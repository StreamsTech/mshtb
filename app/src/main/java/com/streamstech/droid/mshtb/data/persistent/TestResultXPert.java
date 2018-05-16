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
public class TestResultXPert {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private Date orderdate;
    @NotNull
    private int specimen_type;
    @NotNull
    private Date resultdate;
    @NotNull
    private int genexpert_result;
    @NotNull
    private int rif_result;

    @NotNull
    private Date createdtime;
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1982480473)
    public TestResultXPert(Long id, @NotNull String patientid,
            @NotNull Date orderdate, int specimen_type, @NotNull Date resultdate,
            int genexpert_result, int rif_result, @NotNull Date createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.orderdate = orderdate;
        this.specimen_type = specimen_type;
        this.resultdate = resultdate;
        this.genexpert_result = genexpert_result;
        this.rif_result = rif_result;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 69450378)
    public TestResultXPert() {
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
    public int getSpecimen_type() {
        return this.specimen_type;
    }
    public void setSpecimen_type(int specimen_type) {
        this.specimen_type = specimen_type;
    }
    public Date getResultdate() {
        return this.resultdate;
    }
    public void setResultdate(Date resultdate) {
        this.resultdate = resultdate;
    }
    public int getGenexpert_result() {
        return this.genexpert_result;
    }
    public void setGenexpert_result(int genexpert_result) {
        this.genexpert_result = genexpert_result;
    }
    public int getRif_result() {
        return this.rif_result;
    }
    public void setRif_result(int rif_result) {
        this.rif_result = rif_result;
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
