package org.example.mutantes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mutantes.dto.DnaRequest;
import org.example.mutantes.dto.StatsResponse;
import org.example.mutantes.service.MutantService;
import org.example.mutantes.service.StatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Mutantes", description = "Operaciones para identificar mutantes y consultar estadísticas")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    @PostMapping("/mutant")
    @Operation(
            summary = "Verifica si un ADN pertenece a un mutante",
            description = "Retorna 200 si es mutante, 403 si es humano. Guarda el resultado en la base."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "El ADN pertenece a un mutante"),
            @ApiResponse(responseCode = "403", description = "El ADN pertenece a un humano"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida o formato incorrecto"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> isMutant(@Valid @RequestBody DnaRequest request) {
        boolean isMutant = mutantService.isMutantAndSave(request);

        return isMutant
                ? ResponseEntity.ok().build()
                : ResponseEntity.status(403).build();
    }

    @GetMapping("/stats")
    @Operation(
            summary = "Obtiene estadísticas de mutantes",
            description = "Devuelve la cantidad de ADN mutante, humano, y el ratio entre ambos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estadísticas generadas correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<StatsResponse> stats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}