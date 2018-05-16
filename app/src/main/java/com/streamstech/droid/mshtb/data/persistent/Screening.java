package com.streamstech.droid.mshtb.data.persistent;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by AKASH-LAPTOP on 8/14/2017.
 */

@Entity
public class Screening {
    @Id
    private Long id;
    @NotNull
    private String patientid;
    @NotNull
    private int q3;
    @NotNull
    private int q3a;
    @NotNull
    private int q3b;
    @NotNull
    private int q4;
    @NotNull
    private int q5;
    @NotNull
    private int q6;
    @NotNull
    private int q7;
    @NotNull
    private int q7a;
    @NotNull
    private int q8;
    @NotNull
    private int q8a;
    @NotNull
    private int q9;
    @NotNull
    private int q9a;

    private String q9aothers;

    @NotNull
    private int q10;
    @NotNull
    private int q11;
    @NotNull
    private int q12;
    @NotNull
    private int q13;
    @NotNull
    private Date createdtime;
    @NotNull
    private boolean uploaded;
    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @Generated(hash = 844041961)
    public Screening(Long id, @NotNull String patientid, int q3, int q3a, int q3b,
            int q4, int q5, int q6, int q7, int q7a, int q8, int q8a, int q9,
            int q9a, String q9aothers, int q10, int q11, int q12, int q13,
            @NotNull Date createdtime, boolean uploaded, double longitude,
            double latitude) {
        this.id = id;
        this.patientid = patientid;
        this.q3 = q3;
        this.q3a = q3a;
        this.q3b = q3b;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q7a = q7a;
        this.q8 = q8;
        this.q8a = q8a;
        this.q9 = q9;
        this.q9a = q9a;
        this.q9aothers = q9aothers;
        this.q10 = q10;
        this.q11 = q11;
        this.q12 = q12;
        this.q13 = q13;
        this.createdtime = createdtime;
        this.uploaded = uploaded;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    @Generated(hash = 1448845937)
    public Screening() {
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
    public int getQ3() {
        return this.q3;
    }
    public void setQ3(int q3) {
        this.q3 = q3;
    }
    public int getQ3a() {
        return this.q3a;
    }
    public void setQ3a(int q3a) {
        this.q3a = q3a;
    }
    public int getQ3b() {
        return this.q3b;
    }
    public void setQ3b(int q3b) {
        this.q3b = q3b;
    }
    public int getQ4() {
        return this.q4;
    }
    public void setQ4(int q4) {
        this.q4 = q4;
    }
    public int getQ5() {
        return this.q5;
    }
    public void setQ5(int q5) {
        this.q5 = q5;
    }
    public int getQ6() {
        return this.q6;
    }
    public void setQ6(int q6) {
        this.q6 = q6;
    }
    public int getQ7() {
        return this.q7;
    }
    public void setQ7(int q7) {
        this.q7 = q7;
    }
    public int getQ7a() {
        return this.q7a;
    }
    public void setQ7a(int q7a) {
        this.q7a = q7a;
    }
    public int getQ8() {
        return this.q8;
    }
    public void setQ8(int q8) {
        this.q8 = q8;
    }
    public int getQ8a() {
        return this.q8a;
    }
    public void setQ8a(int q8a) {
        this.q8a = q8a;
    }
    public int getQ9() {
        return this.q9;
    }
    public void setQ9(int q9) {
        this.q9 = q9;
    }
    public int getQ9a() {
        return this.q9a;
    }
    public void setQ9a(int q9a) {
        this.q9a = q9a;
    }
    public String getQ9aothers() {
        return this.q9aothers;
    }
    public void setQ9aothers(String q9aothers) {
        this.q9aothers = q9aothers;
    }
    public int getQ10() {
        return this.q10;
    }
    public void setQ10(int q10) {
        this.q10 = q10;
    }
    public int getQ11() {
        return this.q11;
    }
    public void setQ11(int q11) {
        this.q11 = q11;
    }
    public int getQ12() {
        return this.q12;
    }
    public void setQ12(int q12) {
        this.q12 = q12;
    }
    public int getQ13() {
        return this.q13;
    }
    public void setQ13(int q13) {
        this.q13 = q13;
    }
    public Date getCreatedtime() {
        return this.createdtime;
    }
    public void setCreatedtime(Date createdtime) {
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
