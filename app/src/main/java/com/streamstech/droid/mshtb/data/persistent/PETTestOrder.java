package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class PETTestOrder {
    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;
    @NotNull
    private int xray;
    @NotNull
    private int smear;
    @NotNull
    private int xpert;
    @NotNull
    private int tstmt;
    @NotNull
    private int cbcesr;
    @NotNull
    private int histopathology;
    private String histopathology_sample;
    @NotNull
    private int ctmri;
    @NotNull
    private int ultrasound;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1393991220)
    public PETTestOrder(Long id, @NotNull String contactqr, int screenerid,
            int xray, int smear, int xpert, int tstmt, int cbcesr,
            int histopathology, String histopathology_sample, int ctmri,
            int ultrasound, long createdtime, boolean uploaded, double longitude,
            double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.xray = xray;
        this.smear = smear;
        this.xpert = xpert;
        this.tstmt = tstmt;
        this.cbcesr = cbcesr;
        this.histopathology = histopathology;
        this.histopathology_sample = histopathology_sample;
        this.ctmri = ctmri;
        this.ultrasound = ultrasound;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 287956375)
    public PETTestOrder() {
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
    public int getXray() {
        return this.xray;
    }
    public void setXray(int xray) {
        this.xray = xray;
    }
    public int getSmear() {
        return this.smear;
    }
    public void setSmear(int smear) {
        this.smear = smear;
    }
    public int getXpert() {
        return this.xpert;
    }
    public void setXpert(int xpert) {
        this.xpert = xpert;
    }
    public int getTstmt() {
        return this.tstmt;
    }
    public void setTstmt(int tstmt) {
        this.tstmt = tstmt;
    }
    public int getCbcesr() {
        return this.cbcesr;
    }
    public void setCbcesr(int cbcesr) {
        this.cbcesr = cbcesr;
    }
    public int getHistopathology() {
        return this.histopathology;
    }
    public void setHistopathology(int histopathology) {
        this.histopathology = histopathology;
    }
    public String getHistopathology_sample() {
        return this.histopathology_sample;
    }
    public void setHistopathology_sample(String histopathology_sample) {
        this.histopathology_sample = histopathology_sample;
    }
    public int getCtmri() {
        return this.ctmri;
    }
    public void setCtmri(int ctmri) {
        this.ctmri = ctmri;
    }
    public int getUltrasound() {
        return this.ultrasound;
    }
    public void setUltrasound(int ultrasound) {
        this.ultrasound = ultrasound;
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
