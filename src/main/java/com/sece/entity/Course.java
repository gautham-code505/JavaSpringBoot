package com.sece.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "department", nullable = false, length = 100)
    private String department;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "fees")
    private Double fees;

    public Course() {
        this.username = "";
        this.password = "";
    }

    public Course(String name, String department) {
        this.name = name;
        this.department = department;
        this.username = generateDefaultUsername(name);
        this.password = "default123";
    }

    public Course(String name, String department, Integer duration, Double fees) {
        this.name = name;
        this.department = department;
        this.duration = duration;
        this.fees = fees;
        this.username = generateDefaultUsername(name);
        this.password = "default123";
    }

    public Course(String name, String department, String username, String password) {
        this.name = name;
        this.department = department;
        this.username = username;
        this.password = password;
    }

    private String generateDefaultUsername(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "student";
        }
        String normalized = name.trim().replaceAll("\\s+", "").toLowerCase();
        return normalized.isEmpty() ? "student" : normalized;
    }

    public Long getId() {
        return courseId;
    }

    public void setId(Long id) {
        this.courseId = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseName() {
        return name;
    }

    public void setCourseName(String courseName) {
        this.name = courseName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }
}