package org.example.mutantes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Respuesta del endpoint /stats con información estadística")
public class StatsResponse {

    @Schema(description = "Cantidad total de ADN mutante detectado", example = "40")
    private long count_mutant_dna;

    @Schema(description = "Cantidad total de ADN humano detectado", example = "100")
    private long count_human_dna;

    @Schema(description = "Relación entre mutantes y humanos", example = "0.4")
    private double ratio;
}

