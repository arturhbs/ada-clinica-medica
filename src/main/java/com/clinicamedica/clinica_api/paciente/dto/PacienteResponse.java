package com.clinicamedica.clinica_api.paciente.dto;

public record PacienteResponse(
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email
) {
    
}
