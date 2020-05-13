
package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 6/21/2018.
 */

@Entity
public class PETFollowupEnd {

    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;

    @NotNull
    private int monthoftreatment;
    @NotNull
    private int reasontoend;
    private String enddescription;

    private String comment;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 362406605)
    public PETFollowupEnd(Long id, @NotNull String contactqr, int screenerid,
            int monthoftreatment, int reasontoend, String enddescription,
            String comment, long createdtime, boolean uploaded, double longitude,
            double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.monthoftreatment = monthoftreatment;
        this.reasontoend = reasontoend;
        this.enddescription = enddescription;
        this.comment = comment;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 293781600)
    public PETFollowupEnd() {
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
    public int getMonthoftreatment() {
        return this.monthoftreatment;
    }
    public void setMonthoftreatment(int monthoftreatment) {
        this.monthoftreatment = monthoftreatment;
    }
    public int getReasontoend() {
        return this.reasontoend;
    }
    public void setReasontoend(int reasontoend) {
        this.reasontoend = reasontoend;
    }
    public String getEnddescription() {
        return this.enddescription;
    }
    public void setEnddescription(String enddescription) {
        this.enddescription = enddescription;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
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
