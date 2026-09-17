package com.sece.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sece.entity.Course;
import com.sece.expert.ExpertApplication;
import com.sece.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ExpertApplication.class)
@AutoConfigureMockMvc
class StudentControllerCrudTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseRepository studentRepository;

    @Test
    void getStudent_returnsAllStudentsFromRepository() throws Exception {
        Course student = new Course("Alice", "Computer Science");
        student.setId(1L);
        given(studentRepository.findAll()).willReturn(Arrays.asList(student));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[0].department").value("Computer Science"));
    }

    @Test
    void postStudent_savesAndReturnsStudent() throws Exception {
        Course request = new Course("Bob", "Physics");
        Course saved = new Course("Bob", "Physics");
        saved.setId(2L);

        given(studentRepository.save(any(Course.class))).willReturn(saved);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Bob"));
    }
}
