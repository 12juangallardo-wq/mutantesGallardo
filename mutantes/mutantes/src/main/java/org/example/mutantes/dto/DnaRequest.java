package org.example.mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.mutantes.validation.ValidDnaSequence;

@Getter
@Setter
@Schema(description = "Request que representa la secuencia de ADN a analizar")
public class DnaRequest {

    @NotNull(message = "DNA no puede ser nulo")
    @NotEmpty(message = "DNA no puede estar vacío")
    @ValidDnaSequence
    @Schema(
            description = "Arreglo NxN de ADN con caracteres válidos: A, T, C, G",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
    )
    private String[] dna;
}