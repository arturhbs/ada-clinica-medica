package com.clinicamedica.clinica_api.paciente.service;


import com.clinicamedica.clinica_api.paciente.dto.PacienteRequest;
import com.clinicamedica.clinica_api.paciente.repository.PacienteRepository;
import com.clinicamedica.clinica_api.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Test
    void deveLancarExcecaoQuandoCpfJaExiste() {
        PacienteRepository repository = mock(PacienteRepository.class);
        when(repository.existsByCpf("12345678901")).thenReturn(true);

        PacienteService service = new PacienteService(repository);

        PacienteRequest request = new PacienteRequest(
                "Artur Henrique",
                "12345678901",
                "11999999999",
                "artur@gmail.com"
        );

        BusinessException ex = assertThrows(BusinessException.class, () -> service.criar(request));
        assertEquals("CPF já cadastrado.", ex.getMessage());

        verify(repository, times(1)).existsByCpf("12345678901");
        verify(repository, never()).save(any());
    }
}