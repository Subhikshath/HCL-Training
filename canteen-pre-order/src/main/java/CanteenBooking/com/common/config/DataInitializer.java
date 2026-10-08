package CanteenBooking.com.common.config;

import CanteenBooking.com.user.entity.Role;
import CanteenBooking.com.user.entity.User;
import CanteenBooking.com.user.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            /*
             * Create Admin
             */
            if (!userRepository.existsByEmail(
                    "admin@bitsathy.ac.in")) {

                User admin = new User(
                        "Admin User",
                        "admin@bitsathy.ac.in",
                        passwordEncoder.encode("admin123"),
                        "9000000001",
                        Role.ADMIN
                );

                userRepository.save(admin);
            }


            /*
             * Create Staff
             */
            if (!userRepository.existsByEmail(
                    "staff@bitsathy.ac.in")) {

                User staff = new User(
                        "Staff User",
                        "staff@bitsathy.ac.in",
                        passwordEncoder.encode("staff123"),
                        "9000000002",
                        Role.STAFF
                );

                userRepository.save(staff);
            }


            /*
             * Create Student
             */
            if (!userRepository.existsByEmail(
                    "student@bitsathy.ac.in")) {

                User student = new User(
                        "Student User",
                        "student@bitsathy.ac.in",
                        passwordEncoder.encode("student123"),
                        "9000000003",
                        Role.STUDENT
                );

                userRepository.save(student);
            }
        };
    }
}