package com.clinicamedica.clinica_api.medico.folder;


import com.clinicamedica.clinica_api.medico.dto.MedicoRequest;
import com.clinicamedica.clinica_api.medico.repository.MedicoRepository;
import com.clinicamedica.clinica_api.medico.service.MedicoService;
import com.clinicamedica.clinica_api.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicoServiceTest {

    @Test
    void deveLancarExcecaoQuandoCrmJaExiste() {
        MedicoRepository repository = mock(MedicoRepository.class);
        when(repository.existsByCrm("CRM12345")).thenReturn(true);

        MedicoService service = new MedicoService(repository);

        MedicoRequest request = new MedicoRequest(
                "Dra. Ana Souza",
                "CRM12345",
                "Cardiologia"
        );

        BusinessException ex = assertThrows(BusinessException.class, () -> service.criar(request));
        assertEquals("CRM já cadastrado.", ex.getMessage());

        verify(repository, times(1)).existsByCrm("CRM12345");
        verify(repository, never()).save(any());
    }
}
