package org.example.mutantes.service;

import org.example.mutantes.dto.DnaRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class MutantServiceInvalidTest {

    @InjectMocks
    private MutantService service;

    public MutantServiceInvalidTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDnaNullThrowsException() {
        DnaRequest req = new DnaRequest();
        req.setDna(null);

        assertThrows(IllegalArgumentException.class, () -> service.isMutantAndSave(req));
    }

    @Test
    void testDnaEmptyThrowsException() {
        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{});

        assertThrows(IllegalArgumentException.class, () -> service.isMutantAndSave(req));
    }

    @Test
    void testNonSquareMatrixThrowsException() {
        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"ATG", "CAGT"});

        assertThrows(IllegalArgumentException.class, () -> service.isMutantAndSave(req));
    }

    @Test
    void testInvalidCharactersThrowsException() {
        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"ATXG", "CAGT", "TTAT", "AGAC"});

        assertThrows(IllegalArgumentException.class, () -> service.isMutantAndSave(req));
    }
}
