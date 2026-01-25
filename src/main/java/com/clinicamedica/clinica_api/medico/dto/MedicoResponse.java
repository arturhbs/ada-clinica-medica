package com.clinicamedica.clinica_api.medico.dto;

public record MedicoResponse(
        Long id,
        String nome,
        String crm,
        String especialidade
) {
}