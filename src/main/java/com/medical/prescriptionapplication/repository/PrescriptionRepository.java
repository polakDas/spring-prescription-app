package com.medical.prescriptionapplication.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.medical.prescriptionapplication.model.DayInfo;
import com.medical.prescriptionapplication.model.Prescription;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPrescriptionDateBetween(LocalDate startDate, LocalDate endDate, Sort sortByDate);

    @Query("SELECT new com.medical.prescriptionapplication.model.DayInfo(p.prescriptionDate, COUNT(p.prescriptionDate)) FROM Prescription p GROUP BY p.prescriptionDate")
    List<DayInfo> getPrescriptionCountByDay();
}
