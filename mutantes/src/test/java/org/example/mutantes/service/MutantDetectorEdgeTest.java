package org.example.mutantes.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorEdgeTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void testN3NeverMutant() {
        String[] dna = {
                "ATG",
                "CAG",
                "TTT"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testDiagonalReverseMutant() {
        String[] dna = {
                "ATGCAA",
                "CATGCA",
                "ACATGC",
                "GACATG",
                "TGACAT",
                "CTGACA"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testAllEqualRowsMutant() {
        String[] dna = {
                "AAAA",
                "TTTT",
                "CCCC",
                "GGGG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantLargeMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCACTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }
}
