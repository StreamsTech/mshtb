package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class PETEnrollment {
    @Id
    private Long id;
    @NotNull
    private int locationid;
    @NotNull
    private int screenerid;
    @NotNull
    private String name;
    @NotNull
    private double age;
    private String nid;
    @NotNull
    private String gender;
    @NotNull
    private String qr;
    @NotNull
    private String indexpatientqr;

    @NotNull
    private double weight;
    @NotNull
    private double height;
    @NotNull
    private double muac;

    @NotNull
    private int bmi;
    @NotNull
    private int pregnancy;
    @NotNull
    private int relation;
    private String relationother;

    @NotNull
    private long createdtime;
    @NotNull
    private boolean uploaded;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 1100232341)
    public PETEnrollment(Long id, int locationid, int screenerid,
            @NotNull String name, double age, String nid, @NotNull String gender,
            @NotNull String qr, @NotNull String indexpatientqr, double weight,
            double height, double muac, int bmi, int pregnancy, int relation,
            String relationother, long createdtime, boolean uploaded,
            double longitude, double latitude) {
        this.id = id;
        this.locationid = locationid;
        this.screenerid = screenerid;
        this.name = name;
        this.age = age;
        this.nid = nid;
        this.gender = gender;
        this.qr = qr;
        this.indexpatientqr = indexpatientqr;
        this.weight = weight;
        this.height = height;
        this.muac = muac;
        this.bmi = bmi;
        this.pregnancy = pregnancy;
        this.relation = relation;
        this.relationother = relationother;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 514365156)
    public PETEnrollment() {
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
    public String getNid() {
        return this.nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
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
    public String getIndexpatientqr() {
        return this.indexpatientqr;
    }
    public void setIndexpatientqr(String indexpatientqr) {
        this.indexpatientqr = indexpatientqr;
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
    public int getPregnancy() {
        return this.pregnancy;
    }
    public void setPregnancy(int pregnancy) {
        this.pregnancy = pregnancy;
    }
    public int getRelation() {
        return this.relation;
    }
    public void setRelation(int relation) {
        this.relation = relation;
    }
    public String getRelationother() {
        return this.relationother;
    }
    public void setRelationother(String relationother) {
        this.relationother = relationother;
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
