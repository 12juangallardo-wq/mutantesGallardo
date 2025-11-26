package org.example.mutantes.service;

import lombok.RequiredArgsConstructor;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository repository;

    public StatsResponse getStats() {
        long mutants = repository.countByMutant(true);
        long humans = repository.countByMutant(false);

        double ratio = humans == 0 ? 0 : (double) mutants / humans;

        return new StatsResponse(mutants, humans, ratio);
    }
}