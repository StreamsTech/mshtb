
package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 6/21/2018.
 */

@Entity
public class PETClinicianFollowup {

    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;

    @NotNull
    private int monthoftreatment;
    @NotNull
    private double weight;
    @NotNull
    private double height;
    @NotNull
    private double muac;

    @NotNull
    private int bmi;

    @NotNull
    private boolean anysymptom;
    private String tbsymptoms;
    private String tbsymptomdescription;
    private String sideeffects;
    private String othersideeffects;

    @NotNull
    private int consistentcomoplains;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1931117585)
    public PETClinicianFollowup(Long id, @NotNull String contactqr, int screenerid,
            int monthoftreatment, double weight, double height, double muac,
            int bmi, boolean anysymptom, String tbsymptoms,
            String tbsymptomdescription, String sideeffects,
            String othersideeffects, int consistentcomoplains, long createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.monthoftreatment = monthoftreatment;
        this.weight = weight;
        this.height = height;
        this.muac = muac;
        this.bmi = bmi;
        this.anysymptom = anysymptom;
        this.tbsymptoms = tbsymptoms;
        this.tbsymptomdescription = tbsymptomdescription;
        this.sideeffects = sideeffects;
        this.othersideeffects = othersideeffects;
        this.consistentcomoplains = consistentcomoplains;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1175470593)
    public PETClinicianFollowup() {
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
    public double getWeight() {
        return this.weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getHeight() {
        return this.height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getMuac() {
        return this.muac;
    }
    public void setMuac(double muac) {
        this.muac = muac;
    }
    public int getBmi() {
        return this.bmi;
    }
    public void setBmi(int bmi) {
        this.bmi = bmi;
    }
    public boolean getAnysymptom() {
        return this.anysymptom;
    }
    public void setAnysymptom(boolean anysymptom) {
        this.anysymptom = anysymptom;
    }
    public String getTbsymptoms() {
        return this.tbsymptoms;
    }
    public void setTbsymptoms(String tbsymptoms) {
        this.tbsymptoms = tbsymptoms;
    }
    public String getTbsymptomdescription() {
        return this.tbsymptomdescription;
    }
    public void setTbsymptomdescription(String tbsymptomdescription) {
        this.tbsymptomdescription = tbsymptomdescription;
    }
    public String getSideeffects() {
        return this.sideeffects;
    }
    public void setSideeffects(String sideeffects) {
        this.sideeffects = sideeffects;
    }
    public String getOthersideeffects() {
        return this.othersideeffects;
    }
    public void setOthersideeffects(String othersideeffects) {
        this.othersideeffects = othersideeffects;
    }
    public int getConsistentcomoplains() {
        return this.consistentcomoplains;
    }
    public void setConsistentcomoplains(int consistentcomoplains) {
        this.consistentcomoplains = consistentcomoplains;
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
