package org.example.mutantes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector mutantDetector;

    @BeforeEach
    void setUp() {
        mutantDetector = new MutantDetector();
    }

    @Test
    @DisplayName("Detecta mutante con secuencias horizontal y diagonal")
    void testMutantWithHorizontalAndDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con secuencias verticales")
    void testMutantWithVerticalSequences() {
        String[] dna = {
                "AAAAGA",
                "AAGTGC",
                "AAATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante con múltiples horizontales")
    void testMultipleHorizontalSequences() {
        String[] dna = {
                "TTTTGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta diagonales ascendente y descendente")
    void testBothDiagonals() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTAAGT",
                "AGAAAG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No detecta mutante con solo una secuencia")
    void testOnlyOneSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT", // solo 1 secuencia
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("No detecta mutante sin secuencias")
    void testNoSequences() {
        String[] dna = {
                "ATGC",
                "CAGT",
                "TTAT",
                "AGAC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza ADN nulo")
    void testNullDna() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Rechaza ADN vacío")
    void testEmpty() {
        assertFalse(mutantDetector.isMutant(new String[]{}));
    }

    @Test
    @DisplayName("Rechaza matriz no cuadrada")
    void testNonSquareMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Rechaza caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC", // X inválida
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante en matriz mínima 4x4")
    void testSmallMatrix() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TTAT",
                "AGAC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Detecta mutante en matriz grande 10x10")
    void testLargeMatrix() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA",
                "CCCCTACCCC",
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Test diagonal ascendente (solo valida que no explote)")
    void testAscendingDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCGCTA",
                "TCGCTG"
        };
        assertDoesNotThrow(() -> mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Early termination funciona correctamente")
    void testEarlyTermination() {
        String[] dna = {
                "AAAAGA", // secuencia 1
                "AAAAGC", // secuencia 2 → debería cortar acá
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };

        long start = System.nanoTime();
        boolean result = mutantDetector.isMutant(dna);
        long end = System.nanoTime();

        assertTrue(result);
        assertTrue((end - start) < 10_000_000); // menos de 10ms
    }
}