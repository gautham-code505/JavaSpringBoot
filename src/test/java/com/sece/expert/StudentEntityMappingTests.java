package com.sece.expert;

import com.sece.entity.Course;
import com.sece.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentEntityMappingTests {

    @Autowired
    private CourseRepository studentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void createsStudentsTableAndPersistsEntity() {
        Integer tableCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'STUDENTS'",
                Integer.class);

        Course student = studentRepository.save(new Course("Alex", "Computer Science"));

        assertThat(tableCount).isEqualTo(1);
        assertThat(student.getId()).isNotNull();
        assertThat(studentRepository.findById(student.getId())).isPresent();
    }
}