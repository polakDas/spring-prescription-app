package com.medical.prescriptionapplication.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.medical.prescriptionapplication.model.DayInfo;
import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public List<Prescription> getAllPrescriptions() {
        Sort sortByDate = Sort.by(Sort.Direction.DESC, "prescriptionDate");
        return prescriptionRepository.findAll(sortByDate);
    }

    @Override
    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    @Override
    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public Prescription updatePrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    @Override
    public boolean deletePrescription(Long id) {
        if (prescriptionRepository.existsById(id)) {
            prescriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Prescription> getPrescriptionsByDateRange(LocalDate startDate, LocalDate endDate) {
        Sort sortByDate = Sort.by(Sort.Direction.DESC, "prescriptionDate");
        return prescriptionRepository.findByPrescriptionDateBetween(startDate, endDate, sortByDate);
    }

    @Override
    public Page<Prescription> getPaginatedPrescriptions(Pageable pageable) {
        return prescriptionRepository.findAll(pageable);
    }

    @Override
    public List<DayInfo> getPrescriptionCountByDay() {
        return prescriptionRepository.getPrescriptionCountByDay();
    }

}
