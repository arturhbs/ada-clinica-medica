package com.clinicamedica.clinica_api.medico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicoRequest(
        @NotBlank @Size(max = 120) String nome,
        @NotBlank @Size(max = 20) String crm,
        @NotBlank @Size(max = 80) String especialidade
) {
}