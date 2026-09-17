package com.sece.controller;

import com.sece.entity.Course;
import com.sece.repository.CourseRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CourseRepository studentRepository;

    public AuthController(CourseRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ==========================================
    // REGISTER
    // POST /auth/register
    // ==========================================

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Course student) {

        if (studentRepository.existsByUsername(student.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        student.setId(null);

        studentRepository.save(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Registration Successful");
    }


    // ==========================================
    // LOGIN
    // POST /auth/login
    // ==========================================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {

        String username = loginData.get("username");
        String password = loginData.get("password");

        Optional<Course> optionalStudent =
                studentRepository.findByUsername(username);

        // Username doesn't exist
        if (optionalStudent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Username not found");
        }

        Course student = optionalStudent.get();

        // Password incorrect
        if (!student.getPassword().equals(password)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Password");
        }

        // Login successful
        return ResponseEntity.ok(
                Map.of(
                        "message", "Login Successful",
                        "name", student.getName()
                )
        );
    }
}