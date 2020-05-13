package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 5/20/2018.
 */

@Entity
public class Treatment {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private long registrationdate;
    @NotNull
    private int category;
    @NotNull
    private int referred;

    private String treatmentcenter;
    private String registrationno;

    @NotNull
    private int patienttype;

    @NotNull
    private long treatmentstartdate;

    @NotNull
    private long createdtime;
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 334573678)
    public Treatment(Long id, @NotNull String patientid, long registrationdate,
            int category, int referred, String treatmentcenter,
            String registrationno, int patienttype, long treatmentstartdate,
            long createdtime, boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.registrationdate = registrationdate;
        this.category = category;
        this.referred = referred;
        this.treatmentcenter = treatmentcenter;
        this.registrationno = registrationno;
        this.patienttype = patienttype;
        this.treatmentstartdate = treatmentstartdate;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 852361623)
    public Treatment() {
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
    public long getRegistrationdate() {
        return this.registrationdate;
    }
    public void setRegistrationdate(long registrationdate) {
        this.registrationdate = registrationdate;
    }
    public int getCategory() {
        return this.category;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public int getReferred() {
        return this.referred;
    }
    public void setReferred(int referred) {
        this.referred = referred;
    }
    public String getTreatmentcenter() {
        return this.treatmentcenter;
    }
    public void setTreatmentcenter(String treatmentcenter) {
        this.treatmentcenter = treatmentcenter;
    }
    public String getRegistrationno() {
        return this.registrationno;
    }
    public void setRegistrationno(String registrationno) {
        this.registrationno = registrationno;
    }
    public int getPatienttype() {
        return this.patienttype;
    }
    public void setPatienttype(int patienttype) {
        this.patienttype = patienttype;
    }
    public long getTreatmentstartdate() {
        return this.treatmentstartdate;
    }
    public void setTreatmentstartdate(long treatmentstartdate) {
        this.treatmentstartdate = treatmentstartdate;
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
