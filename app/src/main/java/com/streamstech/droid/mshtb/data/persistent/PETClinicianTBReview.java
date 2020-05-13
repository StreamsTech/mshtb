
package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 6/21/2018.
 */

@Entity
public class PETClinicianTBReview {

    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;

    @NotNull
    private int adherence;
    @NotNull
    private String labtests;
    @NotNull
    private String plans;

    private String newinstructions;

    @NotNull
    private long returnvisitdate;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 91884068)
    public PETClinicianTBReview(Long id, @NotNull String contactqr, int screenerid,
            int adherence, @NotNull String labtests, @NotNull String plans,
            String newinstructions, long returnvisitdate, long createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.adherence = adherence;
        this.labtests = labtests;
        this.plans = plans;
        this.newinstructions = newinstructions;
        this.returnvisitdate = returnvisitdate;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1138869095)
    public PETClinicianTBReview() {
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
    public int getAdherence() {
        return this.adherence;
    }
    public void setAdherence(int adherence) {
        this.adherence = adherence;
    }
    public String getLabtests() {
        return this.labtests;
    }
    public void setLabtests(String labtests) {
        this.labtests = labtests;
    }
    public String getPlans() {
        return this.plans;
    }
    public void setPlans(String plans) {
        this.plans = plans;
    }
    public String getNewinstructions() {
        return this.newinstructions;
    }
    public void setNewinstructions(String newinstructions) {
        this.newinstructions = newinstructions;
    }
    public long getReturnvisitdate() {
        return this.returnvisitdate;
    }
    public void setReturnvisitdate(long returnvisitdate) {
        this.returnvisitdate = returnvisitdate;
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
