package org.example.mutantes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MutantControllerErrorTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInvalidDnaReturns400() throws Exception {
        // ADN inválido → IllegalArgumentException en el Service
        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"dna\":[\"ATXG\"]}"))
                .andExpect(status().isBadRequest());
    }
}