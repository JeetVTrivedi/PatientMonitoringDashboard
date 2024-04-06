package com.application.mark1;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientData {
    private String PatientID;
    private String PatientName;

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String patientID) {
        this.PatientID = patientID;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        this.PatientName = patientName;
    }

    public String getPatientGender() {
        return PatientGender;
    }

    public void setPatientGender(String patientGender) {
        this.PatientGender = patientGender;
    }

    public Integer getPatientPhone() {
        return PatientPhone;
    }

    public void setPatientPhone(Integer patientPhone) {
        this.PatientPhone = patientPhone;
    }

    public String getPatientPass() {
        return PatientPass;
    }

    public void setPatientPass(String patientPass) {
        this.PatientPass = patientPass;
    }

    public String getHealthIssue() {
        return HealthIssue;
    }

    public void setHealthIssue(String healthIssue) {
        this.HealthIssue = healthIssue;
    }

    public Integer getTemp() {
        return Temp;
    }

    public void setTemp(Integer temp) {
        this.Temp = temp;
    }

    public Integer getBp() {
        return Bp;
    }

    public void setBp(Integer bp) {
        this.Bp = bp;
    }

    public Integer getSpO2() {
        return SpO2;
    }

    public void setSpO2(Integer spO2) {
        this.SpO2 = spO2;
    }

    public Integer getBloodSugar() {
        return BloodSugar;
    }

    public void setBloodSugar(Integer bloodSugar) {
        this.BloodSugar = bloodSugar;
    }

    public Integer getECG() {
        return ECG;
    }

    public void setECG(Integer ECG) {
        this.ECG = ECG;
    }

    private String PatientGender;
    private Integer PatientPhone;
    private String PatientPass;
    private String HealthIssue;
    private Integer Temp;
    private Integer Bp;
    private Integer SpO2;
    private Integer BloodSugar;
    private Integer ECG;

    public PatientData(String patientID, String patientName, String patientGender, Integer patientPhone, String patientPass, String healthIssue, Integer temp, Integer bp, Integer spO2, Integer bloodSugar, Integer ECG) {
        this.PatientID = patientID;
        this.PatientName = patientName;
        this.PatientGender = patientGender;
        this.PatientPhone = patientPhone;
        this.PatientPass = patientPass;
        this.HealthIssue = healthIssue;
        this.Temp = temp;
        this.Bp = bp;
        this.SpO2 = spO2;
        this.BloodSugar = bloodSugar;
        this.ECG = ECG;
    }
}
