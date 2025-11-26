package org.example.mutantes.service;

import lombok.RequiredArgsConstructor;
import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.exception.DnaHashCalculationException;
import org.example.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository repository;

    public boolean isMutantAndSave(DnaRequest request) {
        String[] dna = request.getDna();

        // Validaciones mínimas de negocio (además de Bean Validation)
        validarDnaMinimo(dna);

        // Crear hash único del ADN
        String hash = hashDna(dna);

        // Si ya está guardado, devolver su valor
        return repository.findByDnaHash(hash).orElseGet(() -> {

            boolean isMutant = mutantDetector.isMutant(dna);

            DnaRecord record = new DnaRecord();
            record.setDnaHash(hash);
            record.setMutant(isMutant);

            repository.save(record);
            return record;

        }).isMutant();
    }

    // =============================================================
    // 🔍 VALIDACIONES MÍNIMAS
    // =============================================================
    private void validarDnaMinimo(String[] dna) {

        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("El ADN no puede estar vacío");
        }

        int n = dna.length;

        if (n < 4) {
            throw new IllegalArgumentException("El ADN debe tener tamaño mínimo 4x4");
        }

        for (String fila : dna) {

            if (fila == null || fila.length() != n) {
                throw new IllegalArgumentException("El ADN debe ser una matriz cuadrada NxN");
            }

            for (char c : fila.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    throw new IllegalArgumentException("El ADN solo puede contener A,T,C,G");
                }
            }
        }
    }

    // =============================================================
    // 🔐 HASH DEL ADN PARA NO DUPLICAR REGISTROS
    // =============================================================
    private String hashDna(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String joined = String.join("", dna);
            byte[] encoded = digest.digest(joined.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : encoded) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new DnaHashCalculationException("Error generando hash de ADN", e);
        }
    }
}