package com.sece.repository;

import com.sece.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Course, Long> {

    boolean existsByUsername(String username);

    Optional<Course> findByUsername(String username);
}