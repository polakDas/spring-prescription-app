package com.medical.prescriptionapplication.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.service.PrescriptionServiceImpl;

@RestController
@RequestMapping("/api/v1/prescription")
public class ApiPrescriptionController {
    private final PrescriptionServiceImpl prescriptionServiceImpl;

    public ApiPrescriptionController(PrescriptionServiceImpl prescriptionServiceImpl) {
        this.prescriptionServiceImpl = prescriptionServiceImpl;
    }

    // get all the prescription for root api endpoint
    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionServiceImpl.getAllPrescriptions();

        return ResponseEntity.ok(prescriptions);
    }

    // paginated prescriptions
    @GetMapping("/paginated")
    public ResponseEntity<Page<Prescription>> getPaginatedPrescriptions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Prescription> paginatedPrescriptions = prescriptionServiceImpl.getPaginatedPrescriptions(pageable);

        return ResponseEntity.ok(paginatedPrescriptions);
    }

}
