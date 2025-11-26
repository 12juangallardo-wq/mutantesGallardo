package org.example.mutantes.service;

import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.entity.DnaRecord;
import org.example.mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class MutantServiceTest {

    @Test
    void testMutantSaved() {
        MutantDetector detector = Mockito.mock(MutantDetector.class);
        DnaRecordRepository repo = Mockito.mock(DnaRecordRepository.class);

        Mockito.when(detector.isMutant(any())).thenReturn(true);
        Mockito.when(repo.findByDnaHash(any())).thenReturn(java.util.Optional.empty());

        MutantService service = new MutantService(detector, repo);

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"AAAA", "GGGG", "TTTT", "CCCC"});

        boolean result = service.isMutantAndSave(req);

        assertTrue(result);
        Mockito.verify(repo).save(any(DnaRecord.class));
    }

    @Test
    void testHumanSaved() {
        MutantDetector detector = Mockito.mock(MutantDetector.class);
        DnaRecordRepository repo = Mockito.mock(DnaRecordRepository.class);

        Mockito.when(detector.isMutant(any())).thenReturn(false);
        Mockito.when(repo.findByDnaHash(any())).thenReturn(java.util.Optional.empty());

        MutantService service = new MutantService(detector, repo);

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"ATGC", "CAGT", "TTAT", "AGAC"});

        boolean result = service.isMutantAndSave(req);

        assertFalse(result);
        Mockito.verify(repo).save(any(DnaRecord.class));
    }

    @Test
    void testAlreadyExists() {
        MutantDetector detector = Mockito.mock(MutantDetector.class);
        DnaRecordRepository repo = Mockito.mock(DnaRecordRepository.class);

        DnaRecord rec = new DnaRecord();
        rec.setMutant(true);

        Mockito.when(repo.findByDnaHash(any())).thenReturn(java.util.Optional.of(rec));

        MutantService service = new MutantService(detector, repo);

        DnaRequest req = new DnaRequest();
        req.setDna(new String[]{"AAAA", "CCCC", "TTTT", "GGGG"});

        boolean result = service.isMutantAndSave(req);

        assertTrue(result);
        Mockito.verify(repo, Mockito.never()).save(any());
    }
}