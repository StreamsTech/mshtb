package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class PETRegistration {
    @Id
    private Long id;
    @NotNull
    private int locationid;
    @NotNull
    private int screenerid;
    @NotNull
    private int tbtype;
    @NotNull
    private String name;
    @NotNull
    private double age;
    @NotNull
    private String gender;
    @NotNull
    private String qr;
    private String nid;
    @NotNull
    private int diagnosistype;
    @NotNull
    private String trnumber;
    @NotNull
    private int totalhouseholdmember;

    private String address;
    private String contantno;
    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 695744918)
    public PETRegistration(Long id, int locationid, int screenerid, int tbtype,
            @NotNull String name, double age, @NotNull String gender,
            @NotNull String qr, String nid, int diagnosistype,
            @NotNull String trnumber, int totalhouseholdmember, String address,
            String contantno, long createdtime, boolean uploaded, double longitude,
            double latitude) {
        this.id = id;
        this.locationid = locationid;
        this.screenerid = screenerid;
        this.tbtype = tbtype;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.qr = qr;
        this.nid = nid;
        this.diagnosistype = diagnosistype;
        this.trnumber = trnumber;
        this.totalhouseholdmember = totalhouseholdmember;
        this.address = address;
        this.contantno = contantno;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1158342949)
    public PETRegistration() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLocationid() {
        return this.locationid;
    }
    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }
    public int getScreenerid() {
        return this.screenerid;
    }
    public void setScreenerid(int screenerid) {
        this.screenerid = screenerid;
    }
    public int getTbtype() {
        return this.tbtype;
    }
    public void setTbtype(int tbtype) {
        this.tbtype = tbtype;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getAge() {
        return this.age;
    }
    public void setAge(double age) {
        this.age = age;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getQr() {
        return this.qr;
    }
    public void setQr(String qr) {
        this.qr = qr;
    }
    public String getNid() {
        return this.nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
    }
    public int getDiagnosistype() {
        return this.diagnosistype;
    }
    public void setDiagnosistype(int diagnosistype) {
        this.diagnosistype = diagnosistype;
    }
    public String getTrnumber() {
        return this.trnumber;
    }
    public void setTrnumber(String trnumber) {
        this.trnumber = trnumber;
    }
    public int getTotalhouseholdmember() {
        return this.totalhouseholdmember;
    }
    public void setTotalhouseholdmember(int totalhouseholdmember) {
        this.totalhouseholdmember = totalhouseholdmember;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContantno() {
        return this.contantno;
    }
    public void setContantno(String contantno) {
        this.contantno = contantno;
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
