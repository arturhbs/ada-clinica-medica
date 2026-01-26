package com.clinicamedica.clinica_api.agenda.service;


import com.clinicamedica.clinica_api.agenda.dto.ConsultaRequest;
import com.clinicamedica.clinica_api.agenda.repository.ConsultaRepository;
import com.clinicamedica.clinica_api.shared.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendaServiceTest {

    @Test
    void deveImpedirAgendamentoNoPassado() {
        ConsultaRepository repository = mock(ConsultaRepository.class);
        AgendaService service = new AgendaService(repository);

        LocalDateTime inicio = LocalDateTime.now().minusDays(1);
        LocalDateTime fim = inicio.plusMinutes(30);

        ConsultaRequest request = new ConsultaRequest(1L, 1L, inicio, fim);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.agendar(request));
        assertEquals("Não é permitido agendar no passado.", ex.getMessage());

        verify(repository, never()).existsConflitoHorario(anyLong(), any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void deveImpedirConflitoDeHorarioParaMesmoMedico() {
        ConsultaRepository repository = mock(ConsultaRepository.class);
        when(repository.existsConflitoHorario(anyLong(), any(), any())).thenReturn(true);

        AgendaService service = new AgendaService(repository);

        LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime fim = inicio.plusMinutes(30);

        ConsultaRequest request = new ConsultaRequest(1L, 1L, inicio, fim);

        BusinessException ex = assertThrows(BusinessException.class, () -> service.agendar(request));
        assertEquals("Horário indisponível para este médico.", ex.getMessage());

        verify(repository, times(1)).existsConflitoHorario(eq(1L), eq(inicio), eq(fim));
        verify(repository, never()).save(any());
    }
}
