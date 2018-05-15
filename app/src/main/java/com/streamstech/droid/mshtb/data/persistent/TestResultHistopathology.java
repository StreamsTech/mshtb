package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

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
    private int histopathology_site;

    @NotNull
    private long resultdate;
    @NotNull
    private int result;

    @NotNull
    private long createdtime;
    private boolean uploaded;
    @Generated(hash = 2135375647)
    public TestResultHistopathology(Long id, @NotNull String patientid,
            long orderdate, int histopathology, int histopathology_site,
            long resultdate, int result, long createdtime, boolean uploaded) {
        this.id = id;
        this.patientid = patientid;
        this.orderdate = orderdate;
        this.histopathology = histopathology;
        this.histopathology_site = histopathology_site;
        this.resultdate = resultdate;
        this.result = result;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
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
    public int getHistopathology_site() {
        return this.histopathology_site;
    }
    public void setHistopathology_site(int histopathology_site) {
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
}
