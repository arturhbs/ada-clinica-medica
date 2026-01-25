package com.clinicamedica.clinica_api.medico.service;


import com.clinicamedica.clinica_api.medico.dto.MedicoRequest;
import com.clinicamedica.clinica_api.medico.dto.MedicoResponse;
import com.clinicamedica.clinica_api.medico.entity.Medico;
import com.clinicamedica.clinica_api.medico.repository.MedicoRepository;
import com.clinicamedica.clinica_api.shared.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService(MedicoRepository repository) {
        this.repository = repository;
    }

    public MedicoResponse criar(MedicoRequest request) {
        if (repository.existsByCrm(request.crm())) {
            throw new BusinessException("CRM já cadastrado.");
        }

        Medico medico = new Medico(request.nome(), request.crm(), request.especialidade());
        Medico salvo = repository.save(medico);
        return toResponse(salvo);
    }

    public MedicoResponse buscarPorId(Long id) {
        Medico medico = repository.findById(id)
                .orElseThrow(() -> new BusinessException("Médico não encontrado."));

        return toResponse(medico);
    }

    public List<MedicoResponse> listar() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private MedicoResponse toResponse(Medico medico) {
        return new MedicoResponse(
                medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEspecialidade()
        );
    }
}