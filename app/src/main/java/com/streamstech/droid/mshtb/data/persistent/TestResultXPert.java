package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

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
    private long orderdate;
    @NotNull
    private int specimen_type;
    @NotNull
    private long resultdate;
    @NotNull
    private int genexpert_result;
    @NotNull
    private int rif_result;

    @NotNull
    private long createdtime;
    private boolean uploaded;
    @Generated(hash = 1806400476)
    public TestResultXPert(Long id, @NotNull String patientid, long orderdate,
            int specimen_type, long resultdate, int genexpert_result,
            int rif_result, long createdtime, boolean uploaded) {
        this.id = id;
        this.patientid = patientid;
        this.orderdate = orderdate;
        this.specimen_type = specimen_type;
        this.resultdate = resultdate;
        this.genexpert_result = genexpert_result;
        this.rif_result = rif_result;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
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
    public long getOrderdate() {
        return this.orderdate;
    }
    public void setOrderdate(long orderdate) {
        this.orderdate = orderdate;
    }
    public int getSpecimen_type() {
        return this.specimen_type;
    }
    public void setSpecimen_type(int specimen_type) {
        this.specimen_type = specimen_type;
    }
    public long getResultdate() {
        return this.resultdate;
    }
    public void setResultdate(long resultdate) {
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
