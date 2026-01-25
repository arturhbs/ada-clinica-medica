package com.clinicamedica.clinica_api.paciente.service;

import com.clinicamedica.clinica_api.paciente.dto.PacienteRequest;
import com.clinicamedica.clinica_api.paciente.dto.PacienteResponse;
import com.clinicamedica.clinica_api.paciente.entity.Paciente;
import com.clinicamedica.clinica_api.paciente.repository.PacienteRepository;
import com.clinicamedica.clinica_api.shared.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    
    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public PacienteResponse criar(PacienteRequest request) {
        if (repository.existsByCpf(request.cpf())) {
            throw new BusinessException("CPF já cadastrado.");
        }

        Paciente paciente = new Paciente(
                request.nome(),
                request.cpf(),
                request.telefone(),
                request.email()
        );

        Paciente salvo = repository.save(paciente);
        return toResponse(salvo);
    }

    public PacienteResponse buscarPorId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Paciente não encontrado."));

        return toResponse(paciente);
    }

    public List<PacienteResponse> listar() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private PacienteResponse toResponse(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEmail()
        );
    }
}
