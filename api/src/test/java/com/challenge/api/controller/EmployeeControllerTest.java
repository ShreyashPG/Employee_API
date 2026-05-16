package com.challenge.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.api.EntryLevelJavaChallengeApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = EntryLevelJavaChallengeApplication.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllEmployees() throws Exception {
        mockMvc.perform(get("/api/v1/employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].firstName").value("Shreyash"));
    }

    @Test
    void shouldReturnEmployeeByUuid() throws Exception {
        String existingUuid = "8386bb48-78ca-4cac-935d-3c712ffc87cd";
        mockMvc.perform(get("/api/v1/employee/" + existingUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(existingUuid))
                .andExpect(jsonPath("$.lastName").value("Ghanekar"));
    }

    @Test
    void shouldReturn404WhenEmployeeNotFound() throws Exception {
        String randomUuid = "00000000-0000-0000-0000-000000000000";
        mockMvc.perform(get("/api/v1/employee/" + randomUuid)).andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateEmployeeSuccessfully() throws Exception {
        String newEmployeeJson =
                """
                {
                    "firstName": "Jethalal",
                    "lastName": "Gada",
                    "age": 51,
                    "salary": 160000,
                    "jobTitle": "Analyst",
                    "email": "jethalal@gmail.com"
                }
                """;

        mockMvc.perform(post("/api/v1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.fullName").value("Jethalal Gada"));
    }
}
