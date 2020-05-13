
package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 6/21/2018.
 */

@Entity
public class PETAdverseEventFollowup {

    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;

    @NotNull
    private int monthoftreatment;
    @NotNull
    private boolean nonseriousevent;
    private String nonseriousevents;

    @NotNull
    private boolean seriousevent;
    private String seriousevents;

    private String others;

    @NotNull
    private int consistentcomoplains;
    @NotNull
    private int symptomaction;

    private String comment;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 924106581)
    public PETAdverseEventFollowup(Long id, @NotNull String contactqr,
            int screenerid, int monthoftreatment, boolean nonseriousevent,
            String nonseriousevents, boolean seriousevent, String seriousevents,
            String others, int consistentcomoplains, int symptomaction,
            String comment, long createdtime, boolean uploaded, double longitude,
            double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.monthoftreatment = monthoftreatment;
        this.nonseriousevent = nonseriousevent;
        this.nonseriousevents = nonseriousevents;
        this.seriousevent = seriousevent;
        this.seriousevents = seriousevents;
        this.others = others;
        this.consistentcomoplains = consistentcomoplains;
        this.symptomaction = symptomaction;
        this.comment = comment;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1952012147)
    public PETAdverseEventFollowup() {
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
    public boolean getNonseriousevent() {
        return this.nonseriousevent;
    }
    public void setNonseriousevent(boolean nonseriousevent) {
        this.nonseriousevent = nonseriousevent;
    }
    public String getNonseriousevents() {
        return this.nonseriousevents;
    }
    public void setNonseriousevents(String nonseriousevents) {
        this.nonseriousevents = nonseriousevents;
    }
    public boolean getSeriousevent() {
        return this.seriousevent;
    }
    public void setSeriousevent(boolean seriousevent) {
        this.seriousevent = seriousevent;
    }
    public String getSeriousevents() {
        return this.seriousevents;
    }
    public void setSeriousevents(String seriousevents) {
        this.seriousevents = seriousevents;
    }
    public String getOthers() {
        return this.others;
    }
    public void setOthers(String others) {
        this.others = others;
    }
    public int getConsistentcomoplains() {
        return this.consistentcomoplains;
    }
    public void setConsistentcomoplains(int consistentcomoplains) {
        this.consistentcomoplains = consistentcomoplains;
    }
    public int getSymptomaction() {
        return this.symptomaction;
    }
    public void setSymptomaction(int symptomaction) {
        this.symptomaction = symptomaction;
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
