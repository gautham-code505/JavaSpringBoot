package com.sece.controller;

import com.sece.entity.Course;
import com.sece.repository.CourseRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // GET ALL COURSES
    // GET http://localhost:8000/course
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // GET COURSE BY ID
    // GET http://localhost:8000/course/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {

        Course course = courseRepository.findById(id).orElse(null);

        if (course != null) {
            return ResponseEntity.ok(course);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Course with ID " + id + " not found");
    }

    // CREATE COURSE
    // POST http://localhost:8000/course
    @PostMapping
    public ResponseEntity<Course> createCourse(
            @RequestBody Course course) {

        course.setCourseId(null);

        Course savedCourse = courseRepository.save(course);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCourse);
    }

    // UPDATE COURSE
    // PUT http://localhost:8000/course/1
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long id,
            @RequestBody Course details) {

        Course course = courseRepository.findById(id).orElse(null);

        if (course == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Course with ID " + id + " not found");
        }

        course.setCourseName(details.getCourseName());
        course.setDepartment(details.getDepartment());
        course.setDuration(details.getDuration());
        course.setFees(details.getFees());

        Course updatedCourse = courseRepository.save(course);

        return ResponseEntity.ok(updatedCourse);
    }

    // DELETE COURSE
    // DELETE http://localhost:8000/course/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {

        if (!courseRepository.existsById(id)) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Course with ID " + id + " not found");
        }

        courseRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}