package com.streamstech.droid.mshtb.data;

import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.Patient;

import java.util.List;

/**
 * Created by AKASH-LAPTOP on 5/22/2018.
 */

public class PETPatientGroup {
    private String date;
    private int count;
    private int male;
    private int female;
    private List<PETRegistration> patientList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PETRegistration> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<PETRegistration> patientList) {
        this.patientList = patientList;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }
}
