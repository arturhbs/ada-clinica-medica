package com.clinicamedica.clinica_api.paciente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PacienteRequest(    
    @NotBlank @Size(max= 120) String nome,
    @NotBlank @Size(min=11, max=11) String cpf,
    @Size(max=20) String telefone,
    @Email @Size(max = 120) String email
) {

}
