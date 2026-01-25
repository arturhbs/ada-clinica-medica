package com.clinicamedica.clinica_api.agenda.service;

import com.clinicamedica.clinica_api.agenda.dto.ConsultaRequest;
import com.clinicamedica.clinica_api.agenda.dto.ConsultaResponse;
import com.clinicamedica.clinica_api.agenda.entity.Consulta;
import com.clinicamedica.clinica_api.agenda.repository.ConsultaRepository;
import com.clinicamedica.clinica_api.shared.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaService {

    private final ConsultaRepository repository;

    public AgendaService(ConsultaRepository repository) {
        this.repository = repository;
    }

    public ConsultaResponse agendar(ConsultaRequest request) {
        validarDatas(request.inicio(), request.fim());

        boolean conflito = repository.existsConflitoHorario(request.medicoId(), request.inicio(), request.fim());
        if (conflito) {
            throw new BusinessException("Horário indisponível para este médico.");
        }

        Consulta consulta = new Consulta(
                request.medicoId(),
                request.pacienteId(),
                request.inicio(),
                request.fim()
        );

        Consulta salvo = repository.save(consulta);
        return toResponse(salvo);
    }

    public ConsultaResponse buscarPorId(Long id) {
        Consulta consulta = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Consulta não encontrada."));

        return toResponse(consulta);
    }

    public List<ConsultaResponse> listar() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private void validarDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (!fim.isAfter(inicio)) {
            throw new BusinessException("Fim deve ser depois do início.");
        }
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new BusinessException("Não é permitido agendar no passado.");
        }
    }

    private ConsultaResponse toResponse(Consulta c) {
        return new ConsultaResponse(
                c.getId(),
                c.getMedicoId(),
                c.getPacienteId(),
                c.getInicio(),
                c.getFim()
        );
    }
}
