package com.streamstech.droid.mshtb.data;

import com.streamstech.droid.mshtb.data.persistent.Outcome;
import com.streamstech.droid.mshtb.data.persistent.PETAdherence;
import com.streamstech.droid.mshtb.data.persistent.PETAdverseEventFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETClinicianTBReview;
import com.streamstech.droid.mshtb.data.persistent.PETEligibilitySymptom;
import com.streamstech.droid.mshtb.data.persistent.PETEnrollment;
import com.streamstech.droid.mshtb.data.persistent.PETFollowup;
import com.streamstech.droid.mshtb.data.persistent.PETFollowupEnd;
import com.streamstech.droid.mshtb.data.persistent.PETRegistration;
import com.streamstech.droid.mshtb.data.persistent.PETSymptom;
import com.streamstech.droid.mshtb.data.persistent.PETTestOrder;
import com.streamstech.droid.mshtb.data.persistent.PETTestResult;
import com.streamstech.droid.mshtb.data.persistent.PETTreatmentStart;
import com.streamstech.droid.mshtb.data.persistent.Patient;
import com.streamstech.droid.mshtb.data.persistent.Screening;
import com.streamstech.droid.mshtb.data.persistent.TestIndication;
import com.streamstech.droid.mshtb.data.persistent.TestResultHistopathology;
import com.streamstech.droid.mshtb.data.persistent.TestResultSmear;
import com.streamstech.droid.mshtb.data.persistent.TestResultXPert;
import com.streamstech.droid.mshtb.data.persistent.TestResultXRay;
import com.streamstech.droid.mshtb.data.persistent.Treatment;

import java.util.List;

/**
 * Created by AKASH-LAPTOP on 5/26/2018.
 */

public class UpdateModelsPET {
    private List<PETRegistration> registrationList;
    private List<PETEnrollment> enrollmentList;
    private List<PETSymptom> symptomList;
    private List<PETTestOrder> testOrderList;
    private List<PETTestResult> testResultList;
    private List<PETEligibilitySymptom> eligibilitySymptomList;
    private List<PETTreatmentStart> treatmentStartList;
    private List<PETAdherence> adherenceList;
    private List<PETFollowup> followupList;
    private List<PETClinicianFollowup> clinicianFollowupList;
    private List<PETClinicianTBReview> clinicianTBReviewList;
    private List<PETAdverseEventFollowup> petAdverseEventFollowupList;
    private List<PETFollowupEnd> followupEndList;
    private long lastupdatetime;
    private String lastupdatetimestring;

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

    public List<PETRegistration> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<PETRegistration> registrationList) {
        this.registrationList = registrationList;
    }

    public List<PETEnrollment> getEnrollmentList() {
        return enrollmentList;
    }

    public void setEnrollmentList(List<PETEnrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    public List<PETSymptom> getSymptomList() {
        return symptomList;
    }

    public void setSymptomList(List<PETSymptom> symptomList) {
        this.symptomList = symptomList;
    }

    public List<PETTestOrder> getTestOrderList() {
        return testOrderList;
    }

    public void setTestOrderList(List<PETTestOrder> testOrderList) {
        this.testOrderList = testOrderList;
    }

    public List<PETTestResult> getTestResultList() {
        return testResultList;
    }

    public void setTestResultList(List<PETTestResult> testResultList) {
        this.testResultList = testResultList;
    }

    public List<PETEligibilitySymptom> getEligibilitySymptomList() {
        return eligibilitySymptomList;
    }

    public void setEligibilitySymptomList(List<PETEligibilitySymptom> eligibilitySymptomList) {
        this.eligibilitySymptomList = eligibilitySymptomList;
    }

    public List<PETTreatmentStart> getTreatmentStartList() {
        return treatmentStartList;
    }

    public void setTreatmentStartList(List<PETTreatmentStart> treatmentStartList) {
        this.treatmentStartList = treatmentStartList;
    }

    public List<PETAdherence> getAdherenceList() {
        return adherenceList;
    }

    public void setAdherenceList(List<PETAdherence> adherenceList) {
        this.adherenceList = adherenceList;
    }

    public List<PETFollowup> getFollowupList() {
        return followupList;
    }

    public void setFollowupList(List<PETFollowup> followupList) {
        this.followupList = followupList;
    }

    public List<PETClinicianFollowup> getClinicianFollowupList() {
        return clinicianFollowupList;
    }

    public void setClinicianFollowupList(List<PETClinicianFollowup> clinicianFollowupList) {
        this.clinicianFollowupList = clinicianFollowupList;
    }

    public List<PETClinicianTBReview> getClinicianTBReviewList() {
        return clinicianTBReviewList;
    }

    public void setClinicianTBReviewList(List<PETClinicianTBReview> clinicianTBReviewList) {
        this.clinicianTBReviewList = clinicianTBReviewList;
    }

    public List<PETAdverseEventFollowup> getPetAdverseEventFollowupList() {
        return petAdverseEventFollowupList;
    }

    public void setPetAdverseEventFollowupList(List<PETAdverseEventFollowup> petAdverseEventFollowupList) {
        this.petAdverseEventFollowupList = petAdverseEventFollowupList;
    }

    public List<PETFollowupEnd> getFollowupEndList() {
        return followupEndList;
    }

    public void setFollowupEndList(List<PETFollowupEnd> followupEndList) {
        this.followupEndList = followupEndList;
    }
}

