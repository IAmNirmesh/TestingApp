package com.android.docapp.model;

import io.realm.RealmObject;

/**
 * Created by hp pc on 27-08-2016.
 */
public class PatientInfoModel extends RealmObject {

    private String patientName;
    private String patientPhoneNumber;
    private int patientAge = 0;
    private boolean isPatientSuffersMigraine;
    private boolean isPatientUseHalluDrugs;
    private boolean isPatientMale;
    private String toddSyndromeProbability;

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhoneNumber() {
        return patientPhoneNumber;
    }

    public void setPatientPhoneNumber(String patientPhoneNumber) {
        this.patientPhoneNumber = patientPhoneNumber;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public boolean isPatientSuffersMigraine() {
        return isPatientSuffersMigraine;
    }

    public void setPatientSuffersMigraine(boolean patientSuffersMigraine) {
        isPatientSuffersMigraine = patientSuffersMigraine;
    }

    public boolean isPatientUseHalluDrugs() {
        return isPatientUseHalluDrugs;
    }

    public void setPatientUseHalluDrugs(boolean patientUseHalluDrugs) {
        isPatientUseHalluDrugs = patientUseHalluDrugs;
    }

    public boolean isPatientMale() {
        return isPatientMale;
    }

    public void setPatientMale(boolean patientMale) {
        isPatientMale = patientMale;
    }

    public String getToddSyndromeProbability() {
        return toddSyndromeProbability;
    }

    public void setToddSyndromeProbability(String toddSyndromeProbability) {
        this.toddSyndromeProbability = toddSyndromeProbability;
    }
}
