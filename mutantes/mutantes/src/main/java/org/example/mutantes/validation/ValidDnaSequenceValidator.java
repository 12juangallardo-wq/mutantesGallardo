package org.example.mutantes.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final String VALID_CHARS = "ATCG";

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;

        // tamaño mínimo
        if (n < 4) {
            return false;
        }

        for (String row : dna) {
            if (row == null) {
                return false;
            }

            if (row.length() != n) {
                return false;
            }

            for (char c : row.toCharArray()) {
                if (VALID_CHARS.indexOf(c) == -1) {
                    return false;
                }
            }
        }

        return true;
    }
}
