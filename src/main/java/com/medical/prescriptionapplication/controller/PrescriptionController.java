package com.medical.prescriptionapplication.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.model.Prescription.Gender;
import com.medical.prescriptionapplication.service.PrescriptionServiceImpl;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {
    private final PrescriptionServiceImpl prescriptionServiceImpl;

    public PrescriptionController(PrescriptionServiceImpl prescriptionServiceImpl) {
        this.prescriptionServiceImpl = prescriptionServiceImpl;
    }

    // get all the prescriptions and send it to the root url
    @GetMapping
    public String getAllPrescriptions(Model model) {
        List<Prescription> prescriptions = prescriptionServiceImpl.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);

        return "prescriptions";
    }

    // get prescription details by prescription's id
    @GetMapping("/{id}")
    public String getPrescriptionDetails(@PathVariable Long id, Model model) {
        Optional<Prescription> prescription = prescriptionServiceImpl.getPrescriptionById(id);
        model.addAttribute("prescription", prescription.get());

        return "prescription-details";
    }

    // show the form for new prescription
    @GetMapping("/new")
    public String showPrescriptionCreateForm(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("prescription", new Prescription());
        return "create-prescription-form";
    }

    // store new prescription
    @PostMapping
    public String createPrescription(Prescription prescription, Model model) {
        Prescription createdPrescription = prescriptionServiceImpl.createPrescription(prescription);
        model.addAttribute("prescription", createdPrescription);
        return "redirect:/prescriptions";
    }

    // edit form
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Prescription> prescription = prescriptionServiceImpl.getPrescriptionById(id);
        if (prescription.isPresent()) {
            model.addAttribute("prescription", prescription.get());
        } else {
            return "Nothing found";
        }
        // pass gender information
        model.addAttribute("genders", Gender.values());

        // format the date
        // failed
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("formattedPrescriptionDate", prescription.get().getPrescriptionDate().format(dateFormatter));
        model.addAttribute("formattedNextVisitDate", prescription.get().getNextVisitDate().format(dateFormatter));

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
    @PostMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionServiceImpl.deletePrescription(id);

        return "redirect:/prescriptions";
    }

    @GetMapping("/report")
    public String prescriptionReport() {

        return "prescription-report";
    }
}
