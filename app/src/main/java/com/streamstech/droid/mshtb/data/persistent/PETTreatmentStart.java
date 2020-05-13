
package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 6/21/2018.
 */

@Entity
public class PETTreatmentStart {

    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;

    @NotNull
    private int regimen;
    @NotNull
    private String drugone;
    @NotNull
    private String drugtwo;
    @NotNull
    private long treatmentstartdate;
    @NotNull
    private String supportername;
    @NotNull
    private String supporternumber;
    @NotNull
    private int supporterrelation;
    @NotNull
    private String supporterother;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1684838438)
    public PETTreatmentStart(Long id, @NotNull String contactqr, int screenerid,
            int regimen, @NotNull String drugone, @NotNull String drugtwo,
            long treatmentstartdate, @NotNull String supportername,
            @NotNull String supporternumber, int supporterrelation,
            @NotNull String supporterother, long createdtime, boolean uploaded,
            double longitude, double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.regimen = regimen;
        this.drugone = drugone;
        this.drugtwo = drugtwo;
        this.treatmentstartdate = treatmentstartdate;
        this.supportername = supportername;
        this.supporternumber = supporternumber;
        this.supporterrelation = supporterrelation;
        this.supporterother = supporterother;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1253370072)
    public PETTreatmentStart() {
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
    public int getRegimen() {
        return this.regimen;
    }
    public void setRegimen(int regimen) {
        this.regimen = regimen;
    }
    public String getDrugone() {
        return this.drugone;
    }
    public void setDrugone(String drugone) {
        this.drugone = drugone;
    }
    public String getDrugtwo() {
        return this.drugtwo;
    }
    public void setDrugtwo(String drugtwo) {
        this.drugtwo = drugtwo;
    }
    public long getTreatmentstartdate() {
        return this.treatmentstartdate;
    }
    public void setTreatmentstartdate(long treatmentstartdate) {
        this.treatmentstartdate = treatmentstartdate;
    }
    public String getSupportername() {
        return this.supportername;
    }
    public void setSupportername(String supportername) {
        this.supportername = supportername;
    }
    public String getSupporternumber() {
        return this.supporternumber;
    }
    public void setSupporternumber(String supporternumber) {
        this.supporternumber = supporternumber;
    }
    public int getSupporterrelation() {
        return this.supporterrelation;
    }
    public void setSupporterrelation(int supporterrelation) {
        this.supporterrelation = supporterrelation;
    }
    public String getSupporterother() {
        return this.supporterother;
    }
    public void setSupporterother(String supporterother) {
        this.supporterother = supporterother;
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
