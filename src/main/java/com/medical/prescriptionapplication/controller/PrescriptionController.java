package com.medical.prescriptionapplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.service.PrescriptionServiceImpl;

@Controller
@RequestMapping
public class PrescriptionController {
    private final PrescriptionServiceImpl prescriptionServiceImpl;

    public PrescriptionController(PrescriptionServiceImpl prescriptionServiceImpl) {
        this.prescriptionServiceImpl = prescriptionServiceImpl;
    }

    // get all the prescriptions and send it to the root url
    @GetMapping("/")
    public String getAllPrescriptions(Model model) {
        List<Prescription> prescriptions = prescriptionServiceImpl.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);

        return "prescriptions";
    }

    // get prescription details by prescription's id
    @GetMapping("/{id}")
    public String getPrescriptionDetails(@PathVariable Long id, Model model) {
        Optional<Prescription> prescription = prescriptionServiceImpl.getPrescriptionById(id);
        model.addAttribute("prescription", prescription);

        return "prescription-details";
    }

    // show the form for new prescription
    @GetMapping("/new")
    public String showPrescriptionCreateForm(Model model) {
        return "create-prescription-form";
    }

    // store new prescription
    @PostMapping("/")
    public String createPrescription(@RequestBody Prescription prescription, Model model) {
        Prescription createdPrescription = prescriptionServiceImpl.createPrescription(prescription);
        model.addAttribute(createdPrescription);
        return "redirect:/prescription";
    }

    // edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Prescription> prescription = prescriptionServiceImpl.getPrescriptionById(id);
        model.addAttribute("prescription", prescription);
        // Logic to prepare data for the edit form
        return "edit-prescription-form";
    }

    // update a existing prescription
    @PutMapping("/{id}")
    public String updatePrescription(@PathVariable Long id, @ModelAttribute Prescription prescription) {
        prescription.setId(id);
        prescriptionServiceImpl.updatePrescription(prescription);
        return "redirect:/prescriptions";
    }

    public Prescription updatePrescriptionNew(Long id, Prescription updatedPrescription) {
        Optional<Prescription> existingPrescriptionOptional = prescriptionServiceImpl.getPrescriptionById(id);
        if (existingPrescriptionOptional.isPresent()) {
            Prescription existingPrescription = existingPrescriptionOptional.get();
            existingPrescription.setPatientName(updatedPrescription.getPatientName());
            existingPrescription.setDiagnosis(updatedPrescription.getDiagnosis());

            // return prescriptionServiceImpl.save(existingPrescription);
        }
        return null;
    }

    // delete a prescription if exist
    @DeleteMapping("/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionServiceImpl.deletePrescription(id);
        return "redirect:/prescriptions";
    }
}
