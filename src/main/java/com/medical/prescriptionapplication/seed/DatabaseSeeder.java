package com.medical.prescriptionapplication.seed;

import com.github.javafaker.Faker;
import com.medical.prescriptionapplication.model.AppUser;
import com.medical.prescriptionapplication.model.Prescription;
import com.medical.prescriptionapplication.model.Role;
import com.medical.prescriptionapplication.model.Prescription.Gender;
import com.medical.prescriptionapplication.repository.PrescriptionRepository;
import com.medical.prescriptionapplication.repository.RoleRepository;
import com.medical.prescriptionapplication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Faker faker = new Faker();

    private final PrescriptionRepository prescriptionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DatabaseSeeder(PrescriptionRepository prescriptionRepository, RoleRepository roleRepository,
            UserRepository userRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleUser = new Role("USER");
        Role roleAdmin = new Role("ADMIN");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        AppUser user1 = new AppUser("user", passwordEncoder.encode("user"), roles);
        AppUser user2 = new AppUser("admin", passwordEncoder.encode("admin"), roles);
        userRepository.save(user1);
        userRepository.save(user2);

        for (int i = 1; i <= 40; i++) {
            Prescription prescription = new Prescription(LocalDate.now().minusDays(faker.number().numberBetween(0, 40)),
                    faker.name().fullName(),
                    faker.number().numberBetween(0, 100), Gender.values()[faker.number().numberBetween(1, 2)]);
            prescription.setDiagnosis("Sample Diagnosis " + faker.lorem().sentence());
            prescription.setMedicines("Sample Medicine " + faker.lorem().sentence());
            prescription.setNextVisitDate(LocalDate.now().plusDays(faker.number().numberBetween(1, 40)));
            prescriptionRepository.save(prescription);
        }
    }
}
