package org.example.mutantes.service;

import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService statsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Calcula ratio correctamente con humanos > 0")
    void testRatioNormal() {
        when(repository.countByMutant(true)).thenReturn(40L);
        when(repository.countByMutant(false)).thenReturn(100L);

        StatsResponse response = statsService.getStats();

        assertEquals(40, response.getCount_mutant_dna());
        assertEquals(100, response.getCount_human_dna());
        assertEquals(0.4, response.getRatio());
    }

    @Test
    @DisplayName("Ratio = 0 cuando no hay humanos")
    void testNoHumans() {
        when(repository.countByMutant(true)).thenReturn(10L);
        when(repository.countByMutant(false)).thenReturn(0L);

        StatsResponse response = statsService.getStats();

        assertEquals(10, response.getCount_mutant_dna());
        assertEquals(0, response.getCount_human_dna());
        assertEquals(0, response.getRatio());
    }
}