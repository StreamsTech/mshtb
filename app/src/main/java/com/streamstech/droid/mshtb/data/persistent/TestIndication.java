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
public class TestIndication {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private int xray;
    @NotNull
    private int xpert;
    @NotNull
    private int smear;
    @NotNull
    private int ultrasound;
    @NotNull
    private int histopathology;
    @NotNull
    private int ctmri;

    private String histopathology_sample;
    private String other;

    @NotNull
    private Date createdtime;

    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 424858074)
    public TestIndication(Long id, @NotNull String patientid, int xray, int xpert,
            int smear, int ultrasound, int histopathology, int ctmri,
            String histopathology_sample, String other, @NotNull Date createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.xray = xray;
        this.xpert = xpert;
        this.smear = smear;
        this.ultrasound = ultrasound;
        this.histopathology = histopathology;
        this.ctmri = ctmri;
        this.histopathology_sample = histopathology_sample;
        this.other = other;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 658695616)
    public TestIndication() {
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
    public int getXray() {
        return this.xray;
    }
    public void setXray(int xray) {
        this.xray = xray;
    }
    public int getXpert() {
        return this.xpert;
    }
    public void setXpert(int xpert) {
        this.xpert = xpert;
    }
    public int getSmear() {
        return this.smear;
    }
    public void setSmear(int smear) {
        this.smear = smear;
    }
    public int getUltrasound() {
        return this.ultrasound;
    }
    public void setUltrasound(int ultrasound) {
        this.ultrasound = ultrasound;
    }
    public int getHistopathology() {
        return this.histopathology;
    }
    public void setHistopathology(int histopathology) {
        this.histopathology = histopathology;
    }
    public int getCtmri() {
        return this.ctmri;
    }
    public void setCtmri(int ctmri) {
        this.ctmri = ctmri;
    }
    public String getHistopathology_sample() {
        return this.histopathology_sample;
    }
    public void setHistopathology_sample(String histopathology_sample) {
        this.histopathology_sample = histopathology_sample;
    }
    public String getOther() {
        return this.other;
    }
    public void setOther(String other) {
        this.other = other;
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
