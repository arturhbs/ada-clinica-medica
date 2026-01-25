package com.clinicamedica.clinica_api.agenda.dto;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        Long medicoId,
        Long pacienteId,
        LocalDateTime inicio,
        LocalDateTime fim
) {
}