package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by AKASH-LAPTOP on 5/20/2018.
 */

@Entity
public class Outcome {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private long treatmentstartdate;
    @NotNull
    private int category;
    @NotNull
    private int siteofdisease;

    @NotNull
    private int patienttype;
    @NotNull
    private int treatmentoutcome;

    @NotNull
    private long treatmentoutcomedate;

    @NotNull
    private long createdtime;
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1562508026)
    public Outcome(Long id, @NotNull String patientid, long treatmentstartdate,
            int category, int siteofdisease, int patienttype, int treatmentoutcome,
            long treatmentoutcomedate, long createdtime, boolean uploaded,
            double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.treatmentstartdate = treatmentstartdate;
        this.category = category;
        this.siteofdisease = siteofdisease;
        this.patienttype = patienttype;
        this.treatmentoutcome = treatmentoutcome;
        this.treatmentoutcomedate = treatmentoutcomedate;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1097829206)
    public Outcome() {
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
    public long getTreatmentstartdate() {
        return this.treatmentstartdate;
    }
    public void setTreatmentstartdate(long treatmentstartdate) {
        this.treatmentstartdate = treatmentstartdate;
    }
    public int getCategory() {
        return this.category;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public int getSiteofdisease() {
        return this.siteofdisease;
    }
    public void setSiteofdisease(int siteofdisease) {
        this.siteofdisease = siteofdisease;
    }
    public int getPatienttype() {
        return this.patienttype;
    }
    public void setPatienttype(int patienttype) {
        this.patienttype = patienttype;
    }
    public int getTreatmentoutcome() {
        return this.treatmentoutcome;
    }
    public void setTreatmentoutcome(int treatmentoutcome) {
        this.treatmentoutcome = treatmentoutcome;
    }
    public long getTreatmentoutcomedate() {
        return this.treatmentoutcomedate;
    }
    public void setTreatmentoutcomedate(long treatmentoutcomedate) {
        this.treatmentoutcomedate = treatmentoutcomedate;
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
