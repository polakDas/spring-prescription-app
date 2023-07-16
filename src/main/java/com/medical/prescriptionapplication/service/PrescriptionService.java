package com.medical.prescriptionapplication.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.medical.prescriptionapplication.model.Prescription;

public interface PrescriptionService {

    List<Prescription> getAllPrescriptions();

    Optional<Prescription> getPrescriptionById(Long id);

    Prescription createPrescription(Prescription prescription);

    Prescription updatePrescription(Prescription prescription);

    boolean deletePrescription(Long id);

    List<Prescription> getPrescriptionsByDateRange(LocalDate startDate, LocalDate endDate);

    Page<Prescription> getPaginatedPrescriptions(Pageable pageable);
}
