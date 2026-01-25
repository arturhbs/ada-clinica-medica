package com.clinicamedica.clinica_api.agenda.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ConsultaRequest(
        @NotNull Long medicoId,
        @NotNull Long pacienteId,
        @NotNull LocalDateTime inicio,
        @NotNull LocalDateTime fim
) {
}