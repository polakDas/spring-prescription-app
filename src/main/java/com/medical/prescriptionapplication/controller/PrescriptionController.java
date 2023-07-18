package com.medical.prescriptionapplication.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.medical.prescriptionapplication.model.DayInfo;
import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.model.Prescription.Gender;
import com.medical.prescriptionapplication.service.PrescriptionServiceImpl;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    // @Autowired
    // private RestTemplate restTemplate;

    private final PrescriptionServiceImpl prescriptionServiceImpl;

    public PrescriptionController(PrescriptionServiceImpl prescriptionServiceImpl) {
        this.prescriptionServiceImpl = prescriptionServiceImpl;
    }

    // get all the prescriptions and send it to the root url
    @GetMapping
    public String getAllPrescriptions(@RequestParam(value = "all", defaultValue = "false") boolean all, Model model) {
        if (all == true) {
            List<Prescription> prescriptions = prescriptionServiceImpl.getAllPrescriptions();
            model.addAttribute("prescriptions", prescriptions);

            return "prescriptions";
        }

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        List<Prescription> prescriptions = prescriptionServiceImpl.getPrescriptionsByDateRange(firstDayOfMonth, today);
        model.addAttribute("prescriptions", prescriptions);

        return "prescriptions";
    }

    // get precriptions between dates
    @GetMapping("/prescriptions-between-dates")
    public String getPrescriptionsBetweenDates(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        if (endDate == null) {
            endDate = startDate;
        }

        List<Prescription> prescriptions = prescriptionServiceImpl.getPrescriptionsByDateRange(startDate, endDate);
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
    public String prescriptionReport(Model model) {
        List<DayInfo> prescriptionCount = prescriptionServiceImpl.getPrescriptionCountByDay();
        LocalDate reportDate = prescriptionCount.get(0).getDay();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy"); // like 17 July 2023
        String formattedDate = reportDate.format(formatter);

        model.addAttribute("prescriptionCount", prescriptionCount);
        model.addAttribute("formattedDate", formattedDate);
        return "prescription-report";
    }

    // consume rest API
    @GetMapping("/api-data")
    public String ApiData(Model model) {
        String uri = "https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?rxcui=341248";
        RestTemplate restTemplate = new RestTemplate();
        JsonNode jsonNode = restTemplate.getForObject(uri, JsonNode.class);
        model.addAttribute("apiData", jsonNode);

        return "api-data";
    }
}
