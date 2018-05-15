package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

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
    private boolean xray;
    @NotNull
    private boolean xpert;
    @NotNull
    private boolean smear;
    @NotNull
    private boolean ultrasound;
    @NotNull
    private boolean histopathology;
    @NotNull
    private boolean ctmri;

    private String histopathology_sample;
    private String other;

    @NotNull
    private long createdtime;

    private boolean uploaded;

    @Generated(hash = 1160020729)
    public TestIndication(Long id, @NotNull String patientid, boolean xray,
            boolean xpert, boolean smear, boolean ultrasound,
            boolean histopathology, boolean ctmri, String histopathology_sample,
            String other, long createdtime, boolean uploaded) {
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

    public boolean getXray() {
        return this.xray;
    }

    public void setXray(boolean xray) {
        this.xray = xray;
    }

    public boolean getXpert() {
        return this.xpert;
    }

    public void setXpert(boolean xpert) {
        this.xpert = xpert;
    }

    public boolean getSmear() {
        return this.smear;
    }

    public void setSmear(boolean smear) {
        this.smear = smear;
    }

    public boolean getUltrasound() {
        return this.ultrasound;
    }

    public void setUltrasound(boolean ultrasound) {
        this.ultrasound = ultrasound;
    }

    public boolean getHistopathology() {
        return this.histopathology;
    }

    public void setHistopathology(boolean histopathology) {
        this.histopathology = histopathology;
    }

    public boolean getCtmri() {
        return this.ctmri;
    }

    public void setCtmri(boolean ctmri) {
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
}
