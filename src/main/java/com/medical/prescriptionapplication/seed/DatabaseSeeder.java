package com.medical.prescriptionapplication.seed;

import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.model.Prescription.Gender;
import com.medical.prescriptionapplication.model.User;
import com.medical.prescriptionapplication.repository.PrescriptionRepository;
import com.medical.prescriptionapplication.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final PrescriptionRepository prescriptionRepository;
    private final UserRepository userRepository;

    public DatabaseSeeder(PrescriptionRepository prescriptionRepository, UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("user@user.com", "password");
        user1.setUsername("User One");
        User user2 = new User("admin@admin.com", "password");
        user2.setUsername("User Two");
        userRepository.save(user1);
        userRepository.save(user2);

        Prescription prescription1 = new Prescription(LocalDate.now(), "Patient 1", 25, Gender.MALE);
        prescription1.setDiagnosis("Sample Diagnosis 1");
        prescription1.setMedicines("Sample Medicine 1");
        prescription1.setNextVisitDate(LocalDate.now().plusDays(7));
        prescriptionRepository.save(prescription1);

        Prescription prescription2 = new Prescription(LocalDate.now(), "Patient 2", 30, Gender.FEMALE);
        prescription2.setDiagnosis("Sample Diagnosis 2");
        prescription2.setMedicines("Sample Medicine 2");
        prescription2.setNextVisitDate(LocalDate.now().plusDays(14));
        prescriptionRepository.save(prescription2);
    }
}
