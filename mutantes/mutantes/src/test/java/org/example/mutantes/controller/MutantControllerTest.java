package org.example.mutantes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.dto.StatsResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /mutant - mutante → 200 OK")
    void testMutantReturns200() throws Exception {

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        });

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant - humano → 403 Forbidden")
    void testHumanReturns403() throws Exception {

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        });

        mockMvc.perform(post("/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("GET /stats - retorna JSON válido")
    void testStats() throws Exception {

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").exists())
                .andExpect(jsonPath("$.count_human_dna").exists())
                .andExpect(jsonPath("$.ratio").exists());
    }
}