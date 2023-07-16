package com.medical.prescriptionapplication.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Prescription date is required.")
    @Column(name = "prescription_date", nullable = false)
    private Date prescriptionDate;

    @NotBlank(message = "Patient name is required.")
    @Column(name = "patient_name", length = 120, nullable = false)
    private String PatientName;

    @NotBlank(message = "Patient age is required.")
    @Min(value = 0, message = "Patient age must be at least zero")
    @Max(value = 150, message = "Maximum age for a patient is 150")
    @Column(name = "patient_age", nullable = false)
    private int patinetAge;

    @NotBlank(message = "Patient gender is required.")
    @Enumerated(EnumType.STRING)
    @Column(name = "patient_gender", length = 10, nullable = false)
    private Gender patientGender;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String diagnosis;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String medicines;

    @Temporal(TemporalType.DATE)
    @Column(name = "next_visit_date")
    private Date nextVisitDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public int getPatinetAge() {
        return patinetAge;
    }

    public void setPatinetAge(int patinetAge) {
        this.patinetAge = patinetAge;
    }

    public Gender getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(Gender patientGender) {
        this.patientGender = patientGender;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public Date getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(Date nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

}
