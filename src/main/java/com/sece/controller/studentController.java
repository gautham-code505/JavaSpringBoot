package com.sece.controller;

import com.sece.entity.Course;
import com.sece.repository.CourseRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class studentController {

    private final CourseRepository studentRepository;

    public studentController(CourseRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // =========================
    // GET ALL STUDENTS
    // GET /student
    // =========================
    @GetMapping
    public List<Course> getAllStudents() {
        return studentRepository.findAll();
    }

    // =========================
    // GET STUDENT BY ID
    // GET /student/{id}
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {

        Optional<Course> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Student with ID " + id + " not found");
    }

    // =========================
    // ADD ONE STUDENT
    // POST /student
    // =========================
    @PostMapping
    public ResponseEntity<Course> addStudent(
            @RequestBody Course student) {

        student.setId(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentRepository.save(student));
    }

    // =========================
    // ADD MULTIPLE STUDENTS
    // POST /student/bulk
    // =========================
    @PostMapping("/bulk")
    public ResponseEntity<List<Course>> addStudents(
            @RequestBody List<Course> students) {

        students.forEach(student -> student.setId(null));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(studentRepository.saveAll(students));
    }

    // =========================
    // UPDATE ONE STUDENT
    // PUT /student/{id}
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestBody Course details) {

        Optional<Course> optionalStudent =
                studentRepository.findById(id);

        if (optionalStudent.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student with ID " + id + " not found");
        }

        Course student = optionalStudent.get();

        student.setName(details.getName());
        student.setDepartment(details.getDepartment());

        return ResponseEntity.ok(studentRepository.save(student));
    }

    // =========================
    // UPDATE MULTIPLE STUDENTS
    // PUT /student/bulk
    // =========================
    @PutMapping("/bulk")
    public ResponseEntity<?> updateStudents(
            @RequestBody List<Course> students) {

        for (Course details : students) {

            if (details.getId() == null) {
                continue;
            }

            Optional<Course> existing =
                    studentRepository.findById(details.getId());

            if (existing.isPresent()) {
                Course student = existing.get();

                student.setName(details.getName());
                student.setDepartment(details.getDepartment());

                studentRepository.save(student);
            }
        }

        return ResponseEntity.ok(
                studentRepository.findAll()
        );
    }

    // =========================
    // DELETE ONE STUDENT
    // DELETE /student/{id}
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student with ID " + id + " not found");
        }

        studentRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // =========================
    // DELETE MULTIPLE STUDENTS
    // DELETE /student/bulk
    // =========================
    @DeleteMapping("/bulk")
    public ResponseEntity<Void> deleteStudents(
            @RequestBody List<Long> ids) {

        studentRepository.deleteAllById(ids);

        return ResponseEntity.noContent().build();
    }
}