
package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 6/21/2018.
 */

@Entity
public class PETAdherence {

    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;

    @NotNull
    private int monthoftreatment;
    @NotNull
    private int weekoftreatment;
    @NotNull
    private int adrerence;

    @NotNull
    private String adverseeffect;
    @NotNull
    private int seriousadverse;
    @NotNull
    private String comment;
    @NotNull
    private int doctornotified;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 50727515)
    public PETAdherence(Long id, @NotNull String contactqr, int screenerid,
            int monthoftreatment, int weekoftreatment, int adrerence,
            @NotNull String adverseeffect, int seriousadverse,
            @NotNull String comment, int doctornotified, long createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.monthoftreatment = monthoftreatment;
        this.weekoftreatment = weekoftreatment;
        this.adrerence = adrerence;
        this.adverseeffect = adverseeffect;
        this.seriousadverse = seriousadverse;
        this.comment = comment;
        this.doctornotified = doctornotified;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1752204764)
    public PETAdherence() {
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
    public int getWeekoftreatment() {
        return this.weekoftreatment;
    }
    public void setWeekoftreatment(int weekoftreatment) {
        this.weekoftreatment = weekoftreatment;
    }
    public int getAdrerence() {
        return this.adrerence;
    }
    public void setAdrerence(int adrerence) {
        this.adrerence = adrerence;
    }
    public String getAdverseeffect() {
        return this.adverseeffect;
    }
    public void setAdverseeffect(String adverseeffect) {
        this.adverseeffect = adverseeffect;
    }
    public int getSeriousadverse() {
        return this.seriousadverse;
    }
    public void setSeriousadverse(int seriousadverse) {
        this.seriousadverse = seriousadverse;
    }
    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public int getDoctornotified() {
        return this.doctornotified;
    }
    public void setDoctornotified(int doctornotified) {
        this.doctornotified = doctornotified;
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
