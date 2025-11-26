package org.example.mutantes.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        // Validaciones básicas
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;

        // Validar matriz cuadrada y caracteres válidos
        for (String row : dna) {
            if (row == null || row.length() != n) return false;
            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') return false;
            }
        }

        // Convertir a matriz de chars para acceso rápido
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int foundSequences = 0;

        // Recorrer toda la matriz
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Early termination: si ya hay más de una secuencia → mutante
                if (foundSequences > 1) return true;

                char base = matrix[row][col];

                // Horizontal →
                if (col <= n - SEQUENCE_LENGTH) {
                    if (matrix[row][col + 1] == base &&
                            matrix[row][col + 2] == base &&
                            matrix[row][col + 3] == base) {
                        foundSequences++;
                    }
                }

                // Vertical ↓
                if (row <= n - SEQUENCE_LENGTH) {
                    if (matrix[row + 1][col] == base &&
                            matrix[row + 2][col] == base &&
                            matrix[row + 3][col] == base) {
                        foundSequences++;
                    }
                }

                // Diagonal ↘
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (matrix[row + 1][col + 1] == base &&
                            matrix[row + 2][col + 2] == base &&
                            matrix[row + 3][col + 3] == base) {
                        foundSequences++;
                    }
                }

                // Diagonal ↗
                if (row >= SEQUENCE_LENGTH - 1 && col <= n - SEQUENCE_LENGTH) {
                    if (matrix[row - 1][col + 1] == base &&
                            matrix[row - 2][col + 2] == base &&
                            matrix[row - 3][col + 3] == base) {
                        foundSequences++;
                    }
                }
            }
        }

        return foundSequences > 1;
    }
}