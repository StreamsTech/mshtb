package com.streamstech.droid.mshtb.data;

import com.streamstech.droid.mshtb.data.persistent.Outcome;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathology;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmear;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPert;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.Treatment;
import java.util.Date;
import java.util.List;

/**
 * Created by AKASH-LAPTOP on 5/26/2018.
 */

public class UpdateModels {
    private List<Patient> patientList;
    private List<Screening> screeningforms;
    private List<TestIndication> testindications;
    private List<TestResultXRay> testresultxrays;
    private List<TestResultXPert> texTestresultxperts;
    private List<TestResultSmear> testresultsmears;
    private List<TestResultHistopathology> testresulthistos;
    private List<Treatment> treatmentintiations;
    private List<Outcome> treatmentoutcomes;
    private long lastupdatetime;
    private String lastupdatetimestring;

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Screening> getScreeningforms() {
        return screeningforms;
    }

    public void setScreeningforms(List<Screening> screeningforms) {
        this.screeningforms = screeningforms;
    }

    public List<TestIndication> getTestindications() {
        return testindications;
    }

    public void setTestindications(List<TestIndication> testindications) {
        this.testindications = testindications;
    }

    public List<TestResultXRay> getTestresultxrays() {
        return testresultxrays;
    }

    public void setTestresultxrays(List<TestResultXRay> testresultxrays) {
        this.testresultxrays = testresultxrays;
    }

    public List<TestResultXPert> getTexTestresultxperts() {
        return texTestresultxperts;
    }

    public void setTexTestresultxperts(List<TestResultXPert> texTestresultxperts) {
        this.texTestresultxperts = texTestresultxperts;
    }

    public List<TestResultSmear> getTestresultsmears() {
        return testresultsmears;
    }

    public void setTestresultsmears(List<TestResultSmear> testresultsmears) {
        this.testresultsmears = testresultsmears;
    }

    public List<TestResultHistopathology> getTestresulthistos() {
        return testresulthistos;
    }

    public void setTestresulthistos(List<TestResultHistopathology> testresulthistos) {
        this.testresulthistos = testresulthistos;
    }

    public List<Treatment> getTreatmentintiations() {
        return treatmentintiations;
    }

    public void setTreatmentintiations(List<Treatment> treatmentintiations) {
        this.treatmentintiations = treatmentintiations;
    }

    public List<Outcome> getTreatmentoutcomes() {
        return treatmentoutcomes;
    }

    public void setTreatmentoutcomes(List<Outcome> treatmentoutcomes) {
        this.treatmentoutcomes = treatmentoutcomes;
    }

    public String getLastupdatetimestring() {
        return lastupdatetimestring;
    }

    public void setLastupdatetimestring(String lastupdatetimestring) {
        this.lastupdatetimestring = lastupdatetimestring;
    }

    public long getLastupdatetime() {
        return lastupdatetime;
    }

    public void setLastupdatetime(long lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }
}

