package com.jonathansoriano.enterprisedevgroupproject.controller;

import com.jonathansoriano.enterprisedevgroupproject.exception.SearchNotFoundException;
import com.jonathansoriano.enterprisedevgroupproject.model.Student;
import com.jonathansoriano.enterprisedevgroupproject.service.StudentService;
import com.jonathansoriano.enterprisedevgroupproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the StudentApiController REST endpoint (/findStudent).
 * Spring Security filters are disabled via addFilters=false for clean unit
 * testing.
 * UserService is mocked to satisfy the SecurityConfig dependency.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StudentApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class StudentControllerTest {

    /** Mock of the business logic layer used by the controller under test. */
    @MockitoBean
    private StudentService service;

    /**
     * Required mock for Spring Security's UserDetailsService dependency.
     * Without this, the WebMvcTest context fails to load due to SecurityConfig.
     */
    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Verifies that a successful search returns HTTP 200 with the list of students
     * as JSON.
     * Checks that the first two students match the expected names and that the list
     * has exactly 2 items.
     */
    @Test
    void find_Http200() throws Exception {
        // Arrange
        List<Student> expectedList = getStudentList();
        when(service.find(any())).thenReturn(expectedList);

        // Act & Assert
        mockMvc.perform(get("/findStudent")
                .param("firstName", "Jo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jon"))
                .andExpect(jsonPath("$[1].firstName").value("John"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    /**
     * Verifies that when the service throws SearchNotFoundException, the controller
     * returns HTTP 404 Not Found.
     */
    @Test
    void find_Http404() throws Exception {
        // Arrange
        when(service.find(any())).thenThrow(SearchNotFoundException.class);

        // Act & Assert
        mockMvc.perform(get("/findStudent")
                .param("firstName", "Grady")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Helper method that builds a list of two test students named "Jon" and "John".
     *
     * @return a list with two Student objects for use in test assertions
     */
    private List<Student> getStudentList() {
        List<Student> studentList = new ArrayList<>();

        Student student1 = Student.builder()
                .id(1L)
                .firstName("Jon")
                .lastName("Sanjuan")
                .residentCity("Cincinnati")
                .residentState("OH")
                .universityName("University of Cincinnati")
                .grade("Senior")
                .email("jon@mail.com")
                .socialMediaLink("linkedin.com/sorianjn")
                .build();

        Student student2 = Student.builder()
                .id(2L)
                .firstName("John")
                .lastName("Saint")
                .residentCity("Cincinnati")
                .residentState("OH")
                .universityName("University of Cincinnati")
                .grade("Junior")
                .email("john@mail.com")
                .socialMediaLink("linkedin.com/sorianjn")
                .build();

        studentList.add(student1);
        studentList.add(student2);

        return studentList;
    }
}