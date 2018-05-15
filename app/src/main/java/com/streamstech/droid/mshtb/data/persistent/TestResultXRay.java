package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 5/14/2018.
 */

@Entity
public class TestResultXRay {

    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private long orderdate;
    @NotNull
    private boolean chesrxray;
    @NotNull
    private long resultdate;
    @NotNull
    private int radiologica_finding;
    @NotNull
    private int radiologica_diagnosis;
    @NotNull
    private int extent_disease;

    @NotNull
    private long createdtime;

    private boolean uploaded;

    @Generated(hash = 1403282013)
    public TestResultXRay(Long id, @NotNull String patientid, long orderdate,
            boolean chesrxray, long resultdate, int radiologica_finding,
            int radiologica_diagnosis, int extent_disease, long createdtime,
            boolean uploaded) {
        this.id = id;
        this.patientid = patientid;
        this.orderdate = orderdate;
        this.chesrxray = chesrxray;
        this.resultdate = resultdate;
        this.radiologica_finding = radiologica_finding;
        this.radiologica_diagnosis = radiologica_diagnosis;
        this.extent_disease = extent_disease;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
    }

    @Generated(hash = 1831426138)
    public TestResultXRay() {
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

    public boolean getChesrxray() {
        return this.chesrxray;
    }

    public void setChesrxray(boolean chesrxray) {
        this.chesrxray = chesrxray;
    }

    public long getResultdate() {
        return this.resultdate;
    }

    public void setResultdate(long resultdate) {
        this.resultdate = resultdate;
    }

    public int getRadiologica_finding() {
        return this.radiologica_finding;
    }

    public void setRadiologica_finding(int radiologica_finding) {
        this.radiologica_finding = radiologica_finding;
    }

    public int getRadiologica_diagnosis() {
        return this.radiologica_diagnosis;
    }

    public void setRadiologica_diagnosis(int radiologica_diagnosis) {
        this.radiologica_diagnosis = radiologica_diagnosis;
    }

    public int getExtent_disease() {
        return this.extent_disease;
    }

    public void setExtent_disease(int extent_disease) {
        this.extent_disease = extent_disease;
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
