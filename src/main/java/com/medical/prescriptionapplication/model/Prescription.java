package com.medical.prescriptionapplication.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "prescriptions")
public class Prescription {

    public Prescription() {
    }

    public Prescription(LocalDate prescriptionDate, String patientName, int patientAge, Gender patientGender) {
        this.prescriptionDate = prescriptionDate;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Prescription date is required.")
    @PastOrPresent(message = "Prescription date must be in past or today.")
    @Column(name = "prescription_date", nullable = false)
    private LocalDate prescriptionDate;

    @NotNull(message = "Patient name is required.")
    @Column(name = "patient_name", length = 120, nullable = false)
    private String patientName;

    @NotNull(message = "Patient age is required.")
    @Min(value = 0, message = "Patient age must be at least zero")
    @Max(value = 150, message = "Maximum age for a patient is 150")
    @Column(name = "patient_age", nullable = false)
    private int patientAge;

    @NotNull(message = "Patient gender is required.")
    @Enumerated(EnumType.STRING)
    @Column(name = "patient_gender", length = 10, nullable = false)
    private Gender patientGender;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String diagnosis;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String medicines;

    @Temporal(TemporalType.DATE)
    @FutureOrPresent(message = "Next visit date must be in the future.")
    @Column(name = "next_visit_date")
    private LocalDate nextVisitDate;

    @ManyToOne
    private User user;

    @Override
    public String toString() {
        return "Prescription [id=" + id + ", prescriptionDate=" + prescriptionDate + ", patientName=" + patientName
                + ", patientAge=" + patientAge + ", patientGender=" + patientGender + ", diagnosis=" + diagnosis
                + ", medicines=" + medicines + ", nextVisitDate=" + nextVisitDate + ", user=" + user + "]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
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

    public LocalDate getNextVisitDate() {
        return nextVisitDate;
    }

    public void setNextVisitDate(LocalDate nextVisitDate) {
        this.nextVisitDate = nextVisitDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
