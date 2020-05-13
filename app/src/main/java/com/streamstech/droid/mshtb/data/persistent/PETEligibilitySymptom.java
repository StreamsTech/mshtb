package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class PETEligibilitySymptom {
    @Id
    private Long id;
    @NotNull
    private String contactqr;
    @NotNull
    private int screenerid;
    @NotNull
    private int tbdiagnosed;
    @NotNull
    private int tbdiagnosedhow;
    @NotNull
    private int tbtype2;
    @NotNull
    private int tbtype3;
    @NotNull
    private int tbtype;
    @NotNull
    private int referred;
    @NotNull
    private int tbruledout;
    @NotNull
    private int tbruledouthow;
    @NotNull
    private int peteligible;
    @NotNull
    private String facilityname;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 880381746)
    public PETEligibilitySymptom(Long id, @NotNull String contactqr, int screenerid,
            int tbdiagnosed, int tbdiagnosedhow, int tbtype2, int tbtype3,
            int tbtype, int referred, int tbruledout, int tbruledouthow,
            int peteligible, @NotNull String facilityname, long createdtime,
            boolean uploaded, double longitude, double latitude) {
        this.id = id;
        this.contactqr = contactqr;
        this.screenerid = screenerid;
        this.tbdiagnosed = tbdiagnosed;
        this.tbdiagnosedhow = tbdiagnosedhow;
        this.tbtype2 = tbtype2;
        this.tbtype3 = tbtype3;
        this.tbtype = tbtype;
        this.referred = referred;
        this.tbruledout = tbruledout;
        this.tbruledouthow = tbruledouthow;
        this.peteligible = peteligible;
        this.facilityname = facilityname;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 2008298584)
    public PETEligibilitySymptom() {
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
    public int getTbdiagnosed() {
        return this.tbdiagnosed;
    }
    public void setTbdiagnosed(int tbdiagnosed) {
        this.tbdiagnosed = tbdiagnosed;
    }
    public int getTbdiagnosedhow() {
        return this.tbdiagnosedhow;
    }
    public void setTbdiagnosedhow(int tbdiagnosedhow) {
        this.tbdiagnosedhow = tbdiagnosedhow;
    }
    public int getTbtype2() {
        return this.tbtype2;
    }
    public void setTbtype2(int tbtype2) {
        this.tbtype2 = tbtype2;
    }
    public int getTbtype3() {
        return this.tbtype3;
    }
    public void setTbtype3(int tbtype3) {
        this.tbtype3 = tbtype3;
    }
    public int getTbtype() {
        return this.tbtype;
    }
    public void setTbtype(int tbtype) {
        this.tbtype = tbtype;
    }
    public int getReferred() {
        return this.referred;
    }
    public void setReferred(int referred) {
        this.referred = referred;
    }
    public int getTbruledout() {
        return this.tbruledout;
    }
    public void setTbruledout(int tbruledout) {
        this.tbruledout = tbruledout;
    }
    public int getTbruledouthow() {
        return this.tbruledouthow;
    }
    public void setTbruledouthow(int tbruledouthow) {
        this.tbruledouthow = tbruledouthow;
    }
    public int getPeteligible() {
        return this.peteligible;
    }
    public void setPeteligible(int peteligible) {
        this.peteligible = peteligible;
    }
    public String getFacilityname() {
        return this.facilityname;
    }
    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
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
